package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.container.Container;
import track.container.JsonConfigReader;
import track.messenger.commands.CommandException;
import track.messenger.commands.CommandFactory;
import track.messenger.messages.Message;
import track.messenger.store.ArrayStoreImpl.ArrayMessageStore;
import track.messenger.store.ArrayStoreImpl.ArrayUserStore;
import track.messenger.store.StoreFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 *
 */
public class MessengerServer {

    static Logger log = LoggerFactory.getLogger(MessengerServer.class);

    private Protocol protocol;
    private int port;

    private ServerSocket serverSocket;
    private BlockingQueue<Session> sessionBlockingQueue = new LinkedBlockingQueue<>();
    Executor executor = Executors.newFixedThreadPool(5);
    private StoreFactory storeFactory = new StoreFactory(new ArrayMessageStore(), new ArrayUserStore());

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

    private void initSocket() throws IOException {
        serverSocket = new ServerSocket(getPort());
       // serverSocket.setSoTimeout(3000);
    }

    public void runListener() {
        Thread listenerThread = new Thread(() -> {
            while (true) {
                Socket clientSocket;
                Session session;
                try {
                    clientSocket = serverSocket.accept();
                    session = new Session(clientSocket, protocol);
                    log.info("New client accepted. Session created, id: " + session.getId());
                    sessionBlockingQueue.put(session);
                } catch (IOException e) {
                    log.error("Failed to accept socket.", e);
                } catch (InterruptedException e) {
                    log.error("Listener interrupted");
                }
            }
        });
        listenerThread.setName("Listener thread");
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public void runServer() {
        try {
            log.info("Server started");
            initSocket();
            log.info("Socket initialized, port: " + getPort());
            runListener();
            log.info("Listener started");
            while (true) {
                if (sessionBlockingQueue.isEmpty()) {
                    Thread.sleep(500);
                    continue;
                }
                Session session = sessionBlockingQueue.poll(3000, TimeUnit.MILLISECONDS);
                if (session == null) {
                    continue;
                }
                //log.info("Took session from queue, id: " + session.getId());
                Message message;
                try {
                    //message = session.receiveMessage();
                    message = session.pollMessage(1000);
                    if (message == null) {
                        sessionBlockingQueue.put(session);
                        //log.info("Added session to queue");
                        continue;
                    }
                    executor.execute(() -> {
                        try {
                            CommandFactory.get(message.getType()).execute(session, message, storeFactory);
                        } catch (CommandException e) {
                            log.error("Cannot execute command");
                        } catch (IOException e) {
                            log.error("Network error");
                        } catch (ProtocolException e) {
                            log.error("Protocol error");
                        }
                        try {
                            sessionBlockingQueue.put(session);
                        } catch (InterruptedException e) {
                            log.error("Executor interrupted");
                        }
                    });
                } catch (IOException e) {
                    log.info("Session ended, id: " + session.getId());
                    session.close();
                } catch (ProtocolException e) {
                    log.error("Protocol error", e);
                    sessionBlockingQueue.put(session);
                    log.info("Added session to queue");
                }
            }
        } catch (IOException e) {
            log.error("Socket init failed.", e);
        } catch (InterruptedException e) {
            log.error("Server interrupted.", e);
        }
    }

    public static void main(String... args) {
        Container container = new Container("src\\main\\resources\\server.json", new JsonConfigReader());
        MessengerServer server = (MessengerServer) container.getById("messengerServer");
        if (server == null) {
            log.error("Cannot initialize sever.");
            return;
        }
        server.runServer();
    }
}
