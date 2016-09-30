package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        return new Document(data.split(" "));
    }

    public static void main(String[] args) throws Exception {

        String path = "/home/stas/Workspace/track-java/track16/src/main/resources/log4j.xml";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()

        StringBuilder data = new StringBuilder();

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            data.append(line);
        }
        // Создайте объект Parser
        Parser parser = new Parser();
        // Получите объект Document, реализовав метод parse()
        Document document = parser.parse(data.toString());
        System.out.println(document.getTokens()[0]);
    }
}
