package track.messenger.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
    private User user;

    // сокет на клиента
    private Socket socket;

    /**
     * С каждым сокетом связано 2 канала in/out
     */
    private InputStream in;
    private OutputStream out;

    private Protocol protocol;

    public Session(Socket clientSocket) {
        try {
            socket = clientSocket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
            protocol = new JsonProtocol(); //TODO добавить в конфиг
        } catch (Exception e) {
            System.out.println("session init error: " + e.toString());
            close();
        }

    }

    public Message receiveMessage() throws IOException, ProtocolException {
        // буффер данных в 64 килобайта
        byte buf[] = new byte[64 * 1024]; // TODO: Magic number
        // читаем 64кб от клиента, результат - кол-во реально принятых данных
        int r = in.read(buf);
        Message msg = protocol.decode(buf);
        onMessage(msg);
        return msg;
    }

    public void send(Message msg) {
        // TODO: Отправить клиенту сообщение
        try {
            out.write(msg.toString().getBytes());
        } catch (Exception e) {
            System.out.println("send error: " + e.toString());
        }
    }

    public void onMessage(Message msg) {
        // TODO: Пришло некое сообщение от клиента, его нужно обработать
        System.out.println(Long.toString(msg.getSenderId()) + msg.toString());

    }

    public void close() {
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
        try {
            socket.close();
            in.close();
            out.close();
        } catch (Exception e) {
            System.out.println("session close error : " + e.toString());
        }
    }
}