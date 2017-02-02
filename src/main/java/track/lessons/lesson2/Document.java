package track.lessons.lesson2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 */

public class Document {
    private String[] tokens;
    private static final String tokenRegexp = "[\\p{Alpha}\\-_'.]+\\b";
    Pattern pattern = Pattern.compile(tokenRegexp);

    private void parse(String data) {
        data = data.replaceAll("[^\\p{Alpha}\\-_]+", " ");
        tokens = data.trim().split("\\s+");
    }

    Document(String data) {
        parse(data);
    }

    String[] getTokens() {
        return tokens.clone();
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        for (String s: tokens) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }
}
