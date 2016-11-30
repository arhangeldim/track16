package track.messenger;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import track.container.Container;
import track.messenger.net.MessengerServer;
import track.messenger.net.ObjectProtocol;
import track.messenger.teacher.client.MessengerClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by geoolekom on 24.11.16.
 */
public class MainTest {

    private String host = "localhost";
    private int port = 19000;
    private int nusers = 2;

    private ExecutorService workers;
    private MessengerServer server;
    private BlockingQueue<String> logins;
    private BlockingQueue<MessengerClient> executed = new LinkedBlockingQueue<>();

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
        for (int i = 0; i < nusers; i++) {
            logins.add("user" + i);
        }
        serverThread.start();
    }

    @After
    public void tearDown() throws Exception {
        server.halt();
    }

    //@Test
    public void ConnectionTest() {
        executeClientsQuietly("");
        //executed.stream().map(MessengerClient::getRecieved).forEach(System.out::println);
        executed.forEach(client -> Assert.assertEquals(3, client.getRecieved()));
        System.out.println("+ Connection test passed.");
        executed.clear();
    }

    @Test
    public void AuthTest() {
        executeClients("/info\n");
        //executed.stream().map(MessengerClient::getRecieved).forEach(System.out::println);
        executed.forEach(client -> Assert.assertEquals(4, client.getRecieved()));
        System.out.println("+ Login test passed.");
        executed.clear();
    }

    private void executeClients(String command) {
        Runnable clientImpl = () -> {
            MessengerClient client = new MessengerClient();
            client.setHost(host);
            client.setPort(port);
            client.setProtocol(new ObjectProtocol());
            try {
                client.start(IOUtils.toInputStream(
                        "/login " + logins.take() + " qwerty\n" +
                                command +
                                "/quit\n"
                ));
                executed.add(client);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        try {
            for (int i = 0; i < nusers; i++) {
                workers.submit(clientImpl);
                Thread.sleep(100);
            }
            workers.shutdown();
            workers.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void executeClientsQuietly(String command) {
        PrintStream out = System.out;
        try {
            System.setOut(new PrintStream(new FileOutputStream("/dev/null")));
        } catch (FileNotFoundException e) {
            //
        }
        executeClients("");
        System.setOut(out);
    }


}