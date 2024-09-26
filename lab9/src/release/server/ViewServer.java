package release.server;

/* AWT */
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/* SWING */
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import release.config.Config;
import release.logs.Log;

/* Window Server */
public class ViewServer {
    /* Ссылка на конфиг файл */
    private Config config;

    private JFrame win = new JFrame("Запуск сервера");
    /* Элементы окна */
    private JButton buttonStartServer = new JButton("Запустить сервер");
    private JButton buttonStopServer = new JButton("Остановить сервер");
    private JPanel panelButtons = new JPanel();
    /* Описание запрсоа */
    private JTextArea dialogWin = new JTextArea(10, 40);

    public ViewServer(Server server, Config config) {
        this.config = config;
        dialogWin.setEditable(false);
        dialogWin.setLineWrap(true); // автоматический перенос строки в JTextArea
        win.add(new JScrollPane(dialogWin), BorderLayout.CENTER);
        panelButtons.add(buttonStartServer);
        panelButtons.add(buttonStopServer);
        win.add(panelButtons, BorderLayout.SOUTH);
        win.pack();
        win.setLocationRelativeTo(null); // при запуске отображает окно по центру экрана
        win.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        /* Слушаем и глушим сервер при закрытии окна */
        win.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.stopServer();
                System.exit(0);
            }
        });
        win.setVisible(true);

        /* Обработка кнопки включения */
        buttonStartServer.addActionListener(e -> {
            int port = getPortFromOptionPane();
            server.startServer(port);
        });

        /* Обработка кнопки выключения */
        buttonStopServer.addActionListener(e -> server.stopServer());
    }

    /* Добавление сообщения в окно */
    public void refreshDialogWindowServer(String serviceMessage) {
        dialogWin.append(serviceMessage);
    }

    /* Ввод порта */
    protected int getPortFromOptionPane() {
        while (true) {
            Log.logServer("Попытка создание сервера");
            if (config.getServerPort() == 0) {
                String port = JOptionPane.showInputDialog(
                        win, "Введите значения для порта" + config.getServerPort(), "Введите порт сервера: ",
                        JOptionPane.QUESTION_MESSAGE);
                try {
                    return Integer.parseInt(port.trim());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            win, "Введен неккоректный порт сервера. Попробуйте еще раз.",
                            "Ошибка ввода порта сервера", JOptionPane.ERROR_MESSAGE);
                    Log.logServer("Ошибка ввода порта сервера");
                }
            } else {
                return config.getServerPort();
            }
        }
    }
}