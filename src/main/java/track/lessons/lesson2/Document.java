package track.lessons.lesson2;

/**
 *
 */
public class Document {
    String[] tokens;

    public Document(String[] inputLines) {
        tokens = inputLines;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        for ( String line : tokens) {
            if (line != null && line.equals(token)) {
                return true;
            }
        }
        return false;
    }
}
