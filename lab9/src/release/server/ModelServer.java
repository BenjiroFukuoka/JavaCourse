package release.server;

import java.util.HashMap;

import release.connection.Connect;

public class ModelServer {
    /* Список пользователей */
    private HashMap<String, Connect> userList = new HashMap<>();

    HashMap<String, Connect> getUsers() {
        return userList;
    }

    void addUser(String nameUser, Connect connect) {
        userList.put(nameUser, connect);
    }

    void removeUser(String nameUser) {
        userList.remove(nameUser);
    }
}