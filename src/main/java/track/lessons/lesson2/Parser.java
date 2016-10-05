package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        Document document = new Document(data);
        return document;
    }

    public static void main(String[] args) throws Exception {

        String line;
        String path = "/home/geoolekom/track/java/test.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder builder = new StringBuilder();
        // reader умеет читать по строкам с помощью метода readLine()
        do {
            line = reader.readLine();
            if (line != null) {
                builder.append(line);
                builder.append(" ");
            }
        } while (line != null);

        Parser parser = new Parser();
        Document document = parser.parse(builder.toString());


    }
}
