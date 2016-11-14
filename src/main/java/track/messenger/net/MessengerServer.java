package track.messenger.net;

import org.apache.commons.lang.SerializationException;
import org.mockito.internal.util.io.IOUtil;
import track.messenger.messages.*;
import track.messenger.store.UserStore;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

/**
 *
 */

public class MessengerServer {

    private Integer port;
    private Integer nthreads = 4;

    private ServerSocket serverSocket;
    private ExecutorService service = Executors.newFixedThreadPool(nthreads);
    public static UserStore users = new UserStore("store.sqlite3");

    public MessengerServer() {}

    public void setPort(Integer port) {
        this.port = port;
    }

    public void start() throws Exception {
        serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Ждем соединения...");

            while (true) {
                service.submit(() -> {
                    Session session = new Session(serverSocket);
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            Message msg = session.getMessage();
                            session.onMessage(msg);
                        } catch (Exception e) {
                            System.out.println("Клиент отсоединился. " + e.toString());
                            Thread.currentThread().interrupt();
                        }
                    }
                });
            }

        } finally {
            IOUtil.closeQuietly(serverSocket);
        }
    }

    public static void main(String[] args) throws Exception {
        MessengerServer server = new MessengerServer();
        server.setPort(19000);
        server.start();
    }
}
