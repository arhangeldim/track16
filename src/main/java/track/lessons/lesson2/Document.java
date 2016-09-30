package track.lessons.lesson2;

/**
 *
 */
public class Document {

    Document(String[] tokens_) {
        tokens = tokens_;
    }
    String[] tokens;

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {

        for (int i = 0; i < tokens.length; i++) {
            if (token != null && tokens[i] == token){
                return true;
            }
        }
        return false;
    }
}
