package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        Document result = new Document();
        result.tokens = data.split(" ");
        return result;
    }

    public static void main(String[] args) throws Exception {

        String path = "path/to/file";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        StringBuilder line = new StringBuilder();
        String oneLine = reader.readLine();
        while (!oneLine.isEmpty()) {
            line.append(oneLine);
            oneLine = reader.readLine();
        }
        // Создайте объект Parser
        Parser parser = new Parser();
        // Получите объект Document, реализовав метод parse()
        Document result = new Document();
        while (line != null) {
            parser.parse(line.toString());
        }

        for (String item : result.getTokens()) {
            System.out.println(item);
        }

    }
}
