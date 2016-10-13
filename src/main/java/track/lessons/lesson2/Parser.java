package track.lessons.lesson2;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

/**
 *  lipsum 69
 *  ya 618
 *  simple 2
 *  log 68
 *
 */

public class Parser {

    public static Document parse(String data) {
        return new Document(data);
    }

    public static void main(String[] args) throws Exception {
        String path = "/Users/iv/code/track16/test/log4j.xml";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = null;
        StringBuilder docStrings = new StringBuilder("");
        while ((line = reader.readLine()) != null) {
            docStrings.append(line + " ");
        }
        Document doc = parse(docStrings.toString());
        System.out.println(doc.getTokenCount());
        System.out.println(Arrays.toString(doc.getTokens()));
    }
}
