package track.lections.l2objects;

/**
 *
 */
public class Sample {

    public static void main(String[] args) {

        binaryNumbers();
        floatNumbers();
        System.out.println(fact(10));
    }


    public static void floatNumbers() {
        float f1 = 0.7f;
        float f2 = 0.3f + 0.4f;
        System.out.println("f1==f2: " + (f1 == f2));

        float f3 = 123456789.2f * (0.003f + 0.004f);
        float f4 = (123456789.2f * 0.003f) + (123456789.2f * 0.004f);
        System.out.println("f3==f4: " + (f3 == f4));
    }

    public static void binaryNumbers() {
        printBinary((byte) -1);
        printBinary((byte) 127);
        printBinary((byte) -0);
        printBinary((byte) 0);
        printBinary((byte) (-128 >> 1)); // -128/2
    }

    public static void printBinary(byte num) {
        StringBuilder builder = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            builder.append(num >>> i & 0x01);
        }
        System.out.println(builder.toString() + " -> " + num);
    }

    public static long fact(int num) {
        if (num == 1) {
            return 1;
        } else {
            return num * fact(num - 1);
        }
    }
}
