package track.messenger.net;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.messages.Message;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
public class Session {

    static Logger log = LoggerFactory.getLogger(Session.class);
    private static int count = 0;
    private int id;

    /**
     * Пользователь сессии, пока не прошел логин, user == null
     * После логина устанавливается реальный пользователь
     */
    private User user;

    // сокет на клиента
    private Socket socket;

    /**
     * С каждым сокетом связано 2 канала in/out
     */

    private InputStream inputStream;
    private OutputStream outputStream;
    private Protocol protocol;

    public Session(Socket socket, Protocol protocol) throws IOException {
        this.socket = socket;
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        this.protocol = protocol;
        id = count;
        count++;
    }

    public void send(Message msg) throws ProtocolException, IOException {
        outputStream.write(msg.toString().getBytes());
        log.info("Message sent: " + msg);
    }

    public Message receiveMessage() throws IOException, ProtocolException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Message message = protocol.decode(bufferedReader.readLine().getBytes());
        if (hasLogined()) {
            message.setSenderId(getUser().getId());
        }
        log.info("Message recieved: " + message);
        return message;
    }

    public Message pollMessage(int millis) throws IOException, ProtocolException {
        try {
            socket.setSoTimeout(millis);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Message message;
        try {
            String line = bufferedReader.readLine();
            if (line == null) {
                return null;
            }
            message = protocol.decode(line.getBytes());
        } catch (SocketTimeoutException e) {
            return null;
        }
        if (hasLogined()) {
            message.setSenderId(getUser().getId());
        }
        log.info("Message recieved: " + message);
        return message;
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean hasLogined() {
        return user != null;

    }

    public int getId() {
        return id;
    }
}