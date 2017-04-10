package track.lessons.lesson2;

public class Document {
    String[] tokens;

    Document(String string) {
        tokens = string.split(" ");
    }


    String[] getTokens() {
        return tokens.clone();
    }

    int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        for (String match: tokens) {
            if (match.equals(token)) {
                return true;
            }
        }
        return false;
    }
}