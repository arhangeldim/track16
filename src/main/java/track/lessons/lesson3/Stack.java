package track.lessons.lesson3;

/**
 * Created by altair on 12.10.16.
 */
// Стек - структура данных, удовлетворяющая правилу Last IN First OUT
interface Stack {

    // положить значение наверх стека
    void push(int value);

    // вытащить верхнее значение со стека
    int pop();
}
