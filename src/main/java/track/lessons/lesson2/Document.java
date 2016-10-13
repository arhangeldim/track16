package track.lessons.lesson2;

/**
 *
 */
public class Document {
    String [] tokens;
    int tokenNum;

    public Document(String data) {
        data = data.trim();
        int dataLen = data.length();
        if (dataLen == 0) {
            return;
        }

        tokenNum = 0;
        for (int inc = 0; inc < dataLen; inc++) {
            if (data.substring(inc, inc + 1).compareTo(" ") == 0) {
                tokenNum++;
            }
        }
        tokenNum++;
        tokens = new String[tokenNum];
        for (int inc = 0; inc < tokenNum; inc++) {
            int curSpace = data.indexOf(' ');
            if (inc != tokenNum - 1) {
                tokens[inc] = data.substring(0, curSpace);
            } else {
                tokens[inc] = data;
            }
            data = data.substring(curSpace + 1);
        }
    }

    String[] getTokens() {
        return tokens;
    }

    int getTokenCount() {
        return tokenNum;
    }

    boolean hasToken(String token) {
        if (token.length() != 0 && token.indexOf(' ') == -1) {
            return true;
        } else {
            return false;
        }
    }
}
