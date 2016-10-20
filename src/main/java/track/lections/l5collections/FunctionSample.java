package track.lections.l5collections;

import java.util.function.Function;

/**
 *
 */
public class FunctionSample {

    public static void main(String[] args) {
        String result = getFunction().apply(256);
        System.out.println(result);
    }

    public static Function<Integer, String> getFunction0() {
        Function<Integer, Byte> intToByte = (val) -> (byte) (val % 255);
        return intToByte.andThen(v -> "result: " + v);
    }

    public static Function<Integer, String> getFunction() {
        IntToByteFunction intToByte = new IntToByteFunction();
        ByteToStringFunction byteToString = new ByteToStringFunction();
        return intToByte.andThen(byteToString);
    }

}

class IntToByteFunction implements Function<Integer, Byte> {
    @Override
    public Byte apply(Integer val) {
        return (byte) (val % 255);
    }
}

class ByteToStringFunction implements Function<Byte, String> {
    @Override
    public String apply(Byte val) {
        return "result: " + Byte.toString(val);
    }
}
