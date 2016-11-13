package track.messenger.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//import sun.nio.ch.IOUtil;
import org.mockito.internal.util.io.IOUtil;
import track.messenger.User;
import track.messenger.messages.Message;

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

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

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
        // TODO: Отправить клиенту сообщение
    }

    public void onMessage(Message msg) {
        // TODO: Пришло некое сообщение от клиента, его нужно обработать
    }

    public void close() throws IOException {
        IOUtil.closeQuietly(in);
        IOUtil.closeQuietly(out);
        IOUtil.closeQuietly(socket);
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }
}