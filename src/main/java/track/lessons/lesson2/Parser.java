package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    public Document parse(String data) {

        Document document = new Document();
        document.tokens = data.split(" ");
        return document;
    }

    public static void main(String[] args) throws Exception {

        String path = "src\\main\\java\\track\\inputFile\\input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path)) ;
            // reader умеет читать по строкам с помощью метода readLine()
            String readSrting=reader.readLine();
            // Создайте объект Parser
            Parser parser = new Parser();
            Document document = parser.parse (readSrting);
             // Получите объект Document, реализовав метод parse()

    }

}
