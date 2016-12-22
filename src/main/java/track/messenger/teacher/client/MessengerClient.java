package track.messenger.teacher.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.net.BinaryProtocol;
import track.messenger.net.Protocol;
import track.messenger.net.ProtocolException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 *
 */
public class MessengerClient {


    /**
     * Механизм логирования позволяет более гибко управлять записью данных в лог (консоль, файл и тд)
     * */
    static Logger log = LoggerFactory.getLogger(MessengerClient.class);

    /**
     * Протокол, хост и порт инициализируются из конфига
     *
     * */
    private Protocol protocol;
    private int port;
    private String host;
    private User user;
    private boolean running;

    /**
     * С каждым сокетом связано 2 канала in/out
     */
    private InputStream in;
    private OutputStream out;

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void initSocket() throws IOException {
        Socket socket = new Socket(host, port);
        in = socket.getInputStream();
        out = socket.getOutputStream();
        running = true;

        /*
      Тред "слушает" сокет на наличие входящих сообщений от сервера
     */
        Thread socketListenerThread = new Thread(() -> {
            final byte[] buf = new byte[1024 * 64];
            log.info("Starting listener thread...");
            while (!Thread.currentThread().isInterrupted() && running) {
                try {
                    // Здесь поток блокируется на ожидании данных
                    int read = in.read(buf);
                    if (read > 0) {

                        // По сети передается поток байт, его нужно раскодировать с помощью протокола
                        Message msg = protocol.decode(Arrays.copyOf(buf, read));
                        onMessage(msg);
                    }
                } catch (Exception e) {
                    log.error("Failed to process connection: {}", e);
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("exit onMessage");
        });

        socketListenerThread.start();
    }

    /**
     * Реагируем на входящее сообщение
     */
    public void onMessage(Message msg) {
        log.info("Message received: {}", msg);
        if (msg == null) {
            return;
        }

        Type msgType = msg.getType();
        switch (msgType) {
            case MSG_INFO_RESULT:
                InfoResultMessage selfInfoMessage = (InfoResultMessage) msg;
                selfInfoMessage.setType(Type.MSG_INFO_RESULT);
                break;
            case MSG_STATUS:
                StatusMessage statusMessage = (StatusMessage) msg;
                statusMessage.setType(Type.MSG_STATUS);
                switch (statusMessage.getStatus()) {
                    case "EXIST":
                        System.out.println("This login is already exists");
                        break;
                }
                System.out.println(statusMessage.getStatus());
                break;
            case MSG_CHAT_HIST_RESULT:
                ChatHistResultMessage histMessage = (ChatHistResultMessage) msg;
                histMessage.setType(Type.MSG_CHAT_HIST_RESULT);
                ArrayList<String> text = (ArrayList<String>) histMessage.getMessage();
                ArrayList<String> name = (ArrayList<String>) histMessage.getName();
                for (int i = 0; i < text.size(); i++) {
                    System.out.println(name.get(i) + ": " + text.get(i));
                }
                break;
            case MSG_CHAT_LIST_RESULT:
                ChatListResultMessage listMessage = (ChatListResultMessage) msg;
                listMessage.setType(Type.MSG_CHAT_LIST_RESULT);
                String chats = listMessage.getChatIds().stream()
                        .map(id -> id.toString() + " ")
                        .reduce((first, second) -> first + second)
                        .orElse("No Chats.");
                System.out.println(chats);
                break;
            default:
                System.out.println(this.getClass() + ": error in server");
        }
    }


    public void processInput(String line) throws IOException, ProtocolException {
        String[] tokens = line.split(" ");
        //log.info("Tokens: {}", Arrays.toString(tokens));
        String cmdType = tokens[0];
        switch (cmdType) {
            case "/login":
                LoginMessage login = new LoginMessage();
                login.setType(Type.MSG_LOGIN);
                login.setUsername(tokens[1]);
                login.setPassword(tokens[2]);
                send(login);
                break;
            case "/help":
                System.out.println("HELP:\n" +
                        "/help - help.\n" +
                        "/register [username] [password] - registration\n" +
                        "/login [username] [password] - login.\n" +
                        "/info - information.\n" +
                        "/info [id] - information about user id.\n" +
                        "/chat_list - list chats.\n" +
                        "/chat_create [id1] [id2] ... - crate chat with users id1, id2...\n" +
                        "/text [id] [text] - send message text to chat with id.\n" +
                        "/chat_hist [id] - get messages from chat id.\n" +
                        "/quit - quit."
                );
                break;
            case "/text":
                TextMessage sendMessage = new TextMessage();
                sendMessage.setType(Type.MSG_TEXT);
                sendMessage.setChatId(Long.valueOf(tokens[1]));
                sendMessage.setText(tokens[2]);
                send(sendMessage);
                break;
            case "/info":
                InfoMessage info = new InfoMessage();
                info.setType(Type.MSG_INFO);
                info.setId(Long.valueOf(tokens[1]));
                send(info);
                break;
            case "/chat_list":
                ChatList chatlist = new ChatList();
                chatlist.setType(Type.MSG_CHAT_LIST);
                send(chatlist);
                break;
            case "/chat_create":
                ChatCreate chatcreate = new ChatCreate();
                chatcreate.setType(Type.MSG_CHAT_CREATE);
                chatcreate.setUsersIds(tokens);
                send(chatcreate);
                break;
            case "/chat_history":
                ChatHistory chathist = new ChatHistory();
                chathist.setType(Type.MSG_CHAT_HIST);
                chathist.setId(Long.valueOf(tokens[1]));
                send(chathist);
                break;
            case "/register":
                Registration registration = new Registration();
                registration.setType(Type.MSG_REGISTER);
                registration.setUsername(tokens[1]);
                registration.setPassword(tokens[2]);
                send(registration);
                break;
            default:
                log.error("Invalid input: " + line);
        }
    }

    /**
     * Отправка сообщения в сокет клиент -> сервер
     */
    public void send(Message msg) throws IOException, ProtocolException {
        log.info(msg.toString());
        out.write(protocol.encode(msg));
        out.flush(); // принудительно проталкиваем буфер с данными
    }



    public static void main(String[] args) throws Exception {

        MessengerClient client = new MessengerClient();
        client.setHost("localhost");
        client.setPort(19000);
        client.setProtocol(new BinaryProtocol());

        try {
            client.initSocket();

            // Цикл чтения с консоли
            Scanner scanner = new Scanner(System.in);
            System.out.println("$");
            while (client.isRunning()) {
                String input = scanner.nextLine();
                if ("q".equals(input)) {
                    client.close();
                    System.out.println("exit processInput");
                    return;
                }
                try {
                    client.processInput(input);
                } catch (ProtocolException | IOException e) {
                    log.error("Failed to process user input", e);
                }
            }
        } catch (Exception e) {
            log.error("Application failed.", e);
        } finally {
            if (client != null) {
                // TODO
                client.close();
            }
        }
    }

    public void close() {
        running = false;
    }


    public boolean isRunning() {
        return running;
    }
}