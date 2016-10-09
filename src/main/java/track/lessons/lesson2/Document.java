package track.lessons.lesson2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class Document {
    private String[] tokens;

    public void dump() {
        for (String s : tokens) {
            System.out.println(s);
        }
    }

    public Document(String[] tokens) {
        this.tokens = tokens;
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        for (String s : tokens) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }
}
