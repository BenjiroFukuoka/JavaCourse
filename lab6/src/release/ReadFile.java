package release;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

import logs.*;

import calculator.Command;
import calculator.Context;
import exceptions.PathToEmptyException;
import exceptions.UnknownСommandException;

class ReadFile {

    private static String[] resize(String[] array, int size) { // расширение массива строк
        return Arrays.copyOf(array, array.length + size);
    }

    protected static void readFile(File file)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnknownСommandException,
            PathToEmptyException {
        BufferedReader scan = null;
        try {
            Log.LOGGER.log(Level.INFO, "Попытка чтение файла");
            scan = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            Log.LOGGER.log(Level.SEVERE, "Файл не найден");
            System.err.println("Файл не найден!");
        }
        String[] lines = new String[0];
        String line = "";
        try {
            Log.LOGGER.log(Level.INFO, "Процесс чтения строк из файла");
            for (int i = 0; ((line = scan.readLine()) != null); ++i) {
                lines = resize(lines, 1);
                lines[i] = line;
            }
            Log.LOGGER.log(Level.INFO, "Строки прочитаны");
            Log.LOGGER.log(Level.INFO, "Создание экземпляра контекста");
            Context context = new Context();
            Log.LOGGER.log(Level.INFO, "Процесс выполнения команд");
            for (int i = 0; i < lines.length; ++i) {
                Log.LOGGER.log(Level.INFO, "Вызов на сполнение команды: " + lines[i]);
                Command x = context.getFactory().createCommand(lines[i]);
                x.make(context);
                Log.LOGGER.log(Level.INFO, "Команда выполнена");
            }
            Log.LOGGER.log(Level.INFO, "Завершение процесса выполнения команд");
        } catch (IOException e) {
            Log.LOGGER.log(Level.SEVERE, "Невозможно прочиать файл", e);
            System.err.println("Неудалось прочитать строки из файла!");
        } finally {
            if (scan != null) {
                try {
                    Log.LOGGER.log(Level.INFO, "Закрытие файла");
                    scan.close();
                } catch (IOException e) {
                    Log.LOGGER.log(Level.SEVERE, "Не удалось закрыть файл", e);
                    e.printStackTrace();
                }
            }
        }
    }
}