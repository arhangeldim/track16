package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        return new Document(data.split("[ ,]"));
    }

    public static void main(String[] args) throws Exception {

        String path = "src/main/resources/lesson2.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        StringBuffer stringBuffer = new StringBuffer();
        String line = reader.readLine();
        while(line != null) {
            stringBuffer.append(line);
            line = reader.readLine();
        }
        reader.close();
        // Создайте объект Parser
        Parser parser = new Parser();
        Document document = parser.parse(stringBuffer.toString());
        // Получите объект Document, реализовав метод parse()
        System.out.println("Count " + document.getTokenCount());
        System.out.println(document.getTokens());
        System.out.println("has 0 ? " + document.hasToken("0"));
        System.out.println("has 8 ? " + document.hasToken("8"));
    }
}
