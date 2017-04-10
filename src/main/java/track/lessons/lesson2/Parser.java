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

        String path = "D:\\new.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));

        // reader умеет читать по строкам с помощью метода readLine()

        // Создайте объект Parser
        Parser parser = new Parser();
        String str;
        StringBuilder sb = new StringBuilder("");
        while ((str = reader.readLine()) != null) {
            sb.append(str);
            sb.append(" ");
        }
        Document doc = parser.parse(sb.toString());
        // reader умеет читать по строкам с помощью метода readLine()

        // Создайте объект Parser

        // Получите объект Document, реализовав метод parse()


    }
}
