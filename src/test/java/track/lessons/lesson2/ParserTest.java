package track.lessons.lesson2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *  lipsum 69
 *  ya 618
 *  simple 2
 *  log 68
 *
 */
public class ParserTest {

    static String data;
    static private final Map<String, Integer> tests = new HashMap<String, Integer>();
    static private final String[] resourses = new String[] {"simple.txt", "lipsum.txt", "log4j.xml", "ya.html"};
    static private final int[] tokens = {2, 69, 68, 618};
    @BeforeClass
    public static void init() throws Exception {
        ClassLoader classLoader = Parser.class.getClassLoader();
        int i = 0;
        for (String filename: resourses) {
            File file = new File(classLoader.getResource(filename).getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line + " ");
            }
            data = builder.toString();
            tests.put(new String(data), tokens[i]);
            i += 1;
        }
    }

    @Test
    public void parse() throws Exception {
        Assert.assertTrue(data != null && data.length() > 0);
        Parser parser = new Parser();
        Document doc = parser.parse(data);
        Assert.assertEquals(218, doc.getTokenCount());

        System.out.println(doc.getTokens());

        Assert.assertTrue(doc.hasToken("</layout>"));
    }

}