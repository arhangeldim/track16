package track.messenger.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.store.MessageStore;
import track.messenger.store.UserStore;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
public class Session {

    /**
     * Пользователь сессии, пока не прошел логин, user == null
     * После логина устанавливается реальный пользователь
     */
    private User user;
    private Socket socket;
    private UserStore userStore;
    private MessageStore messageStore;

    /**
     * С каждым сокетом связано 2 канала in/out
     */
    private InputStream in;
    private OutputStream out;

    private Protocol protocol;

    static Logger log = LoggerFactory.getLogger(MessengerServer.class);

    public Session(Socket clientSocket, UserStore userStore_, MessageStore messageStore_) {
        try {
            socket = clientSocket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
            protocol = new JsonProtocol(); //TODO добавить в конфиг
            userStore = userStore_;
            messageStore = messageStore_;
        } catch (Exception e) {
            log.error("session init: " + e.toString());
            close();
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message receiveMessage() throws IOException, ProtocolException {
        byte buf[] = new byte[64 * 1024]; // TODO: Magic number
        in.read(buf);
        Message msg = protocol.decode(buf);
        onMessage(msg);
        return msg;
    }

    public void send(Message msg) {
        try {
            out.write(protocol.encode(msg));
        } catch (Exception e) {
            log.error("send error: " + e.toString());
        }
    }

    public void onMessage(Message msg) {
        log.info(msg.toString());
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            log.error("session closing: " + e.toString());
        }
    }
}