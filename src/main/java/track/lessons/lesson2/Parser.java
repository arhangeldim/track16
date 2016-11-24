package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        String[] tokens = data.split("[ ]");
        if (tokens == null) {
            return null;
        }
        return new Document(tokens);
    }

    public static void main(String[] args) throws Exception {

        String path = "/home/alex/file3.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder builder = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            builder.append(line);
        }
        String result = builder.toString();
        Parser obj = new Parser();
        Document doc = obj.parse(result);

    }
}