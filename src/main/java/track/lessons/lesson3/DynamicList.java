package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    int[] content;
    int size = 0;
    static final int MINIMAL_SIZE = 10;

    public DynamicList() {
        content = null;
    }

    void add(int item) {
        if (content == null) {
            content = new int[MINIMAL_SIZE];
            content[0] = item;
        } else {
            if (size > content.length) {
                int[] newContent = new int[2 * content.length];
                System.arraycopy(content, 0, newContent, 0, content.length);
                content = newContent;
            }
            content[size - 1] = item;
        }
        size++;
    }

    int remove(int idx) {
        if ((idx >= size ) || (idx < 0)) {
            System.out.println("This list doesn't have element with this index.");
            return -1;
        }
        int value = content[idx];
        if (idx < size - 1) { //Эта проверка остается т.к. если мы удаляем последний
                              // элемент то можно просто уменьшить size.
            System.arraycopy(content, idx + 1 , content, idx, size - idx - 1);
        }
        size--;
        return value;
    }

    int get(int idx) {
        if ((idx >= content.length ) || (idx < 0)) {
            System.out.println("This list doesn't have element with this index.");
            return -1;
        }
        return content[idx];
    }

    int size() {
        return size;
    }
}
