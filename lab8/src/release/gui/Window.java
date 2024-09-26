package release.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import release.logs.Log;
import release.process.ListId;
import release.process.PropertiesFactory;
import release.process.TimeSetting;
import release.runners.Put.Dealer;

public class Window extends JFrame {
    private int accessorySize = 1;
    private int accessoryLimit = 1;
    private int bodySize = 1;
    private int bodyLimit = 1;
    private int engineSize = 1;
    private int engineLimit = 1;
    private int carSize = 1;
    private int carLimit = 1;

    JLabel accessory = new JLabel("accessory");
    JLabel body = new JLabel("body");
    JLabel engine = new JLabel("engine");
    JLabel car = new JLabel("car");
    JLabel dealer = new JLabel("dealer");
    JLabel info = new JLabel("OTHER INFORMATION");

    JLabel accessoryCount = new JLabel("Suppliers of Accessory: ");
    JLabel bodyCount = new JLabel("Suppliers of Body: ");
    JLabel engineCount = new JLabel("Suppliers of Engine: ");
    JLabel workerCount = new JLabel("Total Workers: ");
    JLabel dealerCount = new JLabel("Total Dealers: ");

    JButton accessoryButton = new JButton("Accessory duration: " + TimeSetting.getAccessoryTime());
    JButton bodyButton = new JButton("Body duration: " + TimeSetting.getBodyTime());
    JButton engineButton = new JButton("Engine duration: " + TimeSetting.getEngineTIme());
    JButton workerButton = new JButton("Worker duration: " + TimeSetting.getWorkerTime());
    JButton dealerButton = new JButton("Dealer duration: " + TimeSetting.getDealerTime());

