package release.warehouse;

import java.util.concurrent.LinkedBlockingQueue;

/* Абстрактный класс для склада */
abstract class WarehouseTemplate<T> {
    /* Максимальный размер склада */
    private int limit;
    /* Создание списка */
    private LinkedBlockingQueue<T> queue;

    /* Создание склада с описанием вместимости и созданием списка */
    WarehouseTemplate(int limit) {
        this.limit = limit;
        this.queue = new LinkedBlockingQueue<>();
    }

    /* Добавление элемента в очередь */
    public synchronized void put(T item) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        queue.add(item);
        notifyAll();
    }

    /* Удаление элемента из очереди */
    public synchronized T take() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        var tmp = queue.poll();
        notifyAll();
        return tmp;
    }

    public synchronized boolean isFull() {
        return queue.size() >= limit;
    }

    public int getLimit() {
        return limit;
    }

    public synchronized int getSize() {
        return queue.size();
    }
}