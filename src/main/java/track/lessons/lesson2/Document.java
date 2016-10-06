package track.lessons.lesson2;

import org.mockito.internal.matchers.Null;

/**
 *
 */
public class Document {
    String[] tokens;

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        if (token  == null) {
            return false;
        }
        for (String tok : tokens) {
            if (token.equals(tok)) {
                return true;
            }
        }
        return false;
    }
}
