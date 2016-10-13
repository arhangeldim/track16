package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        return new Document(data);
    }

    public static void main(String[] args) throws Exception {

        String path = "src/main/java/track/lessons/lesson2/test.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String line = reader.readLine();
        String data = "";
        while (line != null) {
            data = data.concat(line);
            line = reader.readLine();
        }

        Parser test = new Parser();
        Document parsed = test.parse(data);
        for (int inc = 0; inc < parsed.getTokenCount(); inc++) {
            System.out.println(parsed.getTokens()[inc]);
        }
    }
}
