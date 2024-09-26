package view;

/* AWT */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

/* SWING */
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/* FROM PACKAGE */
import model.HighScore;

class ScoreTable extends JTable {
    private static final Font FONT = new Font("Times New Roman", Font.BOLD, 14);
    /** Заголовки столбцов */
    private static String[] col = new String[] { "Rating", "User name", "Score" };

    protected ScoreTable() {
        super(setData(), col); // Записываем заголовоки столбцов и содержание таблицы
        setFont(FONT); // Установка шрифта
        setShowHorizontalLines(false); // Удаление горизантальных линий таблицы
        align(); // Выравнивание
        setTableHeight(this, setData().length); // Размер ячеек
        setForeground(Color.GREEN); // Цвет данных
        setBackground(Color.BLACK); // Цвет фона
    }

    /** Получение данных и запись в таблицу */
    private static Object[][] setData() {
        ArrayList<HighScore> h = HighScore.getHighScores();
        if (h.size() != 0) {
            Object[][] data = new Object[h.size()][3];
            for (int i = 0, j = 1; i < h.size(); i++, j++) {
                data[i][0] = j;
                data[i][1] = h.get(i).getName();
                data[i][2] = h.get(i).getScore();
            }
            return data;
        }
        return null;
    }

    /** Выровнять содержание в ячейках */
    private void align() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 3; i++)
            getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
    }

    /** Установка размера ячеек */
    private void setTableHeight(JTable table, int rows) {
        int width = table.getPreferredSize().width;
        int height = rows * table.getRowHeight();
        table.setPreferredScrollableViewportSize(new Dimension(width, height));
    }
}
