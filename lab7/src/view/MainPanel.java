package view;

/* AWT */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

/* SWING */
import javax.swing.JPanel;

/* IMPORT CLASS FROM PACKAGE PROJECT */
import model.TileType;

public class MainPanel extends JPanel {
    /* Поле конфигурации панели */
    private static final int VISIBLE_ROW_COUNT = 20, // Видимые строки сетки
            HIDDEN_ROW_COUNT = 2; // Невидимые строки игровой сетки

    public static final int COL_COUNT = 10, // Кол-вл столбцов игрового сетки
            ROW_COUNT = VISIBLE_ROW_COUNT + HIDDEN_ROW_COUNT; // Всего строк сетки

    static final int TILE_SIZE = 24, // Размер плитки
            SHADE_WIDTH = 4; // Ширина штриховки

    private static final int BORDER_WIDTH = 5, // Ширина границы вокруг сетки
            CENTER_X = COL_COUNT * TILE_SIZE / 2, // Центр по X
            CENTER_Y = VISIBLE_ROW_COUNT * TILE_SIZE / 2, // Центр по Y
            PANEL_WIDTH = COL_COUNT * TILE_SIZE + BORDER_WIDTH * 2; // Ширина панели

    static final int PANEL_HEIGHT = VISIBLE_ROW_COUNT * TILE_SIZE + BORDER_WIDTH * 2; // Высота панели

    /* ШРИФТЫ */
    private static final Font LARGE_FONT = new Font("Times New Roman", Font.BOLD, 16),
            SMALL_FONT = new Font("Times New Roman", Font.BOLD, 12);

    /* ССЫЛКИ */
    private TileType[][] tiles;
    private Window window;

