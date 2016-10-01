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
        return tokens.length;
    }

    boolean hasToken(String token) {
        if (token == null) return false;
        for (int j = 0; j < tokens.length; j++) {
            if (tokens[j].equals(token)){return true;}
        }
        return false;
    }
}
