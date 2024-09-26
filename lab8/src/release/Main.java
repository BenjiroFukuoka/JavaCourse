package release;

import release.gui.VisualChecker;
import release.gui.Window;
import release.process.PropertiesFactory;
import release.runners.CarPut;
import release.runners.CarPut.Checker;
import release.runners.Put.AccessoryPut;
import release.runners.Put.BodyPut;
import release.runners.Put.Dealer;
import release.runners.Put.EnginePut;
import release.runners.Worker;
import release.threadpool.ThreadPool;
import release.warehouse.Warehouse.AccessoryWarehouse;
import release.warehouse.Warehouse.BodyWarehouse;
import release.warehouse.Warehouse.CarWarehouse;
import release.warehouse.Warehouse.EngineWarehouse;

public class Main {
    public static void main(String[] args) {
        Window win = new Window();

        PropertiesFactory setting = new PropertiesFactory();

        Checker checker = new Checker() {
        };

        ThreadPool accessoryTP = new ThreadPool(setting.getAccessoryCount());
        ThreadPool bodyTP = new ThreadPool(setting.getBodyCount());
        ThreadPool engineTP = new ThreadPool(setting.getEngineCount());
        ThreadPool workerTP = new ThreadPool(setting.getWorkerCount());
        ThreadPool carTP = new ThreadPool(setting.getCarCount());
        ThreadPool dealerTP = new ThreadPool(setting.getDealerCount());

        AccessoryWarehouse aWareHouse = new AccessoryWarehouse(setting.getAccessoryWarehouseSize());
        BodyWarehouse bWareHouse = new BodyWarehouse(setting.getBodyWarehouseSize());
        EngineWarehouse eWareHouse = new EngineWarehouse(setting.getEngineWarehouseSize());
        CarWarehouse cWareHouse = new CarWarehouse(setting.getCarWarehouseSize());

        VisualChecker.start(win, setting, aWareHouse, bWareHouse, eWareHouse, cWareHouse);

        for (int i = 0; i < setting.getAccessoryCount(); i++) {
            AccessoryPut tmp = new AccessoryPut(aWareHouse, i);
            accessoryTP.execute(tmp);
        }
        for (int i = 0; i < setting.getBodyCount(); i++) {
            BodyPut tmp = new BodyPut(bWareHouse, i);
            bodyTP.execute(tmp);
        }
        for (int i = 0; i < setting.getEngineCount(); i++) {
            EnginePut tmp = new EnginePut(eWareHouse, i);
            engineTP.execute(tmp);
        }
        for (int i = 0; i < setting.getWorkerCount(); i++) {
            Worker tmp = new Worker(aWareHouse, bWareHouse, cWareHouse, eWareHouse, checker, i);
            workerTP.execute(tmp);
        }
        for (int i = 0; i < setting.getCarCount(); i++) {
            CarPut tmp = new CarPut(cWareHouse, checker);
            carTP.execute(tmp);
        }
        for (int i = 0; i < setting.getDealerCount(); i++) {
            Dealer tmp = new Dealer(cWareHouse, i);
            dealerTP.execute(tmp);
        }
        accessoryTP.runOff();
        bodyTP.runOff();
        engineTP.runOff();
        workerTP.runOff();
        dealerTP.runOff();
        carTP.runOff();
    }
}