    /* Создание панели */
    MainPanel(Window window) {
        this.window = window;
        this.tiles = new TileType[ROW_COUNT][COL_COUNT]; // сетка
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); // установка размера
        setBackground(Color.BLACK); // цвет фона
    }

    /** Сброс доски */
    void clear() {
        for (int i = 0; i < ROW_COUNT; i++)
            for (int j = 0; j < COL_COUNT; j++)
                tiles[i][j] = null;
    }

    /**
     * Проверка может ли фигура быть размещена в координатах, rotation - положение
     */
    public boolean isValidAndEmpty(TileType tile, int x, int y, int rotation) {
        // Допустимость колонки
        if (notInValidColumn(tile, x, rotation))
            return false;
        // Допустимость строки
        if (notInValidRow(tile, y, rotation))
            return false;
        return doesNotConflictWithOtherTileTypes(tile, x, y, rotation);
    }

    /* Проверка по столбцу */
    private boolean notInValidColumn(TileType tile, int x, int rotation) {
        return x < -tile.getLeftInset(rotation)
                || x + tile.getDimension() - tile.getRightInset(rotation) >= COL_COUNT;
    }

    /* Проверка по строке */
    private boolean notInValidRow(TileType tile, int y, int rotation) {
        return y < -tile.getTopInset(rotation)
                || y + tile.getDimension() - tile.getBottomInset(rotation) >= ROW_COUNT;
    }

    /* Проверка с другими элементами фигур */
    private boolean doesNotConflictWithOtherTileTypes(TileType tile, int x, int y, int rotation) {
        for (int col = 0; col < tile.getDimension(); col++)
            for (int row = 0; row < tile.getDimension(); row++)
                if (tile.isTile(col, row, rotation) && isOccupied(x + col, y + row))
                    return false;
        return true;
    }

    /** Проверка на занятасть плитки */
    private boolean isOccupied(int x, int y) {
        return tiles[y][x] != null;
    }

    /** Добавление фигуры на игровое поле */
    void setPiece(TileType TileType, int x, int y, int rotation) {
        for (int col = 0; col < TileType.getDimension(); col++)
            for (int row = 0; row < TileType.getDimension(); row++)
                if (TileType.isTile(col, row, rotation))
                    setTileType(col + x, row + y, TileType);
    }

    /** Установка фигуры по столбцу и строке */
    private void setTileType(int x, int y, TileType TileType) {
        tiles[y][x] = TileType;
    }

    /** Количество заполненных строк для удаления */
    int checkLinesAndReturnHowManyRemoved() {
        int completedLines = 0;
        for (int row = 0; row < ROW_COUNT; row++)
            if (isRowFullAndRemoveIt(row))
                completedLines++;
        return completedLines;
    }

    /** Проверка на заполненость */
    private boolean isRowFullAndRemoveIt(int line) {
        for (int col = 0; col < COL_COUNT; col++)
            if (!isOccupied(col, line))
                return false;
        for (int row = line - 1; row >= 0; row--)
            for (int col = 0; col < COL_COUNT; col++)
                setTileType(col, row + 1, getTileType(col, row));
        return true;
    }

    /** Получает плитку по ее столбцу и строке */
    private TileType getTileType(int x, int y) {
        return tiles[y][x];
    }

    /** Отрисовка */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.translate(BORDER_WIDTH, BORDER_WIDTH); // установки точки начала отрисовки
        if (window.isPaused()) { // Если пауза отрисуем надпись
            drawPausedScreen(g);
        } else if (window.isNewGame() || window.isGameOver()) { // Если начало игры или гейм овер отрисуем надпись
            drawStartGameScreen(g);
        } else { // Иначе нарисуем сетку игрового поля
            drawGameScreen(g);
        }
        drawOutline(g);
    }

    /** Отрисовка игрового поля */
    private void drawGameScreen(Graphics g) {
        drawAllTileTypes(g);
        drawCurrentPiece(g);
        drawBackgroundGridAbovePieces(g);
    }

    /** Отрисока начала игры */
    private void drawStartGameScreen(Graphics g) {
        g.setFont(LARGE_FONT);
        g.setColor(Color.GREEN.brighter());
        String msg = window.isNewGame() ? "TETRIS" : "GAME OVER";
        g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 150);
        g.setFont(SMALL_FONT);
        msg = "Press Enter to Play" + (window.isNewGame() ? "" : " Again");
        g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 300);
    }

    /** Отрисовка паузы */
    private void drawPausedScreen(Graphics g) {
        g.setFont(LARGE_FONT);
        g.setColor(Color.GREEN.brighter());
        String msg = "PAUSED";
        g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, CENTER_Y);
    }

    /** Отрисовка фигур */
    private void drawAllTileTypes(Graphics g) {
        for (int x = 0; x < COL_COUNT; x++) {
            for (int y = HIDDEN_ROW_COUNT; y < ROW_COUNT; y++) {
                TileType TileType = getTileType(x, y);
                if (TileType != null) {
                    drawTileType(TileType, x * TILE_SIZE, (y - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
                }
            }
        }
    }

    /** Отрисока фигруы */
    private void drawCurrentPiece(Graphics g) {
        TileType TileType = window.getPieceType();
        int pieceCol = window.getPieceCol();
        int pieceRow = window.getPieceRow();
        int rotation = window.getPieceRotation();
        // Draw the piece onto the board.
        for (int col = 0; col < TileType.getDimension(); col++) {
            for (int row = 0; row < TileType.getDimension(); row++) {
                if (pieceRow + row >= 2 && TileType.isTile(col, row, rotation)) {
                    drawTileType(TileType, (pieceCol + col) * TILE_SIZE,
                            (pieceRow + row - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
                }
            }
        }
        drawHelpingGhost(g, TileType, pieceCol, pieceRow, rotation);
    }

    /** Отрисовка теневой фигуры */
    private void drawHelpingGhost(Graphics g, TileType TileType, int pieceCol, int pieceRow, int rotation) {
        Color base = TileType.getBaseColor();
        Color transparentBase = new Color(base.getRed(), base.getGreen(), base.getBlue(), 20);
        int lowest = getLowestTileTypePoint(TileType, pieceCol, pieceRow, rotation);
        drawGhostPieceAtPoint(g, TileType, pieceCol, rotation, transparentBase, lowest);
    }

    /** Отрисовка теневой фигуры */
    private void drawGhostPieceAtPoint(Graphics g, TileType TileType, int pieceCol, int rotation, Color transparentBase,
            int lowest) {
        for (int col = 0; col < TileType.getDimension(); col++) {
            for (int row = 0; row < TileType.getDimension(); row++) {
                if (lowest + row >= 2 && TileType.isTile(col, row, rotation)) {
                    drawTileType(transparentBase,
                            transparentBase.brighter(),
                            transparentBase.darker(),
                            (pieceCol + col) * TILE_SIZE,
                            (lowest + row - HIDDEN_ROW_COUNT) * TILE_SIZE,
                            g);
                }
            }
        }
    }

    /** Получение самой низкой точки */
    public int getLowestTileTypePoint(TileType TileType, int pieceCol, int pieceRow, int rotation) {
        for (int lowest = pieceRow; lowest < ROW_COUNT; lowest++) {
            if (isValidAndEmpty(TileType, pieceCol, lowest, rotation)) {
                continue;
            }
            return lowest - 1;
        }
        throw new RuntimeException();
    }

    /** Отрисовка задней сетки */
    private void drawBackgroundGridAbovePieces(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x < COL_COUNT; x++) {
            for (int y = 0; y < VISIBLE_ROW_COUNT; y++) {
                g.drawLine(0, y * TILE_SIZE, COL_COUNT * TILE_SIZE, y * TILE_SIZE);
                g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, VISIBLE_ROW_COUNT * TILE_SIZE);
            }
        }
    }

    private void drawOutline(Graphics g) {
        g.setColor(Color.GREEN.darker());
        g.drawRect(0, 0, TILE_SIZE * COL_COUNT, TILE_SIZE * VISIBLE_ROW_COUNT);
    }

    private void drawTileType(TileType TileType, int x, int y, Graphics g) {
        drawTileType(TileType.getBaseColor(), TileType.getLightColor(), TileType.getDarkColor(), x, y, g);
    }

    private void drawTileType(Color base, Color light, Color dark, int x, int y, Graphics g) {
        g.setColor(base);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        bottomRightDarkShadow(dark, x, y, g);
        topLeftLightningShadow(light, x, y, g);
    }

    private void bottomRightDarkShadow(Color dark, int x, int y, Graphics g) {
        g.setColor(dark);
        g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
        g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);
    }

    private void topLeftLightningShadow(Color light, int x, int y, Graphics g) {
        g.setColor(light);
        for (int i = 0; i < SHADE_WIDTH; i++) {
            g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
            g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
        }
    }
}