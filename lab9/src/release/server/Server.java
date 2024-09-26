package release.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import release.config.Config;
import release.connection.Connect;
import release.connection.MessageSerializable;
import release.connection.MessageType;
import release.logs.Log;

public class Server {
    private ServerSocket serverSocket;
    private static ViewServer gui;
    private static ModelServer model;
    private static volatile boolean isServerStart = false;
    private static Config config = new Config();
    private ArrayList<String> history;

    /* Запуск сервера */
    void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            isServerStart = true;
            Log.logServer("Сервер запущин с портом: " + port);
            gui.refreshDialogWindowServer("Сервер запущен.\n");
        } catch (Exception e) {
            Log.logServer("Не удалось запустить сервер с портом: " + port);
            gui.refreshDialogWindowServer("Не удалось запустить сервер.\n");
        }
        history = new ArrayList<>();
    }

    /* Остановка сервера */
    void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                for (Map.Entry<String, Connect> user : model.getUsers().entrySet()) {
                    user.getValue().close();
                }
                serverSocket.close();
                model.getUsers().clear();
                Log.logServer("Сервер остановлен");
                gui.refreshDialogWindowServer("Сервер остановлен.\n");
            } else {
                Log.logServer("Сервер не запущен - останавливать нечего!");
                gui.refreshDialogWindowServer("Сервер не запущен - останавливать нечего!\n");
            }
        } catch (Exception e) {
            Log.logServer("Остановить сервер не удалось");
            gui.refreshDialogWindowServer("Остановить сервер не удалось.\n");
        }
    }

    /* Получение сокет-подключения от клиента */
    void acceptServer() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new ServerThread(socket).start();
            } catch (Exception e) {
                Log.logServer("Связь с сервером потеряна.");
                gui.refreshDialogWindowServer("Связь с сервером потеряна.\n");
                break;
            }
        }
    }

    /* Рассылка сообщение для всех юзеров */
    void sendMessageFromUsers(MessageSerializable message) {
        for (Map.Entry<String, Connect> user : model.getUsers().entrySet()) {
            try {
                user.getValue().send(message);
            } catch (Exception e) {
                Log.logServer("Ошибка отправки сообщения всем пользователям!");
                gui.refreshDialogWindowServer("Ошибка отправки сообщения всем пользователям!\n");
            }
        }
    }

    String getHistory() {
        String result = "";
        if (history.isEmpty())
            return null;
        for (Iterator<String> iterator = history.iterator(); iterator.hasNext();) {
            result += iterator.next() + "/n";
        }
        result = result.substring(0, result.length() - "/n".length());
        return result;
    }

    void setHistory(String message) {
        if (history.size() >= config.getHistoryMessages()) {
            history.remove(0);
            history.add(message);
        } else {
            history.add(message);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        gui = new ViewServer(server, config);
        model = new ModelServer();
        while (true) {
            if (isServerStart) {
                server.acceptServer();
                isServerStart = false;
            }
        }
    }

    private class ServerThread extends Thread {
        private Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        private String requestAndAddUsers(Connect connect) {
            while (true) {
                try {
                    /* Посылаем клиенту сообщение-запрос имени */
                    connect.send(new MessageSerializable(MessageType.REQUEST_NAME_USER));
                    MessageSerializable responseMessage = connect.receive();
                    String userName = responseMessage.getTextMessage();
                    /* Получили ответ с именем и проверяем не занято ли это имя другим клиентом */
                    if (responseMessage.getTypeMessage() == MessageType.USER_NAME && userName != null
                            && !userName.isEmpty() && !model.getUsers().containsKey(userName)) {
                        // Сохраняем имя в список
                        model.addUser(userName, connect);
                        HashSet<String> listUsers = new HashSet<>();
                        for (Map.Entry<String, Connect> users : model.getUsers().entrySet()) {
                            listUsers.add(users.getKey());
                        }
                        /* Отправляем клиенту список подключившихся пользователей */
                        connect.send(new MessageSerializable(MessageType.NAME_ACCEPTED, listUsers));
                        /* Отправляем всем клиентам сообщение о новом пользователе */
                        sendMessageFromUsers(new MessageSerializable(MessageType.USER_ADDED, userName));
                        return userName;
                    } else
                        connect.send(new MessageSerializable(MessageType.NAME_USED));
                } catch (Exception e) {
                    Log.logServer("Возникла ошибка при запросе и добавлении нового пользователя");
                    gui.refreshDialogWindowServer("Возникла ошибка при запросе и добавлении нового пользователя\n");
                }
            }
        }

        /* Метод, реализующий обмен сообщениями между пользователями */
        private void messagingBetweenUsers(Connect connect, String userName) {
            while (true) {
                try {
                    MessageSerializable message = connect.receive();
                    /* Приняли сообщение от клиента */
                    if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                        String textMessage = String.format("%s: %s\n", userName, message.getTextMessage());
                        setHistory(textMessage);
                        sendMessageFromUsers(new MessageSerializable(MessageType.TEXT_MESSAGE, textMessage));
                    }
                    /*
                     * Если тип сообщения DISABLE_USER, то рассылаем всем пользователям, что данный
                     * пользователь покинул чат, удаляем его из мапы, закрываем его connection
                     */
                    if (message.getTypeMessage() == MessageType.DISABLE_USER) {
                        sendMessageFromUsers(new MessageSerializable(MessageType.REMOVED_USER, userName));
                        model.removeUser(userName);
                        connect.close();
                        gui.refreshDialogWindowServer(String.format(
                                "Пользователь с удаленным доступом %s отключился.\n", socket.getRemoteSocketAddress()));
                        break;
                    }
                } catch (Exception e) {
                    gui.refreshDialogWindowServer(String.format(
                            "Произошла ошибка при рассылке сообщения от пользователя %s, либо отключился!\n",
                            userName));
                    break;
                }
            }
        }

        @Override
        public void run() {
            gui.refreshDialogWindowServer(String.format("Подключился новый пользователь с удаленным сокетом - %s.\n",
                    socket.getRemoteSocketAddress()));
            try {
                // получаем connection при помощи принятого сокета от клиента и запрашиваем имя,
                // регистрируем, запускаем
                // цикл обмена сообщениями между пользователями
                Connect connect = new Connect(socket);
                String nameUser = requestAndAddUsers(connect);
                messagingBetweenUsers(connect, nameUser);
                sendMessageFromUsers(new MessageSerializable(MessageType.TEXT_MESSAGE, getHistory()));
            } catch (Exception e) {
                gui.refreshDialogWindowServer(
                        String.format("Произошла ошибка при рассылке сообщения от пользователя!\n"));
            }
        }
    }
}