package track.messenger.teacher.client;

import track.messenger.messages.Message;
import track.messenger.messages.server.ResultMessage;


public interface ReceiveCallback {
    void onRecieve(ResultMessage msg);
}
