package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.File;
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
//        String path = "src/main/resources/file.in";
        ClassLoader classLoader = Parser.class.getClassLoader();
        File file = new File(classLoader.getResource("log4j.xml").getFile());
        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder data = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            data.append(line + " ");
        }
        Document document = new Parser().parse(data.toString());
    }
}
