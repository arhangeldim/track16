package track.lessons.lesson2;

import java.util.Arrays;

/**
 *
 */
public class Document {
    String[] tokens;

    public Document(String[] tokens) {
        this.tokens = tokens;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens == null ? 0 : tokens.length;
    }

    boolean hasToken(String token) {
        if (tokens == null || token == null)
            return false;
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] != null && tokens[i].equals(token))
                return true;
        }
        return false;
    }
}
