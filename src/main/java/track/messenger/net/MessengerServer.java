package track.messenger.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class MessengerServer {

    public static final int PORT = 8888;
    public static boolean interrupted = false;

    public static void main(String[] args) throws SQLException, ProtocolException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        SqliteManager sqliteManager = new SqliteManager();
        sqliteManager.connectToDb();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println(serverSocket.getLocalSocketAddress());
            System.out.println(serverSocket.toString());
            while(!interrupted)
            {
                Socket socket = serverSocket.accept();
                executorService.submit(new Session(socket, sqliteManager));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
