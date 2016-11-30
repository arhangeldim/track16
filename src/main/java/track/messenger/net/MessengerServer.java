package track.messenger.net;

import org.mockito.internal.util.io.IOUtil;
import track.messenger.commands.Command;
import track.messenger.commands.CommandException;
import track.messenger.commands.CommandFactory;
import track.messenger.messages.*;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;

/**
 *
 */

public class MessengerServer {

    private ServerSocket serverSocket;
    private BlockingQueue<Session> sessions;

    private Integer port;
    private Integer nthreads;
    private boolean alive;
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
            while (!Thread.currentThread().isInterrupted() && alive) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    sessions.put(new Session(clientSocket));
                } catch (IOException e) {
                    System.out.println("Клиент отключился. " + e.toString());
                    Thread.currentThread().interrupt();
                } catch (InterruptedException e) {
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
        alive = true;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Ждем соединения...");
            listen();

            while (alive) {
                Session session = sessions.take();
                Message msg = session.getMessage();
                if (msg != null) {
                    service.submit(() -> {
                        try {
                            Command command = commands.get(msg.getType());
                            try {
                                command.execute(session, msg, crypto, stores);
                            } catch (CommandException e) {
                                System.out.println("Ошибка выполнения команды: " + e.toString());
                            }
                            sessions.put(session);
                        } catch (InterruptedException e) {
                            System.out.println("Клиент отключился. " + e.toString());
                        }
                    });
                } else if (session.isAlive()) {
                    service.submit(() -> {
                        try {
                            Command command = commands.get(Type.REFRESH);
                            command.execute(session, msg, crypto, stores);
                            sessions.put(session);
                        } catch (Exception e) {
                            System.out.println(this.getClass() + ": ошибка обработки входящих: " + e.toString());
                        }
                    });
                } else {
                    session.kill();
                }
            }

        } finally {
            sessions.forEach(Session::kill);
            stores.close();
            IOUtil.closeQuietly(serverSocket);
        }
    }

    public void halt() {
        alive = false;
    }

}
