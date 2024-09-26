package release.connection;

import java.io.Serializable;
import java.util.Set;

public class MessageSerializable implements Serializable {
    private MessageType typeMessage; // тип сообщения
    private String textMessage; // текст сообщения
    private Set<String> listUsers; // множество имен уже подлючившихся пользователей

    public MessageSerializable(MessageType typeMessage, String textMessage) {
        this.textMessage = textMessage;
        this.typeMessage = typeMessage;
        this.listUsers = null;
    }

    public MessageSerializable(MessageType typeMessage, Set<String> listUsers) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = listUsers;
    }

    public MessageSerializable(MessageType typeMessage) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = null;
    }

    public MessageType getTypeMessage() {
        return typeMessage;
    }

    public Set<String> getListUsers() {
        return listUsers;
    }

    public String getTextMessage() {
        return textMessage;
    }

}
