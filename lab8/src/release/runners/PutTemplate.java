package release.runners;

abstract class PutTemplate<T> implements Runnable {
    private T warehouse;
    private int id;

    PutTemplate(T warehouse, int id) {
        this.warehouse = warehouse;
        this.id = id;
    }

    @Override
    public void run() {
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized T getWarehouse() {
        return warehouse;
    }
}