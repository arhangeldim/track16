package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        Document document = new Document(data.split(" +"));
        return document;
    }

    public static void main(String[] args) throws Exception {
        String path = args[0];
        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder data = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            data.append(line + " ");
        }
        Document document = new Parser().parse(data.toString());
    }
}
