package track.messenger.net;

import org.mockito.internal.util.io.IOUtil;
import track.messenger.commands.Command;
import track.messenger.commands.CommandFactory;
import track.messenger.messages.*;
import track.messenger.security.CryptoSystem;
import track.messenger.store.ChatRelationStore;
import track.messenger.store.MessageStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.UserStore;
import track.messenger.store.dao.ChatRelation;
import track.messenger.store.dao.User;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 *
 */

public class MessengerServer {

    private ServerSocket serverSocket;
    private LinkedBlockingQueue<Session> sessions;

    private Integer port;
    private Integer nthreads;
    private StoreFactory stores;
    private CommandFactory commands;
    private CryptoSystem crypto;

    public MessengerServer() {}

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setNthreads(Integer nthreads) {
        this.nthreads = nthreads;
    }

    public void setStores(StoreFactory stores) {
        this.stores = stores;
    }

    public void setCommands(CommandFactory commands) {
        this.commands = commands;
    }

    public void setCrypto(CryptoSystem crypto) {
        this.crypto = crypto;
    }

    public void listen() {
        Thread listenerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    sessions.put(new Session(clientSocket));
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
                            Command command = commands.get(msg.getType());
                            command.execute(session, msg, crypto, stores);
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
                    service.submit(() -> {
                        try {
                            Command command = commands.get(Type.REFRESH);
                            command.execute(session, new ChatHistMessage(), crypto, stores);
                            sessions.put(session);
                        } catch (Exception e) {
                            System.out.println(this.getClass() + ": ошибка обработки входящих: " + e.toString());
                        }
                    });
                }
            }

        } finally {
            IOUtil.closeQuietly(serverSocket);
        }
    }

}
