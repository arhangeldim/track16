package track.lessons.lesson2;
/**
 *
 */

public class Document {
    String[] tokens;

    Document(String data) {
        tokens = data.split(" ");
    }

    String[] getTokens() {
        String[] strings = new String[this.getTokenCount()];
        int j = 0;
        for (String iterator : tokens) {
            strings[j] = iterator;
            j++;
        }
        return strings;
    }

    int getTokenCount() {
        int counter = 0;
        for (String iterator : tokens) {
            counter ++;
        }
        return counter;
    }

    boolean hasToken(String token) {
        for (String iterator : tokens) {
            if (token.equals(iterator)) return true;
        }
        return false;
    }
}
