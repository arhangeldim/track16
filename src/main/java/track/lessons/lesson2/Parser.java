package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        return new Document(data.split(" "));
    }

    public static void main(String[] args) throws Exception {

        String path = "test.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));

        // reader умеет читать по строкам с помощью метода readLine()
        StringBuilder stringBuilder = new StringBuilder();

        Parser parser = new Parser();
        // Создайте объект Parser
        String currentString = reader.readLine();
        while (currentString != null) {
            stringBuilder.append(currentString);
            currentString = reader.readLine();
        }

        // Получите объект Document, реализовав метод parse()
        Document document = parser.parse(stringBuilder.toString());
        System.out.println(Arrays.toString(document.getTokens()));
    }
}
