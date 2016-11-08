package track.messenger.net;

import track.messenger.messages.Message;

/**
 *
 */
public interface Protocol {

    Message decode(byte[] bytes) throws ProtocolException;

    byte[] encode(Message msg) throws ProtocolException;

}
