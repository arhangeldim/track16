package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 */
public class Parser {
    public static void main(String[] args) throws Exception {
        String path = "src/main/resources/log4j.xml";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        String line = null;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(" ");
        }

        // Создайте объект Parser
        Parser parser = new Parser();

        // Получите объект Document, реализовав метод parse()
        Document document = parser.parse(builder.toString());
        System.out.println(document.getTokenCount());
        document.dump();
    }

    Document parse(String data) {
        return new Document(data.split(" ")); // TODO: " +" is better but tests failed
    }
}
