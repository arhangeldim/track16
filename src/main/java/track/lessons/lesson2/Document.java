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
        return tokens.length;
    }

    boolean hasToken(String token) {
        for (String tok : tokens) {
            if ( tok.equals(token) ) {
                return true;
            }
        }
        return false;
    }
}
