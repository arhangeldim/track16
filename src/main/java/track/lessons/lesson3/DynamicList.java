package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List{
        private static final int DEFAULT_SIZE = 1000;
        private int[] array;
        private int size;
        
        public DynamicList() {
                elements = new int[DEFAULT_SIZE];
        }
        
        @overried
        public void add(int value) {
                if(size == array.length) {
                        allocate();
                }
                array[size++] = value;
        }
        
        @overried
        public void remove(int idx) {
                valid_index(idx);
                int value = array[idx];
                System.arraycopy(array, idx + 1, array, idx, size-- - idx);
                return value;
        }
        
        @overried
        public int get(int idx) {
                valid_index(idx);
                return array[idx];
        }
        @overried
        public int size() {
                return size;
        }
        
        private void allocate() {
                int[] newarray = new int[2 * size];
                System.arraycopy(array, 0, newarray, 0, size);
                elements = newarray;
        }
        
        private valid_index(int idx) {
                if (idx < 0 || idx >= size) {
                        throw new IndexOutOfBoundsException("Invalid index");
                }
        }
}
