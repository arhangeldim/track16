package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.Message;

import java.io.*;

/**
 * Created by geoolekom on 13.11.16.
 */
public class ObjectProtocol implements Protocol {
    static Logger log = LoggerFactory.getLogger(ObjectProtocol.class);

    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Message msg = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
             msg = (Message) ois.readObject();
        } catch (IOException ioe) {
            System.out.println("Нет потока вывода или сообщеий в потоке вывода.");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Не найден класс сериализованного объекта.");
        }
        return msg;

    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(msg);
        } catch (IOException ioe) {
            System.out.println("Нет потока ввода или сообщеий в потоке ввода.");
        }
        return bos.toByteArray();
    }
}
