package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Parser {

    static Document parse(String data) {
        return new Document(data);
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder("");
        String data = null;
        while(reader.readLine() != null) {
            sb.append(data + " ");
        }
        parse(sb.toString());
    }
}
