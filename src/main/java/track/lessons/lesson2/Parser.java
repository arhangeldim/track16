import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Parser {

    Document parse(String data) {
        return new Document(data.split(" "));
    }

    public static void main(String[] args) throws Exception {

        String path = "test.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder stringBuilder = new StringBuilder();
        Parser parser = new Parser();
        String currentString = reader.readLine();
        while (currentString != null) {
            stringBuilder.append(currentString);
            currentString = reader.readLine();
        }

        Document document = parser.parse(stringBuilder.toString());
        System.out.println(Arrays.toString(document.getTokens()));
    }
}