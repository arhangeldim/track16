package track.lessons.lesson3;

/**
 * Created by Konstantin on 13.10.2016.
 */

// Стек - структура данных, удовлетворяющая правилу Last IN First OUT
interface Stack {
    void push(int value); // положить значение наверх стека

    int pop() throws WrongIndexException; // вытащить верхнее значение со стека
}

