package release.warehouse;

import release.details.Car;
import release.details.Details.Accessory;
import release.details.Details.Body;
import release.details.Details.Engine;

public class Warehouse {
    /* Склад корпусов машины */
    public static class BodyWarehouse extends WarehouseTemplate<Body> {
        public BodyWarehouse(int limit) {
            super(limit);
        }
    }

    /* Склад салонов машины */
    public static class AccessoryWarehouse extends WarehouseTemplate<Accessory> {
        public AccessoryWarehouse(int limit) {
            super(limit);
        }
    }

    /* Склад движоков машины */
    public static class EngineWarehouse extends WarehouseTemplate<Engine> {
        public EngineWarehouse(int limit) {
            super(limit);
        }
    }

    /* Склад машин */
    public static class CarWarehouse extends WarehouseTemplate<Car> {
        public CarWarehouse(int limit) {
            super(limit);
        }
    }
}