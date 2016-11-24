package track.lessons.lesson2;

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
        for (String tokenItem: tokens) {
            if (token.equals(tokenItem)) {
                return true;
            }
        }
        return false;
    }
}
