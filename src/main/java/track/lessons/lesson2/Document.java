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
        return (tokens == null) ? 0 : tokens.length;
    }

    boolean hasToken(String token) {
        if (tokens == null) {
            return false;
        }

        for (String string : tokens) {
            if (string.equals(token)) {
                return true;
            }
        }
        return false;
    }
}
