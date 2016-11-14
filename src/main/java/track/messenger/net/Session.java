package track.messenger.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
    private User user;

    // сокет на клиента
    private Socket socket;
    private Protocol protocol;

    /**
     * С каждым сокетом связано 2 канала in/out
     */
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

    public Message getMessage() throws Exception {
        //return (Message) ois.readObject();
        byte[] buf = new byte[MAX_MSG_SIZE];
        in.read(buf, 0, MAX_MSG_SIZE);
        return protocol.decode(buf);
    }

    public Session(ServerSocket serverSocket) throws Exception {
        socket = serverSocket.accept();
        System.out.println("Подключился клиент " + socket.getInetAddress());
        in = socket.getInputStream();
        out = socket.getOutputStream();
        protocol = new ObjectProtocol();
    }

    public void send(Message msg) throws ProtocolException, IOException {
        log.info(msg.getSenderId().toString());
        out.write(protocol.encode(msg));
        out.flush();
        // TODO: Отправить клиенту сообщение
    }

    public void onMessage(Message msg) throws ProtocolException, IOException {
        Type msgType = msg.getType();
        switch (msgType) {
            case MSG_LOGIN:
                LoginMessage lmsg = (LoginMessage) msg;
                user = auth(lmsg.getUsername(), lmsg.getPassword());
                send(new InfoResultMessage(user));
                break;
            case MSG_TEXT:
                TextMessage tmsg = (TextMessage) msg;
                System.out.println(tmsg.getText());
                break;
            case MSG_INFO:
                InfoMessage imsg = (InfoMessage) msg;
                send(new InfoResultMessage(user, User.database.getUser(imsg.getRequestedUser())));
                break;
            default:
                System.out.println("Неправильная команда!");
                break;
        }
    }

    public void close() throws IOException {
        IOUtil.closeQuietly(in);
        IOUtil.closeQuietly(out);
        IOUtil.closeQuietly(socket);
    }

    public User geoolekom = new User("geoolekom", "qwerty");

    public User auth(String username, String password) {
        if ("geoolekom".equals(username) && "qwerty".equals(password)) {
            System.out.println("Подключился geoolekom");
            return geoolekom;
        } else {
            System.out.println("Нет такого юзера");
            return null;
        }
    }
}