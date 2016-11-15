package track.messenger;

import track.container.Container;
import track.messenger.net.MessengerServer;
import track.messenger.store.UserStore;

/**
 *
 */

public class Main {

    public static void main(String[] args) throws Exception {

        Container container = new Container("server.xml");
        MessengerServer server = (MessengerServer) container.getByName("messengerServer");
        server.start();
    }

}
