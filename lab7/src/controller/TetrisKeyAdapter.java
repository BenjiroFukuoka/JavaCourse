package controller;

/* AWT */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/* FROM PACKAGE */
import view.Window;

public class TetrisKeyAdapter implements KeyListener {
    private Window window; // ссылка

    /* КОНСТРУКТОР */
    public TetrisKeyAdapter(Window window) {
        this.window = window;
    }

    /* ПРИ НАЖАТИИ КНОПКИ */
    @Override
    public void keyPressed(KeyEvent e) {
        /* Получаем ключ клавиши */
        switch (e.getKeyCode()) {
            /* SOFT DROP */
            case KeyEvent.VK_S:
                if (!window.isPaused() && window.getDropCooldown() == 0)
                    window.getLogicTimer().setCyclesPerSecond(30.0f);
                break;
            /* HARD DROP */
            case KeyEvent.VK_SPACE:
                int lowest = window.getBoard().getLowestTileTypePoint(window.getCurrentType(),
                        window.getCurrentCol(), window.getCurrentRow(), window.getCurrentRotation());
                window.setCurrentRow(lowest);
                break;
            /* MOVE LEFT */
            case KeyEvent.VK_A:
                if (!window.isPaused() && window.getBoard().isValidAndEmpty(window.getCurrentType(),
                        window.getCurrentCol() - 1, window.getCurrentRow(), window.getCurrentRotation())) {
                    window.decrementCurrentCol();
                }
                break;
            /* MOVE RIGHT */
            case KeyEvent.VK_D:
                if (!window.isPaused() && window.getBoard().isValidAndEmpty(window.getCurrentType(),
                        window.getCurrentCol() + 1, window.getCurrentRow(), window.getCurrentRotation())) {
                    window.incrementCurrentCol();
                }
                break;
            /* ROTATE ANTICLOCKWISE */
            case KeyEvent.VK_Q:
                if (!window.isPaused()) {
                    window.rotatePiece((window.getCurrentRotation() == 0) ? 3 : window.getCurrentRotation() - 1);
                }
                break;
            /* ROTATE CLOCKWISE */
            case KeyEvent.VK_E:
                if (!window.isPaused()) {
                    window.rotatePiece((window.getCurrentRotation() == 3) ? 0 : window.getCurrentRotation() + 1);
                }
                break;
            /* PAUSE GAME */
            case KeyEvent.VK_P:
                if (!window.isGameOver() && !window.isNewGame()) {
                    window.setPaused(!window.isPaused());
                    window.getLogicTimer().setPaused(window.isPaused());
                }
                break;
            /* START GAME */
            case KeyEvent.VK_ENTER:
                if (window.isGameOver() || window.isNewGame()) {
                    window.resetGame();
                }
                break;
        }
    }

    /* ПРИ ОТПУСКАНИИ КНОПКИ */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            /* ВЕРНУТЬ СТАНДАРТНУЮ СКОРОСТЬ ИГРЫ */
            case KeyEvent.VK_S:
                window.getLogicTimer().setCyclesPerSecond(window.getGamespeed());
                window.getLogicTimer().reset();
                break;
        }
    }

    /* EMPTY */
    @Override
    public void keyTyped(KeyEvent e) {
    }
}
