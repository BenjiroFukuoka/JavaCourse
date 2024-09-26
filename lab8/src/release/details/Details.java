package release.details;

public class Details {
    /* Корпус машины */
    public static class Body extends DetailTemplate {
        public Body(int id) {
            super(id);
        }
    }

    /* Салон машины */
    public static class Accessory extends DetailTemplate {
        public Accessory(int id) {
            super(id);
        }
    }

    /* Движок машины */
    public static class Engine extends DetailTemplate {
        public Engine(int id) {
            super(id);
        }
    }
}