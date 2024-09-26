package release;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

import logs.*;

import calculator.Command;
import calculator.Context;
import exceptions.PathToEmptyException;
import exceptions.UnknownСommandException;

class Process {
    private static int choice;

    protected void make()
            throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnknownСommandException, PathToEmptyException {
        Log.LOGGER.log(Level.INFO, "Меню выбора формата чтение");
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("~1 - чтение из файла, 2 - ввод с клавиатуры, exit - выход~");
            Log.LOGGER.log(Level.INFO, "Чтение команды для меню");
            System.out.print("Вводимое значение: ");
            String str = scan.readLine();
            Log.LOGGER.log(Level.INFO, "Получение команды для меню " + str);
            if ("EXIT".equals(str.toUpperCase())) {
                Log.LOGGER.log(Level.INFO, "Выход из меню " + str);
                break;
            }
            if (!str.matches("\\d")) {
                System.out.println("Недопустимое значение, только:");
                Log.LOGGER.log(Level.WARNING, "Было введено некорректное значения " + str);
                continue;
            }
            choice = Integer.parseInt(str);
            switch (choice) {
                case 1:
                    Log.LOGGER.log(Level.INFO, "Исполнение команды - Чтение из файла");
                    File file = new File("src\\release\\text.txt");
                    ReadFile.readFile(file);
                    break;
                case 2:
                    Log.LOGGER.log(Level.INFO, "Исполнение команды - Ручной ввод команд");
                    Log.LOGGER.log(Level.INFO, "Создание контекста");
                    Context context = new Context();
                    do {
                        try {
                            Log.LOGGER.log(Level.INFO, "Чтение команды");
                            System.out.println("Введите комманду: ");
                            str = scan.readLine();
                            Log.LOGGER.log(Level.INFO, "Команда прочитана и передана на исполнение", str);
                            Command com = context.getFactory().createCommand(str);
                            com.make(context);
                            Log.LOGGER.log(Level.INFO, "Команда выполнена");
                        } catch (Exception ex) {
                            Log.LOGGER.log(Level.WARNING, "Ошибка ввода команды", ex);
                            ex.getStackTrace();
                        }
                    } while (str != "EXIT");
                    break;
                default:
                    Log.LOGGER.log(Level.WARNING, "Недопустимое значения для меню");
                    System.out.println("Недопустимое значения для выбора!");
                    continue;
            }
            break;
        }
    }
}
