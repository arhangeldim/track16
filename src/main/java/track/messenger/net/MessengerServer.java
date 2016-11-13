package track.messenger.net;

import org.mockito.internal.util.io.IOUtil;
import track.messenger.User;
import track.messenger.messages.*;

import java.net.ServerSocket;
import java.net.SocketException;

/**
 *
 */

public class MessengerServer {

    private ServerSocket serverSocket;

    public void start(Integer port) throws Exception {
        serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Ждем соединения...");

            while (true) {

                Session session = new Session(serverSocket);
                try {
                    while (true) {
                        Message msg = session.getMessage();
                        if (msg == null) {
                            break;
                        }
                        session.onMessage(msg);
                    }
                } catch (SocketException se) {
                    System.out.println("Клиент отключился.");
                }
            }
        } finally {
            IOUtil.closeQuietly(serverSocket);
        }
    }
}
