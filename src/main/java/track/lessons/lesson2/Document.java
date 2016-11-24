package track.lessons.lesson2;

import java.util.Objects;

/**
 *
 */
public class Document {
    public String[] tokens;

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        for (String token1 : tokens) {
            if (Objects.equals(token, token1)) {
                return true;
            }
        }
        return false;
    }
}
