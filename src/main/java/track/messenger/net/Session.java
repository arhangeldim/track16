package track.messenger.net;

import org.mockito.internal.util.io.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.commands.CommandException;
import track.messenger.commands.CommandExecutor;
import track.messenger.db.MessageBase;
import track.messenger.db.UserBase;
import track.messenger.messages.Message;
import track.messenger.store.MessageFactory;
import track.messenger.store.MessageStore;
import track.messenger.store.UserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

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
    private User user;
    private InputStream in;
    private OutputStream out;
    // сокет на клиента
    private Socket socket;
    private Protocol protocol;
    private static final int MAX_MSG_SIZE = 32 * 1024;
    private UserFactory userFactory;
    private MessageFactory messageFactory;
    private MessageStore messageStore;
    private CommandExecutor commandExecutor;

    public Session(Socket clientSocket, CommandExecutor commandExecutor, UserBase userBase, MessageBase messageBase) throws IOException {
        try {
            socket = clientSocket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
            protocol = new BinaryProtocol();
            this.commandExecutor = commandExecutor;
            this.userFactory = new UserFactory(userBase);
            this.messageFactory = new MessageFactory(userBase, messageBase);

        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    /**
     * С каждым сокетом связано 2 канала in/out
     */

    public void send(Message msg) throws ProtocolException, IOException {
        // TODO: Отправить клиенту сообщение
        log.info("onsend");
        log.info("Message: {}", msg);
        /*if (msg.getSenderId() != null) {
            System.out.println("sent to: " + msg.getSenderId().toString());
        } else {
            System.out.println("sent to: " + null);
        }*/
        out.write(protocol.encode(msg));
        out.flush();
    }

    public void onMessage(Message msg) throws IOException, ProtocolException {
        // TODO: Пришло некое сообщение от клиента, его нужно обработать
        try {
            commandExecutor.handleMessage(msg, this);
        } catch (CommandException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public Message getMessage() throws IOException, ProtocolException {

        byte[] buf = new byte[MAX_MSG_SIZE];
        int read = 0;
        Message ret = null;
        try {
            read = in.read(buf, 0, MAX_MSG_SIZE);
            if (read > 0) {
                ret = protocol.decode(buf);
                log.info("message: {}", ret);
                onMessage(ret);
            }
        } catch (SocketTimeoutException ste) {
            ret = null;
        }
        return ret;
    }

    public void close() {
        IOUtil.closeQuietly(in);
        IOUtil.closeQuietly(out);
        IOUtil.closeQuietly(socket);
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }

    public UserFactory getUserFactory() {
        return userFactory;
    }

    public MessageFactory getMessageFactory() {
        return messageFactory;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public MessageStore getMessageStore() {
        return messageStore;
    }
}