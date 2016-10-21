package track.lections.l5collections;

import track.lections.l5collections.animals.Cat;
import track.lections.l5collections.animals.Dog;
import track.lections.l5collections.animals.Pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class BoxV2<T> {

    private List<T> content = new ArrayList<>();

    public static void main(String[] args) {
        List<Cat> cats = Arrays.asList(new Cat(), new Cat()); // 2 кота
        List<Dog> dogs = Arrays.asList(new Dog(), new Dog()); // 2 собаки

        BoxV2<Pet> pets = new BoxV2<>();
//        pets.putAll(cats);  //compile time Cat extends Pet, but List<Cat> NOT extends List<Pet>
//        pets.putAll(dogs);

    }

    public void putAll(List<T> items) {
        for (T item : items) {
            content.add(item);
        }
    }

}
