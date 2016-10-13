package track.container.beans;

import java.util.Objects;

/**
 * Двигатель
 */
public class Engine {
    private int power;

    public Engine() {
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "power=" + power +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Engine engine = (Engine) obj;
        return power == engine.power;
    }

    @Override
    public int hashCode() {
        return Objects.hash(power);
    }
}
