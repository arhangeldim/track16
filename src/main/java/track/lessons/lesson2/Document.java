package track.lessons.lesson2;

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
        if (tokens == null) {
            return false;
        }
        for (String elem : tokens) {
            if (elem.equals(token)) {
                return true;
            }
        }
        return false;
    }
}
