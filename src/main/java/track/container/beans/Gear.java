package track.container.beans;

import java.util.Objects;

public class Gear {
    private int count;
    private Car car;

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
