package track.messenger.net;

/**
 * Исключение, которое бросается, когда происходят ошибки кодирования/декодирования
 */
public class ProtocolException extends Exception {
    public ProtocolException(String msg) {
        super(msg);
    }

    public ProtocolException(Throwable ex) {
        super(ex);
    }
}
