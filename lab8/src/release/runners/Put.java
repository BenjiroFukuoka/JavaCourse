package release.runners;

import release.details.Details;
import release.details.Details.Accessory;
import release.details.Details.Body;
import release.details.Details.Engine;
import release.logs.Log;
import release.process.ListId;
import release.process.TimeSetting;
import release.warehouse.Warehouse.AccessoryWarehouse;
import release.warehouse.Warehouse.BodyWarehouse;
import release.warehouse.Warehouse.CarWarehouse;
import release.warehouse.Warehouse.EngineWarehouse;

public class Put {
    public static class AccessoryPut extends PutTemplate<AccessoryWarehouse> {
        public AccessoryPut(AccessoryWarehouse warehouse, int id) {
            super(warehouse, id);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(TimeSetting.getAccessoryTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                Accessory accessory = new Details.Accessory(ListId.getaID());
                getWarehouse().put(accessory);
            }
        }
    }

    public static class BodyPut extends PutTemplate<BodyWarehouse> {
        public BodyPut(BodyWarehouse warehouse, int id) {
            super(warehouse, id);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(TimeSetting.getBodyTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                Body body = new Details.Body(ListId.getbID());
                getWarehouse().put(body);
            }
        }
    }

    public static class EnginePut extends PutTemplate<EngineWarehouse> {
        public EnginePut(EngineWarehouse warehouse, int id) {
            super(warehouse, id);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(TimeSetting.getEngineTIme());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                Engine engine = new Details.Engine(ListId.geteID());
                getWarehouse().put(engine);
            }
        }
    }

    public static class Dealer extends PutTemplate<CarWarehouse> {
        private static int count = 0;

        public Dealer(CarWarehouse warehouse, int id) {
            super(warehouse, id);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(TimeSetting.getDealerTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                synchronized (getWarehouse()) {
                    var car = getWarehouse().take();
                    String logEntry = String.format(
                            "Dealer {%d}: Auto {%d} (Body: %d, Motor: %d, Accessory: %d) by Worker: {%d}",
                            getId(), car.getId(), car.getBody().getId(), car.getEngine().getId(),
                            car.getAccessory().getId(), car.getWorkerId());
                    count++;
                    Log.logDealer(logEntry);
                    getWarehouse().notifyAll();
                    Thread.yield();
                }
            }
        }

        public static int getCount() {
            return count;
        }
    }
}