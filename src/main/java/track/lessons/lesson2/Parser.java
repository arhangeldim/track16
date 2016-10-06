package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 */

public class Parser {

    Document parse(String data) {
	Document doc = new Document(data.trim().split(" \\s+"));
        return doc;
    }

    public static void main(String[] args) throws Exception {

        String path = "path/to/file";
        BufferedReader reader = new BufferedReader(new FileReader(path));
	Parser pars = new Parser();     
	String line, resline;	
	while ((line = reader.readLine()) != null)
	{
		resline += line;
	}   
	pars.parse(resline);
	// reader умеет читать по строкам с помощью метода readLine()

        // Создайте объект Parser

        // Получите объект Document, реализовав метод parse()


    }
}
