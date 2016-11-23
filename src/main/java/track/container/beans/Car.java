package track.container.beans;

/**
 * Created by geoolekom on 12.10.16.
 */

public class Car {
    private Gear gear;
    private Engine engine;
    private Body body;

    public Car() {

    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Gear getGear() {
        return gear;
    }

    public Engine getEngine() {
        return engine;
    }

    public Body getBody() {
        return body;
    }
}
