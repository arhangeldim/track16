package track.lessons.lesson2;

import javax.print.Doc;

/**
 *
 */
class Document {
    private String[] tokens;

    String[] getTokens() {
        return tokens;
    }

    public int getTokenCount() {
        return tokens.length;
    }

    boolean hasToken(String token) {
        for (int i = 0;i < tokens.length;++i) {
            if (token.equals(tokens[i])) {
                return true;
            }
        }
        return false;
    }

    Document(String[] tokens) {
        this.tokens = tokens;
    }

}
