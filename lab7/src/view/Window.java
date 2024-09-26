package view;

/* AWT */
import java.awt.BorderLayout;

/* SWING */
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/* FROM PACKAGE */
import controller.TetrisKeyAdapter;
import model.Clock;
import model.HighScore;
import model.TileType;

public class Window extends JFrame {
    /* ССЫЛКИ */
    private MenuBar menuBar;
    private MainPanel board;
    private SidePanel side;
    private TileType currentType, nextType;

    /* СОСТОЯНИЯ */
    private boolean isPaused,
            isNewGame,
            isGameOver;

    /* ВРЕМЯ */
    private Clock logicTimer;
    private float gamespeed = 2.0F;
    private int dropCooldown;

    /* СТАТИСТИКА */
    private int score;
    private String name = "";

    /* ХАРАКТЕРИСТИКИ для ФИГУР */
    private int currentCol, currentRow, currentRotation;

    private String level;
    private JButton btnMessage1 = null;
    private JButton btnMessage2 = null;

    public String getLevel() {
        return level;
    }

    /* КОНСТРУКТОР ФРЕЙМА -- ОКНА WINDOWS */
    public Window() {
        super("Tetris"); // Название для окна Windows
        setIconImage(new ImageIcon("src\\view\\img\\tetris-icon.png").getImage()); // Иконка для окна Windows
        setLayout(new BorderLayout()); // BorderLayout - менеджер для фрейма (5 мест расположения)
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Завершение приложения при закрытии окна
        setResizable(false); // Отключение динамического расширения
        // СОЗДАНИЕ КОМПОНЕНТОВ ОКНА
        this.board = new MainPanel(this); // Панель игрового поля
        this.side = new SidePanel(this); // Панель с инф. об управлении, след. фигуре, очкам
        this.menuBar = new MenuBar(this); // Меню бар
        // ПОДКЛЮЧЕНИЕ КОМПОНЕНТОВ
        add(board, BorderLayout.CENTER);
        add(side, BorderLayout.EAST);
        setJMenuBar(menuBar);
        // РЕАЦИЯ на КНОПКИ клавы
        addKeyListener(new TetrisKeyAdapter(this));
        // ДОП. АТРИБУТЫ ДЛЯ ФРЕЙМА
        pack(); // Упаковка
        setLocationRelativeTo(null); // Центрирование фрейма в центре экрана
        setVisible(true); // Видимость фрейма для юзера
    }

