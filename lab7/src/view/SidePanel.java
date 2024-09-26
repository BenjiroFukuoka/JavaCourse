package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.TileIterator;
import model.TileType;

class SidePanel extends JPanel {
    private static final int TILE_SIZE = MainPanel.TILE_SIZE >> 1,
            SHADE_WIDTH = MainPanel.SHADE_WIDTH >> 1,
            TILE_COUNT = 5,
            SQUARE_CENTER_X = 130,
            SQUARE_CENTER_Y = 65,
            SQUARE_SIZE = (TILE_SIZE * TILE_COUNT >> 1);

    private static final int SMALL_INSET = 20,
            LARGE_INSET = 40,
            STATS_INSET = 150,
            CONTROLS_INSET = 250,
            TEXT_STRIDE = 25;

    private static final Font LARGE_FONT = new Font("Times New Roman", Font.BOLD, 13),
            SMALL_FONT = new Font("Times New Roman", Font.BOLD, 11);

    private final Color DRAW_COLOR = Color.GREEN.brighter();

    private Window window;

    SidePanel(Window window) {
        this.window = window;
        setPreferredSize(new Dimension(200, MainPanel.PANEL_HEIGHT));
        setBackground(Color.BLACK);
    }

    /* Написать статату */
    private void drawStats(Graphics g) {
        int offset = STATS_INSET;
        g.setFont(LARGE_FONT);
        g.drawString("Stats", SMALL_INSET, offset);
        g.setFont(LARGE_FONT);
        g.drawString("Score: " + window.getScore(), LARGE_INSET, offset += TEXT_STRIDE);
    }

    /* Написать управление */
    private void drawCategory(Graphics g) {
        int offset = CONTROLS_INSET;
        g.setFont(LARGE_FONT);
        g.drawString("Controls", SMALL_INSET, offset);
        g.setFont(SMALL_FONT);
        g.drawString("D - Move Right", LARGE_INSET, offset += TEXT_STRIDE);
        g.drawString("A - Move Left", LARGE_INSET, offset += TEXT_STRIDE);
        g.drawString("E - Rotate Clockwise", LARGE_INSET, offset += TEXT_STRIDE);
        g.drawString("Q - Rotate Anticlockwise", LARGE_INSET, offset += TEXT_STRIDE);
        g.drawString("S - Soft Drop", LARGE_INSET, offset += TEXT_STRIDE);
        g.drawString("SPACE - Hard Drop", LARGE_INSET, offset += TEXT_STRIDE);
        g.drawString("P - Pause Game", LARGE_INSET, offset += TEXT_STRIDE);
    }

    /* Отрисовка следующей фигуры */
    private void drawPreview(Graphics g) {
        drawPreviewBox(g);
        drawPreviewPiece(g);
    }

    /* Написать текст и сетку */
    private void drawPreviewBox(Graphics g) {
        g.setFont(LARGE_FONT);
        g.drawString("Next Piece:", SMALL_INSET, 70);
        g.drawRect(SQUARE_CENTER_X - SQUARE_SIZE, SQUARE_CENTER_Y - SQUARE_SIZE, SQUARE_SIZE * 2, SQUARE_SIZE * 2);
    }

    /* Отрисовать новую фигуры */
    private void drawPreviewPiece(Graphics g) {
        TileType type = window.getNextPieceType();
        if (!window.isGameOver() && type != null) {
            int dimension = type.getDimension();
            /* Найти начало отрисовки */
            int startX = (SQUARE_CENTER_X - (dimension * TILE_SIZE / 2));
            int startY = (SQUARE_CENTER_Y - (dimension * TILE_SIZE / 2));
            /* Получаем вставку */
            int top = type.getTopInset(0);
            int left = type.getLeftInset(0);
            /* Пройтись по плитке и отрисовать ее */
            forEachTile(dimension, (row, col) -> {
                if (type.isTile(col, row, 0)) {
                    drawTile(type, startX + ((col - left) * TILE_SIZE), startY + ((row - top) * TILE_SIZE), g);
                }
            });
        }
    }

    /* Лямбда-выражение функция в виде переменной */
    private void forEachTile(int count, TileIterator iterator) {
        for (int row = 0; row < count; row++) {
            for (int col = 0; col < count; col++) {
                iterator.iterate(row, col); // для объединения сразу двух переменных
            }
        }
    }

    /* Отрисовка плитки */
    private void drawTile(TileType type, int x, int y, Graphics g) {
        drawBasicTile(type, x, y, g);
        drawLightningShadow(type, x, y, g);
    }

    /* Отрисовка самой фигуры */
    private void drawBasicTile(TileType type, int x, int y, Graphics g) {
        g.setColor(type.getBaseColor());
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        g.setColor(type.getDarkColor());
        g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
        g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);
    }

    /* Создание шриховки */
    private void drawLightningShadow(TileType type, int x, int y, Graphics g) {
        g.setColor(type.getLightColor());
        for (int i = 0; i < SHADE_WIDTH; i++) {
            g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
            g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
        }
    }

    /* Выполнение отрисовки */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(DRAW_COLOR);
        drawStats(g);
        drawCategory(g);
        if(window.getLevel().equals("Easy"))
            drawPreview(g);
    }
}