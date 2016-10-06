package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class ParserTest {

    static String data;

    @BeforeClass
    public static void init() throws Exception {
        ClassLoader classLoader = Parser.class.getClassLoader();
        File file = new File(classLoader.getResource("log4j.xml").getFile());

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(" ");
        }
        data = builder.toString();
    }

    @Test
    public void parse() throws Exception {
        Assert.assertTrue(data != null && data.length() > 0);
        Parser parser = new Parser();
        Document doc = parser.parse(data);
        Assert.assertEquals(246, doc.getTokenCount());

        System.out.println(doc.getTokens());

        Assert.assertTrue(doc.hasToken("</layout>"));
    }

}