    /* ПРОВЕРКА СОСТОЯНИЙ */
    public boolean isPaused() {
        return isPaused;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isNewGame() {
        return isNewGame;
    }

    /* СЕТТЕРЫ */
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /* ГЕТТЕРЫ */
    public float getGamespeed() {
        return gamespeed;
    }

    public TileType getCurrentType() {
        return currentType;
    }

    public int getCurrentRotation() {
        return currentRotation;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public MainPanel getBoard() {
        return board;
    }

    public SidePanel getSide() {
        return side;
    }

    public int getDropCooldown() {
        return dropCooldown;
    }

    public Clock getLogicTimer() {
        return logicTimer;
    }

    int getScore() {
        return score;
    }

    TileType getPieceType() {
        return currentType;
    }

    TileType getNextPieceType() {
        return nextType;
    }

    int getPieceCol() {
        return currentCol;
    }

    int getPieceRow() {
        return currentRow;
    }

    int getPieceRotation() {
        return currentRotation;
    }

    /* ДОП. ФУНКЦИИ */
    public int decrementCurrentCol() {
        return currentCol--;
    }

    public int incrementCurrentCol() {
        return currentCol++;
    }

    /* НАЧАЛО ИГРЫ */
    public void startGame() {
        setLevel();
        while(true){
            if(level.equals("Easy") || level.equals("Hard"))
                break;
            else
                setLevel();
        }
        this.isNewGame = true; // Подтвердим начало игры
        this.logicTimer = new Clock(gamespeed); // установка времени игры (сколько в секунде будет тактов?)
        logicTimer.setPaused(true); // паузу для времени чтобы не насчитывала лишнее
        setProfile(); // запрос на ввод имени
        while (true) {
            Long start = System.nanoTime(); // Начало отсчета А
            logicTimer.update(); // Обновление часов
            /* Обработать только через такт */
            if (logicTimer.hasElapsedCycleAndDecrement()) {
                updateGame(); // удаление заполненных линий и логика игры
            }
            if (dropCooldown > 0) {
                dropCooldown--;
            }
            renderGame(); // отрисовка
            Long delta = (System.nanoTime() - start) / 1000000L; // Если прошло слишком мало времени
            if (delta < 20) {
                try {
                    Thread.sleep(20 - delta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* Запрос ввода имени */
    public void setProfile() {
        this.name = JOptionPane.showInputDialog(null, "Enter your name", "Tetris", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setLevel() {
        this.level = JOptionPane.showInputDialog(null, "Enter level", "Tetris", JOptionPane.QUESTION_MESSAGE);
    }

    /* Обновление игры, удаление строк, обновление очков, новая фигура */
    private void updateGame() {
        if (board.isValidAndEmpty(currentType, currentCol, currentRow + 1, currentRotation)) {
            currentRow++;
        } else {
            board.setPiece(currentType, currentCol, currentRow, currentRotation);
            int clearedLines = board.checkLinesAndReturnHowManyRemoved();
            score += 100 * clearedLines;
            logicTimer.reset();
            dropCooldown = 10;
            spawnPiece();
        }
    }

    /* Отрисовка игры */
    private void renderGame() {
        board.repaint();
        side.repaint();
        menuBar.repaint();
    }

    /* Сброс игры */
    public void resetGame() {
        this.score = 0;
        this.nextType = TileType.getRandomTile();
        this.isNewGame = false;
        this.isGameOver = false;
        board.clear();
        logicTimer.reset();
        logicTimer.setCyclesPerSecond(gamespeed);
        spawnPiece();
    }

    /* Создание фигур на поле */
    private void spawnPiece() {
        this.currentType = nextType;
        this.currentCol = currentType.getSpawnCol();
        this.currentRow = currentType.getSpawnRow();
        this.currentRotation = 0;
        this.nextType = TileType.getRandomTile();
        if (!board.isValidAndEmpty(currentType, currentCol, currentRow, currentRotation)) {
            HighScore.addHighScore(new HighScore(name, score));
            this.isGameOver = true;
            logicTimer.setPaused(true);
        }
    }

    /* Вращение */
    public void rotatePiece(int newRotation) {
        int newColumn = currentCol;
        int newRow = currentRow;
        int left = currentType.getLeftInset(newRotation);
        int right = currentType.getRightInset(newRotation);
        int top = currentType.getTopInset(newRotation);
        int bottom = currentType.getBottomInset(newRotation);
        /* Если плитка фигуры уходит за край слева */
        if (currentCol < -left) {
            newColumn -= currentCol - left;
            /* Если плитка фигуры уходит за край справа */
        } else if (currentCol + currentType.getDimension() - right >= MainPanel.COL_COUNT) {
            newColumn -= (currentCol + currentType.getDimension() - right) - MainPanel.COL_COUNT + 1;
        }

        /* Также для верха и низа */
        if (currentRow < -top) {
            newRow -= currentRow - top;
        } else if (currentRow + currentType.getDimension() - bottom >= MainPanel.ROW_COUNT) {
            newRow -= (currentRow + currentType.getDimension() - bottom) - MainPanel.ROW_COUNT + 1;
        }

        /* Проверка новой позиции на допустимость */
        if (board.isValidAndEmpty(currentType, newColumn, newRow, newRotation)) {
            currentRotation = newRotation;
            currentRow = newRow;
            currentCol = newColumn;
        }
    }
}