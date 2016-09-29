package track.lessons.lesson2;

/**
 *
 */
public class Document {
    String[] tokens;

    String[] getTokens() {
        return null;
    }

    int getTokenCount() {
        return (tokens == null) ? 0 : tokens.length;
    }

    boolean hasToken(String token) {
        if (tokens == null) {
            return false;
        }
        for (int i = 0; i < tokens.length; i++) {
            if (token.equals(tokens[i])) {
                return true;
            }
        }
        return false;
    }

    public void add_text(String data) {
        int countOfSpaces = 0;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == ' ') {
                countOfSpaces++;
            }
        }
        countOfSpaces++;
        tokens = new String[countOfSpaces];

        int indexToSearch = 0;
        int curIndex = 0;

        for (int i = 0; i < countOfSpaces - 1; i++) {
            indexToSearch = data.indexOf(' ', indexToSearch);
            tokens[i] = data.substring(curIndex, indexToSearch);
            indexToSearch++;
            curIndex = indexToSearch;
        }

        tokens[countOfSpaces - 1] = data.substring(curIndex);
    }
}
