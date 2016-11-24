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

        String path = "path/to/file";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        StringBuilder stringBuilder = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null) {
            stringBuilder.append(str);
            stringBuilder.append(" ");
        }
        // Создайте объект Parser
        Parser parser = new Parser();
        // Получите объект Document, реализовав метод parse()
        Document document = parser.parse(stringBuilder.toString());

    }
}
