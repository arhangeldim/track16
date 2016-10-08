package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        Document document = new Document();
        document.tokens = data.split(" ");
        return document;
    }

    public static void main(String[] args) throws Exception {

        String path = "/home/oleg/input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            stringBuilder.append(line);
            stringBuilder.append(" ");
        }
        String result = stringBuilder.toString();
        // Создайте объект Parser
        Parser parser = new Parser();

        // Получите объект Document, реализовав метод parse()
        Document myDoc = parser.parse(result);
        for (String tok: myDoc.tokens) {
            System.out.println(tok);
        }

    }
}
