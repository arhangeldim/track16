package track.lessons.lesson2;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.FileReader;

public class Parser {
    //Second trying

    Document parse(String data) {
        Document document = new Document();
        document.add_text(data);
        return document;
    }

    public static void main(String[] args) throws Exception {

        String path = "/home/user/";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String temp = reader.readLine();
            if (temp == null) {
                break;
            }
            stringBuilder.append(temp);
        }

        Parser parser = new Parser();
        parser.parse(stringBuilder.toString());

    }
}