    public Window() {
        super("Car Manufacture");
        setSize(300, 450);
        JPanel panel = new JPanel(new Layout());
        Font font = new Font("Consolas", Font.BOLD, 14);

        accessory.setFont(font);
        body.setFont(font);
        engine.setFont(font);
        car.setFont(font);
        dealer.setFont(font);
        info.setFont(font);
        accessoryCount.setFont(font);
        bodyCount.setFont(font);
        engineCount.setFont(font);
        workerCount.setFont(font);
        dealerCount.setFont(font);

        accessoryButton.addActionListener(new ListenerAction());
        bodyButton.addActionListener(new ListenerAction());
        engineButton.addActionListener(new ListenerAction());
        workerButton.addActionListener(new ListenerAction());
        dealerButton.addActionListener(new ListenerAction());

        panel.add(accessory);
        panel.add(accessoryButton);
        panel.add(body);
        panel.add(bodyButton);
        panel.add(engine);
        panel.add(engineButton);
        panel.add(car);
        panel.add(workerButton);
        panel.add(dealerButton);
        panel.add(dealer);
        panel.add(info);
        panel.add(accessoryCount);
        panel.add(bodyCount);
        panel.add(engineCount);
        panel.add(workerCount);
        panel.add(dealerCount);

        getContentPane().add(panel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setAccessoryInfo(int size, int limit) {
        accessorySize = size;
        accessoryLimit = limit;
        accessoryButton.setText("Accessory duration: " + TimeSetting.getAccessoryTime());
        accessory.setText("accessory: " + accessorySize + "/" + accessoryLimit + " ("
                + (((float) accessorySize / (float) accessoryLimit) * 100) + "%)" + " Всего: "
                + ListId.gaID());
    }

    public void setBodyInfo(int size, int limit) {
        bodySize = size;
        bodyLimit = limit;
        bodyButton.setText("Body duration: " + TimeSetting.getBodyTime());
        body.setText("Body: " + bodySize + "/" + bodyLimit + " ("
                + (((float) bodySize / (float) bodyLimit) * 100) + "%)" + " Всего: " + ListId.gbID());
    }

    public void setEngineInfo(int size, int limit) {
        engineSize = size;
        engineLimit = limit;
        engineButton.setText("Engine duration: " + TimeSetting.getEngineTIme());
        engine.setText("engine: " + engineSize + "/" + engineLimit + " ("
                + (((float) engineSize / (float) engineLimit) * 100) + "%)" + " Всего: " + ListId.geID());
    }

    public void setCarInfo(int size, int limit) {
        carSize = size;
        carLimit = limit;
        car.setText("Car: " + carSize + "/" + carLimit + " (" + (((float) carSize / (float) carLimit) * 100) + "%)"
                + " Всего: " + ListId.gcID());
    }

    public void setWorkerInfo() {
        workerButton.setText("Worker duration: " + TimeSetting.getWorkerTime());
    }

    public void setDealerInfo() {
        dealerButton.setText("Dealer duration: " + TimeSetting.getDealerTime());
        dealer.setText(" Всего: " + Dealer.getCount());
    }

    public void setSettingsFactory(PropertiesFactory setting) {
        accessoryCount.setText("Suppliers of Accessory: " + setting.getAccessoryCount());
        bodyCount.setText("Suppliers of Body: " + setting.getBodyCount());
        engineCount.setText("Suppliers of Engine: " + setting.getEngineCount());
        workerCount.setText("Total Workers: " + setting.getWorkerCount());
        dealerCount.setText("Total Dealers: " + setting.getDealerCount());
    }

    class Layout implements LayoutManager {
        private Dimension size = new Dimension();

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return resize(parent);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return resize(parent);
        }

        @Override
        public void layoutContainer(Container parent) {
            Component[] list = parent.getComponents();
            int currentY = 5;
            for (int i = 0; i < list.length; i++) {
                Dimension pref = list[i].getPreferredSize();
                list[i].setBounds(5, currentY, pref.width, pref.height);
                currentY += 5;
                currentY += pref.height;
            }
        }

        private Dimension resize(Container c) {
            Component[] list = c.getComponents();
            int maxWidth = 0;
            for (int i = 0; i < list.length; i++) {
                int width = list[i].getWidth();
                if (width > maxWidth)
                    maxWidth = width;
            }
            size.width = maxWidth + 5;
            int height = 0;
            for (int i = 0; i < list.length; i++) {
                height += 5;
                height += list[i].getHeight();
            }
            size.height = height;
            return size;
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {
            // Empty, but not used
        }

        @Override
        public void removeLayoutComponent(Component comp) {
            // Empty, but not used
        }
    }

    class ListenerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Log.logging().info("Была нажата кнопка: " + e.getActionCommand());
            System.out.println("Button clicked: " + e.getActionCommand());
            String message = JOptionPane.showInputDialog("Введите duration", 1000);
            String[] command = e.getActionCommand().split(" ");
            if (message == null) {
                return;
            }
            int time = Integer.parseInt(message);
            if (time >= 100 && time <= 100000) {
                switch (command[0]) {
                    case ("Accessory"):
                        System.out.println("Accessory duration было: " + TimeSetting.getAccessoryTime()
                                + " стало: " + time);
                        TimeSetting.setAccessoryTime(time);
                        Log.logging().info("Accessory duration set " + time);
                        break;
                    case ("Body"):
                        System.out.println("Body duration было: " + TimeSetting.getBodyTime() + " стало: " + time);
                        TimeSetting.setBodyTime(time);
                        Log.logging().info("Body duration set " + time);
                        break;
                    case ("Engine"):
                        System.out.println("Engine duration было: " + TimeSetting.getEngineTIme() + " стало: " + time);
                        TimeSetting.setEngineTIme(time);
                        Log.logging().info("Engine duration set " + time);
                        break;
                    case ("Dealer"):
                        System.out.println("Dealer duration было: " + TimeSetting.getDealerTime() + " стало: " + time);
                        TimeSetting.setDealerTime(time);
                        Log.logging().info("Dealer duration set " + time);
                        break;
                    case ("Worker"):
                        System.out.println("Worker duration было: " + TimeSetting.getWorkerTime() + " стало: " + time);
                        TimeSetting.setWorkerTime(time);
                        Log.logging().info("Worker duration set " + time);
                        break;
                    default:
                }
            }
        }
    }
}