package release.gui;

import release.process.PropertiesFactory;
import release.warehouse.Warehouse.AccessoryWarehouse;
import release.warehouse.Warehouse.BodyWarehouse;
import release.warehouse.Warehouse.CarWarehouse;
import release.warehouse.Warehouse.EngineWarehouse;

public class VisualChecker {
    public static void start(Window window, PropertiesFactory setting, AccessoryWarehouse aWareHouse,
            BodyWarehouse bWareHouse, EngineWarehouse eWareHouse, CarWarehouse cWareHouse) {
        Thread checker = new Thread(() -> {
            window.setSettingsFactory(setting);
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                window.setAccessoryInfo(aWareHouse.getSize(), aWareHouse.getLimit());
                window.setBodyInfo(bWareHouse.getSize(), bWareHouse.getLimit());
                window.setEngineInfo(eWareHouse.getSize(), eWareHouse.getLimit());
                window.setCarInfo(cWareHouse.getSize(), cWareHouse.getLimit());
                window.setWorkerInfo();
                window.setDealerInfo();
                Thread.yield();
            }
        });
        checker.start();
    }
}