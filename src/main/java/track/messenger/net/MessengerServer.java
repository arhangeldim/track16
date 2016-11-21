package track.messenger.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class MessengerServer {

    public static final int PORT = 8888;

    public static void main(String[] args)
    {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println(serverSocket.getLocalSocketAddress());
            System.out.println(serverSocket.toString());
            Socket socket = serverSocket.accept();
            executorService.submit(new WorkingWithSocket(socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
