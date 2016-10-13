package track.lessons.lesson3;

/**
<<<<<<< HEAD
 * Created by frystile on 13.10.16.
 */

// Для удобства, в динамическом листе у меня есть фиктивная первая нода


class  DynamicList extends List {
    private int[] dynamics;
    private int size;
    private int reserve;

    public DynamicList(int[] array) {
        size = array.length;
        reserve = size * 2;
        dynamics = new int[reserve];
        System.arraycopy(array, 0, dynamics, 0, size);
    }

    DynamicList() {
        dynamics = new int[1];
        size = 0;
        reserve = 1;
    }

    @Override
    void add(int item) {
        if (size == reserve) {
            reserve = size * 2;
            int[] temp = new int[reserve];
            System.arraycopy(dynamics, 0, temp, 0, size);
            dynamics = temp;
        }
        dynamics[size] = item;
        ++size;
    }

    @Override
    int remove(int idx) {
        if (idx >= size || idx < 0) {
            System.err.println("Incorrect index");
            return 0;
        }

        int result = dynamics[idx];
        int[] temp = new int[reserve];
        System.arraycopy(dynamics, 0, temp, 0, idx);
        System.arraycopy(dynamics, idx + 1, temp, idx, size - idx - 1);
        dynamics = temp;
        --size;
        return result;
    }

    @Override
    int get(int idx) {
        if (idx >= size || idx < 0) {
            System.err.println("Incorrect index");
            return 0;
        }
        return dynamics[idx];
    }

    @Override
    int size() {
        return size;
    }
/*
=======

public class DynamicList {
>>>>>>> arch/master
            */
}
