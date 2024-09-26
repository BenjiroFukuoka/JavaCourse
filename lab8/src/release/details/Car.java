package release.details;

import release.details.Details.Accessory;
import release.details.Details.Body;
import release.details.Details.Engine;

/* Машина и ее комплектующие */
public class Car extends DetailTemplate {
    /* Ссылки на детали машины */
    private Accessory accessory;
    private Body body;
    private Engine engine;
    /* Айди сборщика машины */
    private int workerId;
    // private int id // подразумевается

    public Car(int id, Accessory accessory, Body body, Engine engine, int workerId) {
        super(id);
        this.accessory = accessory;
        this.body = body;
        this.engine = engine;
        this.workerId = workerId;
    }

    /* Ссылка на салон */
    public Accessory getAccessory() {
        return accessory;
    }

    /* Ссылка на корпус */
    public Body getBody() {
        return body;
    }

    /* Ссылка на движок */
    public Engine getEngine() {
        return engine;
    }

    /* Айди работника */
    public int getWorkerId() {
        return workerId;
    }
}