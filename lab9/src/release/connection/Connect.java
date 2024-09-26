package release.connection;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connect implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    /* Осуществляем обмен данными по соектному соединению */
    public Connect(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    /* Отправление сообщения */
    public void send(MessageSerializable message) throws IOException {
        synchronized (this.out) {
            out.writeObject(message);
        }
    }

    public Socket getSocker() {
        return socket;
    }

    /* Прием сообщения */
    public MessageSerializable receive() throws IOException, ClassNotFoundException {
        synchronized (this.in) {
            return (MessageSerializable) in.readObject();
        }
    }

    /* Закрытие */
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
