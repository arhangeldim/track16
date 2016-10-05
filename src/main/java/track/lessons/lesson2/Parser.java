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
        String path = "./test";
        BufferedReader reader = new BufferedReader(new FileReader(path));

        StringBuilder stringBuilder = new StringBuilder();
        String buffer;

        while ((buffer = reader.readLine()) != null) {
            stringBuilder.append(buffer);
            stringBuilder.append(" ");
        }

        Parser parser = new Parser();
        Document doc = parser.parse(stringBuilder.toString());
    }
}
