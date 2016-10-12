package track.lessons.lesson2;
import java.security.PublicKey;
/**
 *
 */
public class Document {
    public Document(String[] data) {
        tokens = data;
    }
    private String[] tokens;
    String[] getTokens() {
        return tokens;
    }
    int getTokenCount() {
        return tokens.length;
    }
    boolean hasToken(String token) {
        for (String s : tokens) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }
}