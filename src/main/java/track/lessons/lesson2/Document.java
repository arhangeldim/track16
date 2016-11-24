package track.lessons.lesson2;

import java.util.Arrays;

/**
 *
 */
public class Document {
    String[] tokens;

    Document(String[] newTokens) {
        tokens = newTokens;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        return Arrays.stream(tokens).anyMatch(token::equals);
    }
}
