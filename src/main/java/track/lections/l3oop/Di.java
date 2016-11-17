package track.lections.l3oop;

interface Gear {
    int nextGear();
}

/**
 *
 */
public class Di {

    public static void main(String[] args) {

        Engine engine = new Engine(300);

        Gear gear = new AutoGear();

        Gear variator = new Gear() {
            @Override
            public int nextGear() {
                return 1;
            }
        };

        Auto auto = new Auto(variator, engine);
    }
}

class Auto {
    private Gear gear;
    private Engine engine;

    // BAD!
    public Auto() {
        gear = new AutoGear();

    }

    // GOOD!
    public Auto(Gear gear, Engine engine) {
        this.gear = gear;
        this.engine = engine;
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "gear=" + gear +
                ", engine=" + engine +
                '}';
    }

    public void move() {
        System.out.println("gear: " + gear.nextGear());
    }


}

class Engine {
    int power;

    public Engine(int power) {
        this.power = power;
    }
}

class AutoGear implements Gear {
    int current = 0;

    @Override
    public int nextGear() {
        return current++;
    }

}
