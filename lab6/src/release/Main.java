package release;

import java.io.IOException;
import java.util.logging.Level;

import logs.*;

import exceptions.PathToEmptyException;
import exceptions.UnknownСommandException;

public class Main {
    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException,
            UnknownСommandException, PathToEmptyException {
        Log.LOGGER.log(Level.INFO, "~Старт программы~");
        Process example = new Process();
        Log.LOGGER.log(Level.INFO, "Создание экземпляра " + example.getClass());
        Log.LOGGER.log(Level.INFO, "Вызов метода make() " + example.getClass());
        example.make();
    }
}