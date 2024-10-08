package release.client;

import java.io.IOException;
import java.net.Socket;

import release.config.Config;
import release.connection.Connect;
import release.connection.MessageSerializable;
import release.connection.MessageType;

public class Client {
    private Connect connect;
    private static ViewClient model;
    private static ModelClient gui;
    private volatile boolean isConnect = false; // флаг отобаржающий состояние подключения клиента серверу
    private static Config config = new Config();

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    // точка входа в клиентское приложение
    public static void main(String[] args) {
        Client client = new Client();
        model = new ViewClient();
        gui = new ModelClient(client, config);
        gui.initFrameClient();
        while (true) {
            if (client.isConnect()) {
                client.nameUserRegistration();
                client.receiveMessageFromServer();
                client.setConnect(false);
            }
        }
    }

    // метод подключения клиента серверу
    protected void connectToServer() {
        // если клиент не подключен сервере то..
        if (!isConnect) {
            while (true) {
                try {
                    // вызываем окна ввода адреса, порта сервера
                    String addressServer = gui.getServerAddressFromOptionPane();
                    int port = gui.getPortServerFromOptionPane();
                    // создаем сокет и объект connect
                    Socket socket = new Socket(addressServer, port);
                    connect = new Connect(socket);
                    isConnect = true;
                    gui.addMessage("Сервисное сообщение: Вы подключились к серверу.\n");
                    break;
                } catch (Exception e) {
                    gui.errorDialogWindow(
                            "Произошла ошибка! Возможно Вы ввели не верный адрес сервера или порт. Попробуйте еще раз");
                    break;
                }
            }
        } else
            gui.errorDialogWindow("Вы уже подключены!");
    }

    // метод, реализующий регистрацию имени пользователя со стороны клиентского
    // приложения
    protected void nameUserRegistration() {
        while (true) {
            try {
                MessageSerializable message = connect.receive();
                // приняли от сервера сообщение, если это запрос имени, то вызываем окна ввода
                // имени, отправляем на сервер имя
                if (message.getTypeMessage() == MessageType.REQUEST_NAME_USER) {
                    String nameUser = gui.getNameUser();
                    connect.send(new MessageSerializable(MessageType.USER_NAME, nameUser));
                }
                // если сообщение - имя уже используется, выводим соответствующее оуно с ошибой,
                // повторяем ввод имени
                if (message.getTypeMessage() == MessageType.NAME_USED) {
                    gui.errorDialogWindow("Данное имя уже используется, введите другое");
                    String nameUser = gui.getNameUser();
                    connect.send(new MessageSerializable(MessageType.USER_NAME, nameUser));
                }
                // если имя принято, получаем множество всех подключившихся пользователей,
                // выходим из цикла
                if (message.getTypeMessage() == MessageType.NAME_ACCEPTED) {
                    gui.addMessage("Сервисное сообщение: ваше имя принято!\n");
                    model.setUsers(message.getListUsers());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                gui.errorDialogWindow("Произошла ошибка при регистрации имени. Попробуйте переподключиться");
                try {
                    connect.close();
                    isConnect = false;
                    break;
                } catch (IOException ex) {
                    gui.errorDialogWindow("Ошибка при закрытии соединения");
                }
            }

        }
    }

    // метод отправки сообщения предназначенного для других пользователей на сервер
    protected void sendMessageOnServer(String text) {
        try {
            connect.send(new MessageSerializable(MessageType.TEXT_MESSAGE, text));
        } catch (Exception e) {
            gui.errorDialogWindow("Ошибка при отправки сообщения");
        }
    }

    // метод принимающий с сервера собщение от других клиентов
    protected void receiveMessageFromServer() {
        while (isConnect) {
            try {
                MessageSerializable message = connect.receive();
                // если тип TEXT_MESSAGE, то добавляем текст сообщения в окно переписки
                if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                    gui.addMessage(message.getTextMessage());
                }
                // если сообщение с типо USER_ADDED добавляем сообщение в окно переписки о новом
                // пользователе
                if (message.getTypeMessage() == MessageType.USER_ADDED) {
                    model.addUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format("Сервисное сообщение: пользователь %s присоединился к чату.\n",
                            message.getTextMessage()));
                }
                // аналогично для отключения других пользователей
                if (message.getTypeMessage() == MessageType.REMOVED_USER) {
                    model.removeUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format("Сервисное сообщение: пользователь %s покинул чат.\n",
                            message.getTextMessage()));
                }
            } catch (Exception e) {
                gui.errorDialogWindow("Ошибка при приеме сообщения от сервера.");
                setConnect(false);
                gui.refreshListUsers(model.getUsers());
                break;
            }
        }
    }

    // метод реализующий отключение нашего клиента от чата
    protected void disableClient() {
        try {
            if (isConnect) {
                connect.send(new MessageSerializable(MessageType.DISABLE_USER));
                model.getUsers().clear();
                isConnect = false;
                gui.refreshListUsers(model.getUsers());
            } else
                gui.errorDialogWindow("Вы уже отключены.");
        } catch (Exception e) {
            gui.errorDialogWindow("Сервисное сообщение: произошла ошибка при отключении.");
        }
    }
}
