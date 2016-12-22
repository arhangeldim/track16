package track.messenger;

import track.container.Container;
import track.container.JsonConfigReader;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;
import track.messenger.net.MessengerServer;

import java.io.File;

import static org.apache.log4j.helpers.Loader.getResource;

/**
 *
 */
public class Main{
    public static void main(String[] args) throws Exception {
        try {
            File file = new File(getResource("server.json").getFile());
            ConfigReader reader = new JsonConfigReader();
            Container container = new Container(reader.parseBeans(file));
            MessengerServer server;
            server = (MessengerServer) container.getByClass("track.messenger.net.MessengerServer");
            server.start();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
//Написать бинарный протокол. Серверсокет в одно место. Сделать тредпул.