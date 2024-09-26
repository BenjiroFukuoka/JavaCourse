package model;

import java.awt.Color;
import java.util.Random;

public enum TileType {
    TypeI(Color.CYAN, new boolean[][] {
            { false, false, false, false },
            { true, true, true, true },
            { false, false, false, false },
            { false, false, false, false },
    }),
    TypeO(Color.YELLOW, new boolean[][] {
            { true, true },
            { true, true },
    }),
    TypeT(new Color(102, 0, 153), new boolean[][] {
            { false, true, false },
            { true, true, true },
            { false, false, false },
    }),
    TypeS(Color.GREEN, new boolean[][] {
            { false, true, true },
            { true, true, false },
            { false, false, false },
    }),
    TypeZ(Color.RED, new boolean[][] {
            { true, true, false },
            { false, true, true },
            { false, false, false },
    }),
    TypeJ(Color.BLUE, new boolean[][] {
            { true, false, false },
            { true, true, true },
            { false, false, false },
    }),
    TypeL(Color.ORANGE, new boolean[][] {
            { false, false, true },
            { true, true, true },
            { false, false, false },
    });

    /** псевдослучаное число */
    private static final Random random = new Random();
    /** набор плиток */
    private final boolean[][] tiles;
    /** baseColor, lightColor - осветленный, darkColor - затемненный */
    private final Color baseColor, lightColor, darkColor;
    /** spawnCol, spawnRow - место появление новых фигур */
    private final int spawnCol, spawnRow;
    /** размерность N */
    private final int dimension;

    TileType(final Color color, final boolean[][] tiles) {
        this.baseColor = color; // Базовый цвет
        this.lightColor = color.brighter(); // Осветление цвета
        this.darkColor = color.darker(); // Затемнение цвета
        this.dimension = tiles.length; // Размерность
        this.tiles = tiles;
        this.spawnCol = 5 - (dimension >> 1); // условная середина
        this.spawnRow = getTopInset(0); // самый вверх сетки
    }

    /** Рандомизация фигур */
    public static TileType getRandomTile() {
        /* записываем все виды фигур из перечисления и записываем в массив фигур */
        final TileType[] shapes = TileType.values();
        return shapes[random.nextInt(shapes.length)]; // берем из массива фигур - рандомную фигуру
    }

    /* ГЕТТЕРЫ */
    public Color getBaseColor() {
        return baseColor;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public Color getDarkColor() {
        return darkColor;
    }

    public int getDimension() {
        return dimension;
    }

    public int getSpawnCol() {
        return spawnCol;
    }

    public int getSpawnRow() {
        return spawnRow;
    }

    /** Проверяет, содержит ли заданные координаты плитку */
    public boolean isTile(final int x, final int y, final int rotation) {
        return getRotatedTile(rotation)[x][y];
    }

    /** Возращает массив фигуры с заданным поворотом */
    public boolean[][] getRotatedTile(final int rotation) {
        boolean[][] rotateTiles = tiles;
        for (int i = 0; i < rotation; i++) {
            rotateTiles = rotateArray(rotateTiles);
        }
        return rotateTiles;
    }

    /** Поворт массива фигуры */
    static boolean[][] rotateArray(boolean[][] array) {
        int size = array.length;
        boolean[][] newArray = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newArray[j][size - i - 1] = array[i][j];
            }
        }
        return newArray;
    }

    /** Получить вставку слева */
    public int getLeftInset(final int rotation) {
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                if (isTile(x, y, rotation)) {
                    return x;
                }
            }
        }
        return -1;
    }

    /** Получить вставку справа */
    public int getRightInset(final int rotation) {
        for (int x = dimension - 1; x >= 0; x--) {
            for (int y = 0; y < dimension; y++) {
                if (isTile(x, y, rotation)) {
                    return dimension - x;
                }
            }
        }
        return -1;
    }

    /** Получить вставку сверху */
    public int getTopInset(final int rotation) {
        for (int y = 0; y < dimension; y++) {
            for (int x = 0; x < dimension; x++) {
                if (isTile(x, y, rotation)) {
                    return y;
                }
            }
        }
        return -1;
    }

    /** Получить вставку снизу */
    public int getBottomInset(final int rotation) {
        for (int y = dimension - 1; y >= 0; y--) {
            for (int x = 0; x < dimension; x++) {
                if (isTile(x, y, rotation)) {
                    return dimension - y;
                }
            }
        }
        return -1;
    }
}