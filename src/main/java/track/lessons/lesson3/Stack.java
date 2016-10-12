package track.lessons.lesson3;

/**
 * Created by Андрей on 12.10.2016.
 */

// Стек - структура данных, удовлетворяющая правилу Last IN First OUT
public interface Stack {
    void push(int value); // положить значение наверх стека

    int pop() throws Exception; // вытащить верхнее значение со стека
}
