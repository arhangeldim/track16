package track.messenger.messages.server;

import track.messenger.messages.Message;
import track.messenger.messages.Type;

/**
 * Created on 28.12.2016.
 */
public abstract class ResultMessage extends Message {
    public Result result;
    public Status status;
    public String errorMessage;

    public ResultMessage(Type type) {
        super(type);
    }

    public static abstract class Result{
    }

    public static enum Status{
        OK,
        FAIL
    }
}
