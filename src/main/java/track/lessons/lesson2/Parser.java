package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        String[] tokens = data.split(' ');
        Document doc = new Document(tokens);
        return doc;
    }

    public static void main(String[] args) throws Exception {

        String path = "/home/andpago/Desktop/to_parse.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        StringBuilder builder = new StringBuilder();
        String line = "";
        while (line != null) {
            builder.append(line);
            line = reader.readLine();
        }
        // Создайте объект Parser
        Parser parser = new Parser();
        // Получите объект Document, реализовав метод parse()
        Document doc = parser.parse(builder.toString());

    }
}
