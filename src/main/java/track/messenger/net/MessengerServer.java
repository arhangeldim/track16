package track.messenger.net;

import org.mockito.internal.util.io.IOUtil;
import track.messenger.commands.*;
import track.messenger.db.MessageBase;
import track.messenger.db.UserBase;
import track.messenger.messages.Message;
import track.messenger.messages.Type;
import track.messenger.store.MessageFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class MessengerServer {

    private static final int MAX_CLIENTS = 1000;
    private ServerSocket serverSocket;
    private ArrayBlockingQueue<Session> sessions;
    private int port;
    private int nthreads;
    private volatile boolean running;
    private MessageBase messageBase;
    private ExecutorService service;
    private UserBase userBase;
    private MessageFactory messageFactory;
    private CommandExecutor commandExecutor;
    private volatile long numchats;
    private volatile long numusers;

    private void addCommands()
    {
        this.commandExecutor.addCommand(Type.MSG_LOGIN, new LoginCommand(this));
        this.commandExecutor.addCommand(Type.MSG_REGISTER, new RegistrationCommand(this));
        this.commandExecutor.addCommand(Type.MSG_CHAT_CREATE, new CreateChatCommand(this));
        this.commandExecutor.addCommand(Type.MSG_CHAT_HIST, new HistChatCommand(this));
        this.commandExecutor.addCommand(Type.MSG_TEXT, new TextCommand(this));
        this.commandExecutor.addCommand(Type.MSG_CHAT_LIST, new ListChatCommand(this));
    }

    public MessengerServer() throws SQLException, ClassNotFoundException {
        userBase = new UserBase();
        messageBase = new MessageBase();
        commandExecutor = new CommandExecutor();
        numchats = userBase.Chat_size();
        numusers = userBase.User_size();
        addCommands();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setNthreads(int nthreads) {
        this.nthreads = nthreads;
    }

    public void start() throws Exception{
        running = true;
        serverSocket
                = null;
        sessions = new ArrayBlockingQueue<Session>(MAX_CLIENTS);
        service = Executors.newFixedThreadPool(nthreads);
        System.out.println("Waiting...");
        serverSocket = new ServerSocket(port);
        listen();
        Message msg = null;
        while (running) {
            Session session = null;
            try {
                session = sessions.take();
                msg = session.getMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (msg != null) {
                Session finalSession = session;
                service.submit(() -> {
                    try {
                        assert finalSession != null;
                        sessions.put(finalSession);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        IOUtil.closeQuietly(serverSocket);

    }

    private void listen() {
        Thread listenerThread = new Thread(() -> {
            System.out.println("Listening...");
            while (!Thread.currentThread().isInterrupted() && running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    sessions.add(new Session(clientSocket, commandExecutor, userBase, messageBase));//убрать команды из каждого потока водин
                } catch (Exception e) {
                    System.out.println("listen: " + e.toString() + " error");
                    Thread.currentThread().interrupt();
                }
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public boolean isrunning()
    {
        return running;
    }

    public void stop() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    public ArrayBlockingQueue<Session> getSessions() {
        return sessions;
    }

    public MessageFactory getMessageStore() {
        return messageFactory;
    }

    public long getnumchat() {
        return numchats;
    }

    public long getnumlogin() {
        return numusers;
    }

    public void chatincr() {
        numchats++;
    }

    public void loginincr() {
        numusers++;
    }
}
