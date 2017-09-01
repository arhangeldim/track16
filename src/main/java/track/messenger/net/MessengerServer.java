package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.commands.Command;
import track.messenger.commands.CommandFactory;
import track.messenger.messages.Message;
import track.messenger.store.MessageList;
import track.messenger.store.MessageStore;
import track.messenger.store.UserList;
import track.messenger.store.UserStore;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class MessengerServer {
    private ServerSocket serverSocket;
    private String host;
    private int port;

    private UserStore userStore;
    private MessageStore messageStore;

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

    public UserStore getUserStore() {
        return userStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public MessageStore getMessageStore() {
        return messageStore;
    }

    public void setMessageStore(MessageStore messageStore) {
        this.messageStore = messageStore;
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
            // в новый поток
            Thread listenerThread = new Thread(() -> {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        Session session = new Session(clientSocket, userStore, messageStore);
                        new Thread(() -> {
                            while (true) {
                                try {
                                    Message msg = session.receiveMessage();
                                    Command command = CommandFactory.get(msg.getType());
                                    command.execute(session, msg, messageStore, userStore);
                                } catch (ProtocolException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
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
        MessageStore messageStore = new MessageList();
        server.setMessageStore(messageStore);
        UserStore userStore = new UserList();
        server.setUserStore(userStore);
        server.run();
    }
}
