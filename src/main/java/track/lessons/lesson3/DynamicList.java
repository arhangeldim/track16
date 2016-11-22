package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List{

    private int[] array;
    public DynamicList(int length)
    {
        this.array = new int[length];
        this.size = 0;
    }

    @Override
    void add(int item)
    {
        if(size == array.length)
        {
            realloc();
        }
        array[size++] = item;
    }

    @Override
    int remove(int idx) {
        isValid(idx);

        int item = array[idx];
        System.arraycopy(array, idx + 1, array, idx, size-- - idx);
        return item;

    }

    @Override
    int get(int idx) {
        isValid(idx);
        return array[idx];
    }

    @Override
    int size() {
        return super.size();
    }

    @Override
    void print() {
        if(this.size == 0){
            System.out.print("List is empty\n");
        }
        for(int i = 0; i < this.size; i++){
            System.out.println(this.array[i]);
        }
    }

    @Override
    boolean isValid(int idx) {
        return super.isValid(idx);
    }

    private void realloc()
    {
        int[] array2 = new int[2 * size];
        System.arraycopy(array, 0, array2, 0, size);
        this.array = array2;
    }
}
