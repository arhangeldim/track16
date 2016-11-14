package track.messenger.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

//import sun.nio.ch.IOUtil;
import org.mockito.internal.util.io.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.store.UserStore;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */

public class Session {

    static Logger log = LoggerFactory.getLogger(Session.class);
    /**
     * Пользователь сессии, пока не прошел логин, user == null
     * После логина устанавливается реальный пользователь
     */
    private static final int MAX_MSG_SIZE = 32 * 1024;
    private static final int TIMEOUT = 100;
    private User user;

    private Socket socket;
    private Protocol protocol;
    private boolean alive;

    private InputStream in;
    private OutputStream out;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public Message getMessage() throws IOException, ProtocolException {

        byte[] buf = new byte[MAX_MSG_SIZE];
        int read = 0;
        Message ret = null;
        try {
            read = in.read(buf, 0, MAX_MSG_SIZE);
            if (read > 0) {
                ret = protocol.decode(buf);
            }
        } catch (SocketTimeoutException ste) {
            ret = null;
        }
        return ret;
    }

    public Session(Socket clientSocket) {
        try {
            //socket = serverSocket.accept();
            socket = clientSocket;
            socket.setSoTimeout(TIMEOUT);
            System.out.println("Подключился клиент " + socket.getInetAddress());
            in = socket.getInputStream();
            out = socket.getOutputStream();
            protocol = new ObjectProtocol();
            alive = true;
        } catch (Exception e) {
            System.out.println(this.getClass() + "Не удалось создать сессию. " + e.toString());
            close();
        }
    }

    public void send(Message msg) throws ProtocolException, IOException {
        if (msg.getSenderId() != null) {
            log.info("sent to: " + msg.getSenderId().toString());
        } else {
            log.info("sent to: " + null);
        }
        out.write(protocol.encode(msg));
        out.flush();
    }

    public void onMessage(Message msg) throws ProtocolException, IOException {
        Type msgType = msg.getType();
        switch (msgType) {
            case MSG_LOGIN:
                LoginMessage lmsg = (LoginMessage) msg;
                auth(lmsg.getUsername(), lmsg.getPassword());
                send(new InfoResultMessage(user));
                break;
            case MSG_TEXT:
                TextMessage tmsg = (TextMessage) msg;
                break;
            case MSG_INFO:
                InfoMessage imsg = (InfoMessage) msg;
                send(new InfoResultMessage(user, MessengerServer.users.getUser(imsg.getRequestedUser())));
                break;
            case MSG_QUIT:
                QuitMessage qmsg = (QuitMessage) msg;
                close();
                break;
            default:
                System.out.println("Неправильная команда!");
                break;
        }
    }

    public void close() {
        IOUtil.closeQuietly(in);
        IOUtil.closeQuietly(out);
        IOUtil.closeQuietly(socket);
        alive = false;
    }

    public void auth(String username, String password) {
        user = MessengerServer.users.loginUser(username, password);
        if (user != null) {
            System.out.println("Подключился " + user.toString());
        }
    }

    public boolean isAlive() {
        return alive;
    }
}