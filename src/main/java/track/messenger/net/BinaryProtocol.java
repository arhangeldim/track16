package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.Message;

import java.io.*;

/**
 * Created by asus on 17.12.16.
 */
public class BinaryProtocol implements Protocol {
    private static Logger log = LoggerFactory.getLogger(BinaryProtocol.class);
    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        Message msg = null;
        try {
            in = new ObjectInputStream(inputStream);
            msg = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ProtocolException(e);
        }
        return msg;
    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(outputStream);
            out.writeObject(msg);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ProtocolException(e);
        }
        return bytes;
    }
}
