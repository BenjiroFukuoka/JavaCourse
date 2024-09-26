package model;

/*
 * 1 секунда = 1000 миллисекунда
 * 1 миллсисекунда = 1000 микросекунд
 * 1 микросекунда = 1000 наносекунд
 */
public class Clock {
    /* Поле */
    private float millisecondsPerCycle; // миллисекунд в одном такте
    private long lastUpdate; // предыдущее nanoTime
    private int elapsedCycles; // пройденные такты
    private float excessCycles; // избыточный такты
    private boolean isPaused;

    /* Конструктор */
    public Clock(float cyclesPerSecond) {
        setCyclesPerSecond(cyclesPerSecond);
        reset();
    }

    /** Установка кол-во миллисекунд в одном такте */
    public void setCyclesPerSecond(float cyclesPerSecond) {
        millisecondsPerCycle = (1.0f / cyclesPerSecond) * 1000;
    }

    /** Обновление времени */
    public void update() {
        long currentTime = getCurrentTime(); // получение текущего времени
        float delta = (float) (currentTime - lastUpdate) + excessCycles; // разница между временем + лишнее время
        if (!isPaused) {
            elapsedCycles += (int) Math.floor(delta / millisecondsPerCycle); // сколько прошло
            excessCycles = delta % millisecondsPerCycle; // остаток
        }
        this.lastUpdate = currentTime;
    }

    /** Проверка тактов и единичное использование */
    public boolean hasElapsedCycleAndDecrement() {
        if (elapsedCycles > 0) {
            this.elapsedCycles--;
            return true;
        }
        return false;
    }

    /** Сброс */
    public void reset() {
        elapsedCycles = 0; // сброс пройденых
        excessCycles = 0; // сброс лишних
        lastUpdate = getCurrentTime(); // обновление текущего времени
        isPaused = false; // отмена паузы
    }

    /** Получаем текущее время в миллисекундах */
    private static long getCurrentTime() {
        return (System.nanoTime() / 1000000L);
    }

    /** Установка паузы */
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}