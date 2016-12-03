package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */

public class Parser {

    Document parse(String data) {
        return new Document(data.split("\\s"));
    }

    public static void main(String[] args) throws Exception {
        String path = "path/to/file";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        // reader умеет читать по строкам с помощью метода readLine()
        Parser pars = new Parser();
        // Создайте объект Parser
        StringBuilder data = new StringBuilder();
        String currLine;
        do {
            currLine = reader.readLine();
            data.append(currLine).append(' ');
        }
        while (!currLine.isEmpty());
        pars.parse(data.toString());
        // Получите объект Document, реализовав метод parse()
    }
}
