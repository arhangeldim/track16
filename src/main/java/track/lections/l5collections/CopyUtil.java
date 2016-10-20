package track.lections.l5collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import track.lections.l5collections.animals.Cat;
import track.lections.l5collections.animals.Dog;
import track.lections.l5collections.animals.Pet;

/**
 *
 */
public class CopyUtil {

    static <T> void copy(List<T> dest, List<T> src) {
        for (T e : src) {
            dest.add(e);
        }
    }

    public static void main(String[] args) {
        List<Cat> cats = Arrays.asList(new Cat(), new Cat()); // 2 кота
        List<Dog> dogs = Arrays.asList(new Dog(), new Dog()); // 2 собаки


        List<? extends Pet> pets = new ArrayList<>();

//        copy(pets, cats);
//        copy(pets, dogs);
    }


}
