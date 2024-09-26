package model;

/* ФУНКЦИОНАЛЬНЫЙ ИНТЕРФЕЙС - 1 абстрак. метод */
@FunctionalInterface
public interface TileIterator {
    void iterate(int row, int col);
}