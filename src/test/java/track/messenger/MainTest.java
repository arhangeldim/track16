package track.messenger;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import track.container.Container;
import track.messenger.net.MessengerServer;
import track.messenger.net.ObjectProtocol;
import track.messenger.teacher.client.MessengerClient;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by geoolekom on 24.11.16.
 */
public class MainTest {

    ExecutorService workers;
    MessengerServer server;
    BlockingQueue<String> logins;
    String command = "";

    @Before
    public void setUp() throws Exception {
        workers = Executors.newCachedThreadPool();
        Container container = new Container("server.xml");
        server = (MessengerServer) container.getByName("messengerServer");
        Thread serverThread = new Thread(() -> {
            try {
                server.start();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        });
        logins = new LinkedBlockingQueue<>();
        for (int i = 0; i < 10; i++) {
            logins.add("user" + i);
        }
        serverThread.start();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void ManyClientsTest() {
        BlockingQueue<Integer> executed = new LinkedBlockingQueue<>();

        Runnable clientImpl = () -> {
            MessengerClient client = new MessengerClient();
            client.setHost("localhost");
            client.setPort(19000);
            client.setProtocol(new ObjectProtocol());
            try {
                client.start(IOUtils.toInputStream(
                        "/login " + logins.take() + " qwerty\n" +
                                //"/chat_create 3 4 5 6 7 8 9 10 11 12" +
                                //"/text 2 blabla\n" +
                                //"/text 2 " + Thread.currentThread().getName() + "\n" +
                                "/quit"
                ));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(client.getRecieved());
            executed.add(0);
        };

        try {
            for (int i = 0; i < 10; i++) {
                workers.submit(clientImpl);
                Thread.sleep(100);
            }
            workers.shutdown();
            workers.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("FINAL VALUE: " + executed.size());
            Thread.currentThread().interrupt();
        }
        System.out.println("FINAL VALUE: " + executed.size());
    }


}