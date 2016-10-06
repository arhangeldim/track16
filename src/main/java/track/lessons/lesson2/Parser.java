package track.lessons.lesson2;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        Document doc = new Document();
        doc.tokens = (data == null) ? null : data.split(" ");
        return doc;
    }

    public static void main(String[] args) throws Exception {

        String path = "/home/frystile/test.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
            builder.append(line + " ");
        String data = builder.toString();
        // reader умеет читать по строкам с помощью метода readLine()

        // Создайте объект Parser
        Parser parser = new Parser();

        // Получите объект Document, реализовав метод parse()
        Document doc = parser.parse(data);

        System.out.println(doc.getTokenCount());
        System.out.println(doc.hasToken("world"));
        System.out.println(Arrays.toString(doc.getTokens()));

    }
}
