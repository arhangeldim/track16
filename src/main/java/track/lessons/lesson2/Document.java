package track.lessons.lesson2;

/**
 *
 */
public class Document {
    private String[] tokens;

    public Document(String[] tokens){
        this.tokens = tokens;
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        return tokens.length > 0;
    }
}
