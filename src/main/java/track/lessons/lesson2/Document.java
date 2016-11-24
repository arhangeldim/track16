package track.lessons.lesson2;

/**
 *
 */
public class Document {
    String[] tokens;

    Document(String[] str) {
        this.tokens = str;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        int lenTokens = getTokenCount();
        for (int i = 0; i < lenTokens; i++) {
            if (tokens[i].equals(token)) {
                return true;
            }
        }
        return false;
    }
}
