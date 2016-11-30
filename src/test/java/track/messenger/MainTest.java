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
    static private int nusers = 5;
    static private MessengerServer server;
    static private PrintStream stdout = System.out;

    private BlockingQueue<String> chats;
    private BlockingQueue<String> logins;
    private BlockingQueue<MessengerClient> executed = new LinkedBlockingQueue<>();
    private ExecutorService workers;
    private String command = "";

    private Runnable clientSession = () -> {
        String myCommand;
        synchronized (command) {
            myCommand = command + "/quit\n";
        }
        MessengerClient client = new MessengerClient();
        client.setHost(host);
        client.setPort(port);
        client.setProtocol(new ObjectProtocol());
        client.start(IOUtils.toInputStream(myCommand));
        executed.add(client);
    };

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
        chats = new LinkedBlockingQueue<>();

        for (Integer i = 0; i < nusers; i++) {
            logins.add("user" + i.toString());
            chats.add(Integer.toString(i+25));
        }

        workers = Executors.newFixedThreadPool(nusers);
        /*
        try {
            System.setOut(new PrintStream(new FileOutputStream("/dev/null")));
        } catch (FileNotFoundException e) {
            //
        }
        //*/
    }

    @After
    public void tearDown() throws Exception {
        executed.clear();
    }

    @Test
    public void connectionTest() {
        try {
            for (int i = 0; i < nusers; i++) {
                workers.submit(clientSession);
                Thread.sleep(500);
            }
            workers.shutdown();
            workers.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.setOut(stdout);
        executed.forEach(client -> Assert.assertEquals(1, client.getRecieved()));
        System.out.println("+ Connection test passed.");
    }

    @Test
    public void authTest() {
        try {
            for (int i = 0; i < nusers; i++) {
                command = "/login " + logins.take() + " qwerty \n" + "/info\n";
                workers.submit(clientSession);
                Thread.sleep(500);
            }
            workers.shutdown();
            workers.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.setOut(stdout);
        //executed.stream().map(MessengerClient::getRecieved).forEach(System.out::println);
        executed.forEach(client -> Assert.assertEquals(4, client.getRecieved()));
        System.out.println("+ Login test passed.");
    }

    @Test
    public void dialogTest() {
        try {
            for (int i = 0; i < nusers; i++) {
                String login = logins.take();
                command =
                        "/login " + login + " qwerty \n" + "/chat_create 2\n";
                workers.submit(clientSession);
                Thread.sleep(500);
            }
            workers.shutdown();
            workers.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.setOut(stdout);
        //executed.stream().map(MessengerClient::getRecieved).forEach(System.out::println);
        executed.forEach(client -> Assert.assertEquals(4, client.getRecieved()));
        System.out.println("+ Dialog test passed.");
    }

}