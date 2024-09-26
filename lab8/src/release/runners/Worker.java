package release.runners;

import release.details.Car;
import release.details.Details.Accessory;
import release.details.Details.Body;
import release.details.Details.Engine;
import release.logs.Log;
import release.process.ListId;
import release.process.TimeSetting;
import release.runners.CarPut.Checker;
import release.warehouse.Warehouse.AccessoryWarehouse;
import release.warehouse.Warehouse.BodyWarehouse;
import release.warehouse.Warehouse.CarWarehouse;
import release.warehouse.Warehouse.EngineWarehouse;

public class Worker implements Runnable {
    private int id;
    private AccessoryWarehouse aWareHouse;
    private BodyWarehouse bWareHouse;
    private CarWarehouse cWareHouse;
    private EngineWarehouse eWareHouse;
    private Checker check;

    public Worker(AccessoryWarehouse aWareHouse, BodyWarehouse bWareHouse, CarWarehouse cWareHouse,
            EngineWarehouse eWareHouse,
            Checker check, int id) {
        this.aWareHouse = aWareHouse;
        this.bWareHouse = bWareHouse;
        this.cWareHouse = cWareHouse;
        this.eWareHouse = eWareHouse;
        this.check = check;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (check) {
                try {
                    check.wait();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            try {
                Thread.sleep(TimeSetting.getWorkerTime());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                Thread.currentThread().interrupt();
            }
            Accessory a = aWareHouse.take();
            Body b = bWareHouse.take();
            Engine e = eWareHouse.take();
            Car car = new Car(ListId.getcID(), a, b, e, id);
            cWareHouse.put(car);
            String tmp = String.format("Car: {%d} (Accessory: %d, Body: %d, Motor: %d) Worker: {%d})", ListId.gcID(),
                    a.getId(), b.getId(), e.getId(), id);
            Log.logWorker(tmp);
        }
    }
}