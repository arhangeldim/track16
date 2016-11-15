package track.messenger.net;

import org.mockito.internal.util.io.IOUtil;
import track.messenger.commands.Commander;
import track.messenger.messages.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 *
 */

public class MessengerServer {

    private Integer port;
    private Integer nthreads;
    private Commander commander;

    private ServerSocket serverSocket;
    private LinkedBlockingQueue<Session> sessions;

    public MessengerServer() {}

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setNthreads(Integer nthreads) {
        this.nthreads = nthreads;
    }

    public void setCommander(Commander commander) {
        this.commander = commander;
        this.commander.setTypeCommandMap();
    }

    public void listen() {
        Thread listenerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    sessions.add(new Session(clientSocket, commander));
                } catch (Exception e) {
                    System.out.println("listen: " + e.toString());
                    Thread.currentThread().interrupt();
                }
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public void start() throws Exception {
        serverSocket = null;
        sessions = new LinkedBlockingQueue<>();
        ExecutorService service = Executors.newFixedThreadPool(nthreads);
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Ждем соединения...");
            listen();

            while (true) {
                Session session = sessions.take();
                Message msg = session.getMessage();
                if (msg != null) {
                    service.submit(() -> {
                        try {
                            session.onMessage(msg);
                            if (session.isAlive()) {
                                sessions.put(session);
                            } else {
                                System.out.println("Клиент отключился.");
                            }
                        } catch (Exception e) {
                            System.out.println(this.getClass() + ": ошибка обработки сообщения: " + e.toString());
                        }
                    });
                } else if (session.isAlive()) {
                    sessions.put(session);
                }
            }

        } finally {
            IOUtil.closeQuietly(serverSocket);
        }
    }

}
