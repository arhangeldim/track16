package track.lections.l2objects;

/**
 * Пример описания класса
 * Имя public класса должно совпадать с именем файла, где он лежит
 * В каждом файле может быть только 1 public класс
 */
public class Complex {

    // поля класса
    public float re;
    public float im;

    // Конструктор по-умолчанию
    public Complex() {
        // просто создает объект в памяти, но не заполняет его данными
    }

    // Конструктор с аргументами
    // Используем this если имя аргумента совпадает с полем класс
    public Complex(float re, float im) {
        this.re = re;
        this.im = im;
    }

    // метод класса, ему не нужен объект
    static Complex add(Complex c1, Complex c2) {
        return new Complex(c1.re + c2.re, c1.im + c2.im);
    }

    // MAIN
    public static void main(String[] args) {

        Complex c1 = new Complex(1, 1);
        Complex c2 = new Complex(2, 2);
        System.out.println(c1 + "\n" + c2);

        print();

        c1.add(c2);
        System.out.println(c1 + "\n" + c2);

        print();

        Complex c3 = Complex.add(c1, c2);
        System.out.println(c1 + "\n" + c2 + "\n" + c3);

//        Так делать плохо! но это компилируется
//        Complex c4 = null;
//        c4.add(c1, c2);
//

    }

    static void print() {
        System.out.println("\n***************");
    }

    // метод, который печатает текущий объект
    @Override
    public String toString() {
        return "Complex{" +
                "re=" + re +
                ", im=" + im +
                '}';
    }

    // метод объекта, по сути операция над текущим объектом
    void add(Complex other) {

        re += other.re;
        im += other.im;
    }
}
