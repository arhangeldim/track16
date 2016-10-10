package track.lessons.lesson2;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Parser {
    //Second trying

    Document parse(String data) {

        String[] tokens = data.split(" ");

        Document document = new Document(tokens);

        return document;
    }

    public static void main(String[] args) throws Exception {

        String path = "/home/user/";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder stringBuilder = new StringBuilder();

        String temp;
        while ((temp = reader.readLine()) != null) {
            stringBuilder.append(temp + " ");
        }

        Parser parser = new Parser();
        Document document = parser.parse(stringBuilder.toString());
        //System.out.print(Arrays.toString(document.getTokens()));
    }
}
