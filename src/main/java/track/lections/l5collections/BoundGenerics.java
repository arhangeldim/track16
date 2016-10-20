package track.lections.l5collections;

import java.util.ArrayList;
import java.util.List;

import track.lections.l5collections.animals.Animal;
import track.lections.l5collections.animals.Cat;
import track.lections.l5collections.animals.Dog;
import track.lections.l5collections.animals.Pet;

/**
 *
 */
public class BoundGenerics {

    static void fillPets(List<Pet> pets) {
        pets.add(new Dog());
        pets.add(new Cat());
    }

    static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (T e : src) {
            dest.add(e);
        }
    }

    public static void main(String[] args) {
        List<Cat> cats = new ArrayList<>();
        cats.add(new Cat());
        cats.add(new Cat());

        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog());
        dogs.add(new Dog());


        Box<Pet> pet = new Box<>();
        pet.setItem(new Cat());


//        List<Pet> pets = cats;

//        callPets(cats);
//        callPets(dogs);


        List<Animal> animals = new ArrayList<>();
        // Incompatible types
//        fillPets(animals);

    }

//    static void callPets(List<Pet> list) {
//        // Позовем домашних питомцев
//        for (Pet pet : list) {
//            pet.call();
//        }
//    }

    // Коллекция pets - поставщик данных (producer)
//    static <T extends Pet> void callPets(List<T> pets) {
//        for (T item : pets) {
//            item.call();
//        }
//
//        //pets.stream().forEach(Pet::call);
//    }


    // 1) Метод потребляет внешние данные, то есть читает их
    // 2) Внутри метода нам доступен лишь тип Pet, но не реальный тип (если передали наследника)
    static void callPets(List<Pet> pets) {
        for (Pet pet : pets) {
            pet.call();
        }
    }


    // Коллекция pets - потребитель данных (consumer)
//    static void fillPets(List<? super Pet> pets) {
//        pets.add(new Dog());
//        pets.add(new Cat());
//    }


}
