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
        String path = "/home/hellishnoob/test.lst";
        BufferedReader reader = new BufferedReader(new FileReader(path));

        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        Parser parser = new Parser();
        Document document = parser.parse(builder.toString());

        System.out.println("Token count: " + document.getTokenCount());
        System.out.println("Has token 'test': " + document.hasToken("test"));
        System.out.println("List of tokens:");
        for (String token : document.getTokens()) {
            System.out.println(token);
        }
    }
}
