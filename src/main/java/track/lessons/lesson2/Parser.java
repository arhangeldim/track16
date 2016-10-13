package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        Document newDocument = new Document(data.split(" "));
        return newDocument;
    }

    public static void main(String[] args) throws Exception {

        String path = "/Users/lopatkindaniil/Documents/input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder  readedString = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            readedString.append(line);
            readedString.append(" ");
        }
        // Создайте объект Parser
        Parser parser = new Parser();

        // Получите объект Document, реализовав метод parse()
        Document documentedFile = parser.parse(readedString.toString());

    }
}
