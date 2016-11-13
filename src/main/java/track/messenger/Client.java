package track.messenger;

import org.mockito.internal.util.io.IOUtil;
import track.messenger.messages.InfoMessage;
import track.messenger.messages.LoginMessage;
import track.messenger.messages.old.TextMessage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by geoolekom on 13.11.16.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), 19000);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        User user = null;
        try {
            String line;
            while (true) {
                line = bufferedReader.readLine();
                String action = line.split(" ")[0];
                String data = "";
                if (line.split(" ").length > 1) {
                    data = line.substring(action.length() + 1);
                }
                switch (action) {
                    case "/login":
                        oos.writeObject(new LoginMessage(user, data));
                        oos.flush();
                        break;
                    case "/text":
                        oos.writeObject(new TextMessage(user, data));
                        oos.flush();
                        break;
                    case "/info":
                        oos.writeObject(new InfoMessage(user, data));
                        oos.flush();
                        break;
                    default:
                        System.out.println("Неправильная команда!");
                        break;
                }
            }
        } catch (InterruptedIOException ioe) {
            System.out.println("Операция прервана!");
        } finally {
            IOUtil.closeQuietly(socket);
        }
    }
}
