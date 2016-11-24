package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        Document result = new Document();
        result.tokens = data.split(" ");
        return result;
    }

    public static void main(String[] args) throws Exception {

        String path = "path/to/file";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder line = new StringBuilder();
        String oneLine = new String();
        while (true) {
            oneLine = reader.readLine();
            if (oneLine != null) {
                line.append(oneLine + " ");
            } else {
                break;
            }

        }
        // Создайте объект Parser
        Parser parser = new Parser();
        // Получите объект Document, реализовав метод parse()
        Document result = parser.parse(line.toString());


    }
}
