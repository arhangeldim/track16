package track.messenger;

import org.apache.commons.io.IOUtils;
import org.junit.*;
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

    static private String host = "localhost";
    static private int port = 19000;
    static private int nusers = 2;

    static private MessengerServer server;
    static private BlockingQueue<String> logins;
    static private BlockingQueue<MessengerClient> executed = new LinkedBlockingQueue<>();

    @BeforeClass
    static public void setUpClass() throws Exception {
        Container container = new Container("server.xml");
        server = (MessengerServer) container.getByName("messengerServer");
        Thread serverThread = new Thread(() -> {
            try {
                server.start();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }

        });
        serverThread.start();

    }

    @AfterClass
    static public void tearDownClass() throws Exception {
        server.halt();
    }

    @Before
    public void setUp() throws Exception {
        logins = new LinkedBlockingQueue<>();
        for (int i = 0; i < nusers; i++) {
            logins.add("user" + i);
        }
    }

    @After
    public void tearDown() throws Exception {
        executed.clear();
    }

    @Test
    public void ConnectionTest() {
        executeClients("");
        //executed.stream().map(MessengerClient::getRecieved).forEach(System.out::println);
        executed.forEach(client -> Assert.assertEquals(3, client.getRecieved()));
        System.out.println("+ Connection test passed.");
    }

    @Test
    public void AuthTest() {
        executeClients("/info\n");
        //executed.stream().map(MessengerClient::getRecieved).forEach(System.out::println);
        executed.forEach(client -> Assert.assertEquals(4, client.getRecieved()));
        System.out.println("+ Login test passed.");
    }

    private void executeClients(String command) {
        ExecutorService workers = Executors.newFixedThreadPool(nusers);
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
            Thread.sleep(100);
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
        executeClients(command);
        System.setOut(out);
    }


}