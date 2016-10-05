package track.lessons.lesson2;

/**
 *
 */
public class Document {
    String[] tokens;

    Document(String[] _tokens) {
        tokens = _tokens;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        boolean result = false;

        for (int i = 0; i < tokens.length; ++i) {
            result = (tokens[i].equals(token)) || result;
        }

        return result;
    }
}
