package track.lessons.lesson2;

/**
 *
 */
public class Document {

    private String[] tokens;

    public Document(String[] tokens) {
        this.tokens = tokens;
    }

    String[] getTokens() {
        return tokens;
    }

    public int getTokenCount() {
        return (tokens == null) ? 0 : tokens.length;
    }

    public boolean hasToken(String token) {
        if (tokens == null) {
            return false;
        }
        for (int i = 0; i < tokens.length; i++) {
            if (token.equals(tokens[i])) {
                return true;
            }
        }
        return false;
    }
}
