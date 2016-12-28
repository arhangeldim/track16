package track.messenger.teacher.client;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import track.container.Container;
import track.container.JsonConfigReader;
import track.messenger.messages.*;
import track.messenger.messages.client.*;
import track.messenger.net.Protocol;
import track.messenger.net.ProtocolException;


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
        try {
            if (tokens.length == 0) return;
            if (tokens[0].equals("/help")) {
                //TODO help
            }
            Message message = parseToMessage(tokens);
            send(message);
        } catch (InvalidInputExeption e) {
            log.error("Invalid input: " + line);
        }
    }

    public Message parseToMessage(String[] tokens) throws InvalidInputExeption {
        log.info("Tokens: {}", Arrays.toString(tokens));
        String cmdType = tokens[0];
        switch (cmdType) {
            case "/login":
                return new LoginMessage(tokens[1], tokens[2]);
            case "/text":
                if (tokens.length != 3)
                    throw new InvalidInputExeption();
                if (!isLong(tokens[1]))
                    throw new InvalidInputExeption();
                return new TextMessage(Long.parseLong(tokens[1]), tokens[2]);
            case "/info":
                InfoMessage infoMessage = new InfoMessage();
                if (tokens.length < 2) {
                    infoMessage.setUserId(null);
                } else {
                    if (tokens[1].matches("[0-9]+")) {
                        infoMessage.setUserId(new Long(tokens[1]));
                    } else {
                        throw new InvalidInputExeption();
                    }
                }
                return infoMessage;
            case "/chat_list":
                assert tokens.length < 2;
                return new ChatListMessage();
            case "/chat_create":
                assert tokens.length == 2;
                try {
                    List<Long> ids = Arrays.stream(tokens[1].split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                    return new ChatCreateMessage(ids);
                } catch (PatternSyntaxException | NumberFormatException e) {
                    throw new InvalidInputExeption();
                }
            case "/chat_history":
                assert tokens.length == 2;
                assert isLong(tokens[1]);
                return new ChatHistMessage(Long.parseLong(tokens[1]));
            default:
                throw new InvalidInputExeption();
        }
    }

    private boolean isLong(String s) {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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

        Container container = new Container("src\\main\\resources\\client.json", new JsonConfigReader());
        MessengerClient client = (MessengerClient) container.getById("messengerClient");

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