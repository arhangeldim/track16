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

        String path = "/Users/volodden/Desktop/Projects/Task_1_1_!/testik";
        BufferedReader reader = new BufferedReader(new FileReader(path));

        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        line = builder.toString();

        Parser parser = new Parser();
        Document doc = parser.parse(line);
    }
}
