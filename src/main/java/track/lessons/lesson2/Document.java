package track.lessons.lesson2;

import java.util.Arrays;

/**
 *
 */
public class Document {
    Document(String[] tokens) {
        this.tokens = tokens;
    }

    String[] tokens;

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        return Arrays.asList(tokens).contains(token);
    }
}
