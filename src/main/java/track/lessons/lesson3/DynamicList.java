package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    int[] content;

    public DynamicList() {
        content = null;
    }

    void add(int item) {
        if (content == null) {
            content = new int[1];
            content[0] = item;
        } else {
            int[] newContent = new int[content.length + 1];
            System.arraycopy(content, 0, newContent, 0, content.length);
            content = newContent;
            content[content.length - 1] = item;
        }
    }

    int remove(int idx) {
        int[] newContent = new int[content.length - 1];
        if (idx >= content.length ) {
            System.out.println("This list doesn't have element with this index.");
            return -1;
        }
        System.arraycopy(content, 0, newContent, 0, idx);
        if (idx < content.length - 1) {
            System.arraycopy(content, idx + 1 , newContent, idx, content.length - idx - 1);
        }
        int value = content[idx];
        content = newContent;
        return value;
    }

    int get(int idx) {
        if (idx >= content.length ) {
            System.out.println("This list doesn't have element with this index.");
            return -1;
        }
        return content[idx];
    }

    int size() {
        return content.length;
    }
}
