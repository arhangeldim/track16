package track.messenger;

import track.messenger.net.MessengerServer;

/**
 *
 */

public class Main {

    public static void main(String[] args) throws Exception {
        MessengerServer messengerServer = new MessengerServer();
        messengerServer.start(19000);
    }

}
