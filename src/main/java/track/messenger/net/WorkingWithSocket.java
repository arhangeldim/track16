package track.messenger.net;

import track.messenger.messages.Message;

import java.io.*;
import java.net.Socket;

/**
 * Created by user on 20.11.16.
 */
public class WorkingWithSocket implements Runnable {

    Socket socket;

    public WorkingWithSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true) {

            ObjectInputStream in = null;
            ObjectOutputStream out = null;
            try {
                System.out.println("new SOcket");
                in = new ObjectInputStream(socket.getInputStream());
                System.out.println(in.toString());
                out = new ObjectOutputStream(socket.getOutputStream());
                Message message = (Message) in.readObject();
                System.out.println(in.toString());

                out.close();
                in.close();
                if(0 == 1)
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
