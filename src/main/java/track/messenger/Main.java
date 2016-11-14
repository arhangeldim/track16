package track.messenger;

import track.messenger.net.MessengerServer;
import track.messenger.store.UserStore;

/**
 *
 */

public class Main {

    public static void main(String[] args) throws Exception {
        MessengerServer messengerServer = new MessengerServer();
        messengerServer.start();
    }

}
