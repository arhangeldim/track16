package track.messenger.teacher.client;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.net.ObjectProtocol;
import track.messenger.net.Protocol;
import track.messenger.net.ProtocolException;


/**
 *
 */
public class MessengerClient {

    public static final int MAX_MSG_SIZE = 32 * 1024;
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

    private User user;

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

        Thread socketListenerThread = new Thread(() -> {
            final byte[] buf = new byte[MAX_MSG_SIZE];
            log.info("Слушаем сервер...");
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
        socketListenerThread.setDaemon(true);
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
            case MSG_AUTHORIZED:
                InfoResultMessage amsg = (InfoResultMessage) msg;
                user = amsg.getRequestedUser();
                break;
            case MSG_INFO_RESULT:
                InfoResultMessage irmsg = (InfoResultMessage) msg;
                System.out.println(irmsg.getRequestedUser());
                break;
            case MSG_STATUS:
                StatusMessage smsg = (StatusMessage) msg;
                System.out.println(smsg);
                break;
            default:
                System.out.println(this.getClass() + ": ошибка сервера");
        }
    }

    /**
     * Обрабатывает входящую строку, полученную с консоли
     * Формат строки можно посмотреть в вики проекта
     */
    public void processInput(String line) throws IOException, ProtocolException {
        String[] tokens = line.split(" ");
        log.info("Tokens: {}", Arrays.toString(tokens));
        String cmdType = tokens[0];
        String data = "";
        if (tokens.length > 1) {
            data = line.substring(cmdType.length()).trim();
        }
        switch (cmdType) {
            case "/login":
                send(new LoginMessage(user, data));
                break;
            case "/help":
                // TODO: реализация
                break;
            case "/info":
                if (user != null) {
                    send(new InfoMessage(user, data));
                } else {
                    System.out.println("Вы не авторизованы.");
                }
                break;
            case "/text":
                send(new TextMessage(user, data));
                break;
            case "/quit":
                send(new QuitMessage(user));
                break;
            default:
                log.error("Invalid input: " + line);
                break;
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

    public void close() {
    }

    public static void main(String[] args) throws Exception {

        MessengerClient client = new MessengerClient();
        client.setHost("localhost");
        client.setPort(19000);
        client.setProtocol(new ObjectProtocol());

        try {
            client.initSocket();

            // Цикл чтения с консоли
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                try {
                    client.processInput(input);
                } catch (ProtocolException | IOException e) {
                    log.error("Ошибки при обработке потока ввода.", e);
                }
                if ("/quit".equals(input)) {
                    System.out.println("Завершение сеанса...");
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Приложение рухнуло с оглушительным грохотом.", e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}