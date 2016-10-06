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
        if (tokens == null)
            return  0;
        else
            return tokens.length;
    }

    boolean hasToken(String token) {
        if (tokens == null)
            return false;

        boolean flag = false;
        for (String string : tokens)
            flag = flag || string.equals(token);
        return flag;
    }
}
