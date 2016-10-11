package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    public Document parse(String data) {
        Document doc = new Document();
        doc.tokens = data.split(" ");
        return doc;
    }

    public static void main(String[] args) throws Exception {

        String path = "path/to/file";
        BufferedReader reader = new BufferedReader(new FileReader(path));

        StringBuilder builder = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                builder.append(line).append(" ");
            } else {
                break;
            }
         }

        Parser parser = new Parser();
        Document doc = parser.parse(builder.toString());
    }
}
