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

        String path = "/home/alyona/Documents/Github/track16/src/main/java/track/lessons/lesson2/input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));

        StringBuilder result = new StringBuilder();
        String buffer = reader.readLine();
        while (buffer != null) {
            result.append(buffer);
            buffer = reader.readLine();
            result.append(" ");
        }

        Parser parser = new Parser();
        Document result_document = parser.parse(result.toString());
    }
}
