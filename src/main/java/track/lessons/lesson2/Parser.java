package track.lessons.lesson2;

import javax.print.Doc;
import javax.print.attribute.standard.DocumentName;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        String[] lines = data.split(" ");
        Document document = new Document(lines);
        return document;
    }

    public static void main(String[] args) throws Exception {

        String path = "path/to/file";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        String line;
        StringBuilder everything = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            everything.append(line);
        }
        Parser parser = new Parser();
        // Создайте объект Parser
        Document doc = parser.parse(everything.toString());
        // Получите объект Document, реализовав метод parse()
        System.out.print(doc.getTokenCount());

    }
}
