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
        for (String element: tokens)
            if (token.equals(element))
                return true;
        return false;
    }

    void dump(){
        for (String element: tokens)
            System.out.println(element);
    }
}
