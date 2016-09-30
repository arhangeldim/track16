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
        StringBuilder text = new StringBuilder();

        // Создайте объект Parser
        Parser parser = new Parser();

        String line = reader.readLine();
        while (line != null){
            text.append(line);
            line = reader.readLine();
        }
        // Получите объект Document, реализовав метод parse()
        Document document = parser.parse(text.toString());
        System.out.println(Arrays.toString(document.getTokens()));


    }
}
