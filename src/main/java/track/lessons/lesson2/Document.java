package track.lessons.lesson2;

/**
 *
 */
public class Document {
    String[] tokens;

    Document(String[] newTokens) {
        this.tokens = newTokens;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        for (int i = 0; i < tokens.length; ++i) {
            if (token != null && token.equals(tokens[i])) {
                return true;
            }
        }
        return false;
    }
}
