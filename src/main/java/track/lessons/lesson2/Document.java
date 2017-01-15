package track.lessons.lesson2;

/**
 *
 */
public class Document {

    private String[] tokens;

    Document(String[] tokens) {
        this.tokens = tokens;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        for (String elem : tokens) {
            if (elem.equals(token)) {
                return true;
            }
        }
        return false;
    }
}
