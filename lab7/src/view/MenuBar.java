package view;

/* AWT */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* SWING */
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/* IMPORT FROM PACHAGE */
import model.HighScore;

class MenuBar extends JMenuBar {
    private Window window; // ссылка

    MenuBar(Window window) {
        this.window = window;
        /* СОЗДАНИЕ МЕНЮ содержит подменю */
        JMenu menu1 = new JMenu("Game");
        menu1.add(menuNewGame());
        menu1.add(menuAbout());
        menu1.add(menuExit());
        add(menu1); // добавление меню в менюбар
        add(menuScores()); // отдельная менюшка для score
    }

    private JMenuItem menuNewGame() {
        JMenuItem ngItem = new JMenuItem("New Game", new ImageIcon("src\\view\\img\\newg.png"));
        ngItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.resetGame();
                window.setPaused(true);
                window.setLevel();
                window.setProfile();
            }
        });
        return ngItem;
    }

    private JMenuItem menuScores() {
        JMenuItem scMenu = new JMenuItem("Score");
        scMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!window.isGameOver() && !window.isNewGame())
                    window.setPaused(true); // игра на паузу
                if (HighScore.getHighScores().size() != 0) {
                    for (int i = 0; i < HighScore.getHighScores().size(); i++) {
                        System.out.printf("Name: %s \t Age: %d \n", HighScore.getHighScores().get(i).getName(),
                                HighScore.getHighScores().get(i).getScore());
                    }
                }
                ScoreTable table = new ScoreTable();
                JScrollPane scrollTable = new JScrollPane(table);
                JFrame frame = new JFrame();
                frame.setLayout(new BorderLayout());
                frame.setResizable(false);
                frame.add(scrollTable);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        return scMenu;
    }

    private JMenuItem menuAbout() {
        JMenuItem abItem = new JMenuItem("About", new ImageIcon("src\\view\\img\\info.png"));
        abItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setPaused(true);
                JOptionPane.showMessageDialog(null, "Что-то написать тут!");
            }
        });
        return abItem;
    }

    private JMenuItem menuExit() {
        JMenuItem exItem = new JMenuItem("Exit", new ImageIcon("src\\view\\img\\log-out.png"));
        exItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return exItem;
    }
}