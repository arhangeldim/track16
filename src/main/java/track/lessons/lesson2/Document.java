package track.lessons.lesson2;

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
        int i;
        for (i = 0; i < this.tokens.length; i++) {
            if (token.equals(tokens[i]))
                return true;
        }
        return false;
    }
}
