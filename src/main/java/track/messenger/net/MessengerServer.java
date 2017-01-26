package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 */
public class MessengerServer {
    private ServerSocket serverSocket;
    private Session session; //TODO очередь сессий
    private String host;
    private int port;

    static Logger log = LoggerFactory.getLogger(MessengerServer.class);

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public MessengerServer(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void bind() {
        try {
            // привинтить сокет на локалхост, порт 3128
            this.setServerSocket(new ServerSocket(port, 0, InetAddress.getByName(host)));
        } catch (Exception e) {
            System.out.println("bind error: " + e);
        } // вывод исключений
    }

    public void listen() {
        try {

            // ждём нового подключения, после чего запускаем обработку клиента
            // в новый вычислительный поток
            Thread listenerThread = new Thread(() -> {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        session = new Session(clientSocket);
                        new Thread(() -> {
                            while (true) {
                                try {
                                    session.receiveMessage();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ProtocolException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } catch (IOException e) {
                        System.out.println("Connection with client closed. " + e.toString());
                    }
                }
            });
            // listenerThread.setDaemon(true);
            listenerThread.start();
        } catch (Exception e) {
            System.out.println("listen error: " + e);
        }
    }

    public void run() {
        bind();
        System.out.println("server is started");
        listen();
    }

    public static void main(String args[]) {
        MessengerServer server = new MessengerServer("localhost", 19000);
        server.run();
    }
}
