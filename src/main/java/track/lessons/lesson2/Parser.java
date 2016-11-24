package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        String[] tokens = data.split(" ");
        return new Document(tokens);
    }

    public static void main(String[] args) throws Exception {

        String path = "/home/ksushar/Documents/perl";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String data = builder.toString();
        // Создайте объект Parser
        Parser myParser = new Parser();

        // Получите объект Document, реализовав метод parse()
        Document myDoc = myParser.parse(data);
        System.out.println(myDoc.getTokens()[0]);
    }
}
