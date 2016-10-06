package track.lessons.lesson2;

/**
 *
 */
public class Document {
    String[] tokens;

    Document(String[] tokens){
        this.tokens = tokens;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        for(String tk : tokens){
            if (tk == token) return true;
        }
        return false;
    }
}
