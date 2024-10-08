package release.client;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ModelClient {
    private Client client;
    private JFrame frame = new JFrame("Чат");
    private JTextArea messages = new JTextArea(30, 20);
    private JTextArea users = new JTextArea(30, 15);
    private JPanel panel = new JPanel();
    private JTextField textField = new JTextField(40);
    private JButton buttonDisable = new JButton("Отключиться");
    private JButton buttonConnect = new JButton("Подключиться");
    private release.config.Config config;

    public ModelClient(Client client, release.config.Config config) {
        this.client = client;
        this.config = config;
    }

    // метод, инициализирующий графический интерфейс клиентского приложения
    protected void initFrameClient() {
        messages.setEditable(false);
        users.setEditable(false);
        frame.add(new JScrollPane(messages), BorderLayout.CENTER);
        frame.add(new JScrollPane(users), BorderLayout.EAST);
        panel.add(textField);
        panel.add(buttonConnect);
        panel.add(buttonDisable);
        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null); // при запуске отображает окно по центру экрана
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // класс обработки события при закрытии окна приложения Сервера
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client.isConnect()) {
                    client.disableClient();
                }
                System.exit(0);
            }
        });
        frame.setVisible(true);
        buttonDisable.addActionListener(e -> client.disableClient());
        buttonConnect.addActionListener(e -> client.connectToServer());
        textField.addActionListener(e -> {
            client.sendMessageOnServer(textField.getText());
            textField.setText("");
        });
    }

    protected void addMessage(String text) {
        messages.append(text);
    }

    // метод обновляющий списо имен подлючившихся пользователей
    protected void refreshListUsers(Set<String> listUsers) {
        users.setText("");
        if (client.isConnect()) {
            StringBuilder text = new StringBuilder("Список пользователей:\n");
            for (String user : listUsers) {
                text.append(user + "\n");
            }
            users.append(text.toString());
        }
    }

    // вызывает окно для ввода адреса сервера
    protected String getServerAddressFromOptionPane() {
        while (true) {
            String addressServer = JOptionPane.showInputDialog(
                    frame, "Введите адрес сервера:",
                    "Ввод адреса сервера",
                    JOptionPane.QUESTION_MESSAGE);
            return addressServer.trim();
        }
    }

    // вызывает окно для ввода порта сервера
    protected int getPortServerFromOptionPane() {
        if (config.getServerPort() != 0) {
            return config.getServerPort();
        }
        while (true) {
            String port = JOptionPane.showInputDialog(
                    frame, "Введите порт сервера:",
                    "Ввод порта сервера",
                    JOptionPane.QUESTION_MESSAGE);
            try {
                return Integer.parseInt(port.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        frame, "Введен неккоректный порт сервера. Попробуйте еще раз.",
                        "Ошибка ввода порта сервера", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // вызывает окна для ввода имени пользователя
    protected String getNameUser() {
        return JOptionPane.showInputDialog(
                frame, "Введите имя пользователя:",
                "Ввод имени пользователя",
                JOptionPane.QUESTION_MESSAGE);
    }

    // вызывает окно ошибки с заданным текстом
    protected void errorDialogWindow(String text) {
        JOptionPane.showMessageDialog(
                frame, text,
                "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
