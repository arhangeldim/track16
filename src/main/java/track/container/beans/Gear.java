package track.container.beans;

import java.util.Objects;

/**
 * Коробка передач, поле - количество скоростей
 */
public class Gear {
    private int count;

    public Gear() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Gear{" +
                "count=" + count +
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
        Gear gear = (Gear) obj;
        return count == gear.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }
}
