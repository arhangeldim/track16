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

    public User geoolekom = new User("geoolekom", "qwerty");
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
                        Type msgType = msg.getType();
                        switch (msgType) {
                            case MSG_LOGIN:
                                LoginMessage lmsg = (LoginMessage) msg;
                                session.setUser(auth(lmsg.getUsername(), lmsg.getPassword()));
                                break;
                            case MSG_TEXT:
                                TextMessage tmsg = (TextMessage) msg;
                                System.out.println(tmsg.getText());
                                break;
                            case MSG_INFO:
                                InfoMessage imsg = (InfoMessage) msg;
                                System.out.println(imsg.getRequestedUser());
                                break;
                            default:
                                System.out.println("Неправильная команда!");
                                break;
                        }
                    }
                } catch (SocketException se) {
                    System.out.println("Клиент отключился.");
                }
            }
        } finally {
            IOUtil.closeQuietly(serverSocket);
        }
    }

    public User auth(String username, String password) {
        if ("geoolekom".equals(username) && "qwerty".equals(password)) {
            System.out.println("Подключился geoolekom");
            return geoolekom;
        } else {
            System.out.println("Нет такого юзера");
            return null;
        }
    }
}
