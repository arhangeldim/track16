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
        return (tokens==null)?0:tokens.length;
    }

    boolean hasToken(String token) {
        if(tokens==null) return false;
        for(int i = 0; i<tokens.length; i++)
            if(token.equals(tokens[i]))
                return true;
        return false;
    }

    public void add_text(String data) {
        int count_of_spaces = 0;
        for(int i = 0; i<data.length(); i++)
        {
            if(data.charAt(i)==' ')
                count_of_spaces++;
        }
        count_of_spaces++;
        tokens = new String[count_of_spaces];

        int index_to_search = 0;
        int cur_index = 0;

        for(int i = 0; i<count_of_spaces-1; i++)
        {
            index_to_search = data.indexOf(' ', index_to_search);
            tokens[i] = data.substring(cur_index, index_to_search);
            index_to_search++;
            cur_index = index_to_search;
        }

        tokens[count_of_spaces-1] = data.substring(cur_index);

        System.out.println(count_of_spaces);
    }
}
