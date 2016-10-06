package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

public class Parser {
    Document parse(String data) {
        String[] tokens = data.split("[ ]");
        if(tokens == null) {
            return null;        
        }       
        return new Document(tokens);
    }

    public static void main(String[] args) throws Exception {

        String path = "path/to/file";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder builder = new StringBuilder();
        Parser pars = new Parser();     
        String line;	
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }   
        String res = builder.toString();
        Document doc = pars.parse(res);
   	}
}
