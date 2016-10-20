package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */
public class Parser {

    Document parse(String data) {
        String[] tokens = data.split(" ");
        return new Document(tokens);
    }

    public static void main(String[] args) throws Exception {

        String path = "C:\\Users\\Sasha\\Documents\\track16\\src\\main\\java\\track\\lessons\\lesson2\\input";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()

        String read = reader.readLine();
        StringBuilder result = new StringBuilder();
        while (read != null) {
            result.append(read);
            result.append(" ");
            read = reader.readLine();
        }

        Parser parser = new Parser();
        Document document = parser.parse(result.toString());
        //System.out.println(document.hasToken("Java"));
        // Создайте объект Parser

        // Получите объект Document, реализовав метод parse()


    }
}
