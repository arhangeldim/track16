package track.lections.l3oop;

/**
 *
 */
public interface ClickListener {

    int CONST = 10;
    // do smth on click
    void  onClick();

    default int foo() {
        return 10;
    }
}
