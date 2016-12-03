package track.messenger.teacher.client;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import track.messenger.messages.*;
import track.messenger.net.Protocol;
import track.messenger.net.ProtocolException;
import track.messenger.net.StringProtocol;


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

        /*
      Тред "слушает" сокет на наличие входящих сообщений от сервера
     */
        Thread socketListenerThread = new Thread(() -> {
            final byte[] buf = new byte[1024 * 64];
            log.info("Starting listener thread...");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Здесь поток блокируется на ожидании данных
                    int read = 2;
                    if (read > 0) {

                        // По сети передается поток байт, его нужно раскодировать с помощью протокола
                        Message msg = (Message) new ObjectInputStream(in).readObject();
                        onMessage(msg);
                    }
                } catch (Exception e) {
                    log.error("Failed to process connection: {}", e);
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });

        socketListenerThread.start();
    }

    /**
     * Реагируем на входящее сообщение
     */
    public void onMessage(Message msg) {
        log.info("Message received: {}", msg);
    }

    /**
     * Обрабатывает входящую строку, полученную с консоли
     * Формат строки можно посмотреть в вики проекта
     */
    public void processInput(String line) throws IOException, ProtocolException {
        String[] tokens = line.split(" ");
        log.info("Tokens: {}", Arrays.toString(tokens));
        String cmdType = tokens[0];
        switch (cmdType) {
            case "/login":
                LoginMessage loginMessage = new LoginMessage(1l, 2l, Type.MSG_LOGIN, tokens[1], tokens[2]); // Id, SenderId - not used, not usefull))
                send(loginMessage);
                // TODO: реализация
                break;
            case "/sign_up":
                SignUpMessage signUpMessage = new SignUpMessage(1l, 2l, Type.MSG_SIGN_UP, tokens[1], tokens[2]); // Id, SenderId - usage??
                send(signUpMessage);
                break;
            case "/create_chat":
                ChatCommandMessage chatCommandMessage = new ChatCommandMessage();
                ArrayList<Long> arrayList = new ArrayList<Long>();
                for(int i = 1; i < tokens.length; i++)
                {
                    arrayList.add(Long.valueOf(tokens[i]));
                }
                chatCommandMessage.setIdToDo(arrayList);
                chatCommandMessage.setId(1l); //ID  usage ??
                chatCommandMessage.setSenderId(2l); // SenderID usage ??
                chatCommandMessage.setType(Type.MSG_CHAT_CREATE);
                send(chatCommandMessage);
                break;
            case "/chat_list":
                ChatInfMessage chatInf = new ChatInfMessage(1l, 2l, Type.MSG_CHAT_LIST); // Id, SenderId - usage??
                send(chatInf);
                break;
            case "/chat_hist":
                ChatInfMessage chatHist = new ChatInfMessage(1l, 2l, Type.MSG_CHAT_HIST); // Id, SenderId - usage??
                chatHist.setText(tokens[1]);
                send(chatHist);
                break;
            case "/help":
                // TODO: реализация
                break;
            case "/text":
                // FIXME: пример реализации для простого текстового сообщения
                TextMessage sendMessage = new TextMessage();
                sendMessage.setType(Type.MSG_TEXT);
                sendMessage.setSendToId(Long.parseLong(tokens[1]));
                sendMessage.setSenderId(2l); // My ID
                sendMessage.setText(tokens[2]);
                send(sendMessage);
                break;
            // TODO: implement another types from wiki

            default:
                log.error("Invalid input: " + line);
        }
    }

    /**
     * Отправка сообщения в сокет клиент -> сервер
     */
    public void send(Message msg) throws IOException, ProtocolException {
        log.info(msg.toString());
        ObjectOutputStream out2 = new ObjectOutputStream(out);
        out2.writeObject(msg);
        out2.flush(); // принудительно проталкиваем буфер с данными
    }

    public static void main(String[] args) throws Exception {

        MessengerClient client = new MessengerClient();
        client.setHost("localhost");
        client.setPort(8888);
        client.setProtocol(new StringProtocol());

        try {
            client.initSocket();

            // Цикл чтения с консоли
            Scanner scanner = new Scanner(System.in);
            System.out.println("$");
            while (true) {
                String input = scanner.nextLine();
                if ("q".equals(input)) {
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
//                client.close();
            }
        }
    }
}