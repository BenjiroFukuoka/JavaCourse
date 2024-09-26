package release.runners;

import release.warehouse.Warehouse.CarWarehouse;

public class CarPut implements Runnable {
    private CarWarehouse warehouse;
    private Checker check;

    public interface Checker {
    }

    public CarPut(CarWarehouse warehouse, Checker check) {
        this.warehouse = warehouse;
        this.check = check;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (warehouse) {
                while (warehouse.isFull()) {
                    try {
                        warehouse.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
                synchronized (check) {
                    check.notify();
                }
            }
        }
    }
}
