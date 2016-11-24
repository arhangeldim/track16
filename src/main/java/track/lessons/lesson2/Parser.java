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

        String path = "path/to/file";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        StringBuilder str = new StringBuilder("");

        while (true) {
            String current = reader.readLine();
            if (current != null) {
                current.replace('\n',' ');
                str.append(current);
            } else {
                break;
            }
        }
        // Создайте объект Parser
        Parser parser = new Parser();

        // Получите объект Document, реализовав метод parse()
        String strNew = new String(str);
        Document doc = parser.parse(strNew);


    }
}
