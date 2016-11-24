package track.messenger;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import track.container.Container;
import track.messenger.net.MessengerServer;
import track.messenger.net.ObjectProtocol;
import track.messenger.teacher.client.MessengerClient;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by geoolekom on 24.11.16.
 */
public class MainTest {

    ExecutorService workers;
    MessengerServer server;
    BlockingQueue<String> logins;

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
        workers.submit(() -> {
            MessengerClient client = new MessengerClient();
            client.setHost("localhost");
            client.setPort(19000);
            client.setProtocol(new ObjectProtocol());
            client.start(IOUtils.toInputStream(
                    "/login one qwerty\n" +
                            "/text 2 blabla\n" +
                            "/text 2 " + Thread.currentThread().getName() + "\n" +
                            "/quit"
            ));
            System.out.println(client.getRecieved());
            executed.add(0);
        });
        workers.shutdown();
        try {
            workers.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("FUCK");
        }
        System.out.println(executed.size());
    }


}