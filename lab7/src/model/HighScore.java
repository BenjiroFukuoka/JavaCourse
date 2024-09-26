package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class HighScore implements Serializable, Comparable<HighScore> {
    /* ПОЛЕ */
    private int score;
    private String name;
    private transient static String path = "src\\highscore.dat";

    /* КОНСТРУКТОР */
    public HighScore(String name, int score) {
        this.score = score;
        this.name = name;
    }

    /* ГЕТТЕРЫ и СЕТТЕРЫ */
    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*
     * СОЗДАНИЕ МАССИВА HIGHSCORE с размером 10 и ЗАПИСЬ В ФАЙЛ с помощью
     * СЕРИАЛИЗАЦИИ
     */
    private static void initializeFile() {
        HighScore[] h = { new HighScore(" ", 0), new HighScore(" ", 0), new HighScore(" ", 0),
                new HighScore(" ", 0), new HighScore(" ", 0), new HighScore(" ", 0),
                new HighScore(" ", 0), new HighScore(" ", 0), new HighScore(" ", 0),
                new HighScore(" ", 0) };
        ArrayList<HighScore> tab = new ArrayList<HighScore>();
        for (var i : h) {
            tab.add(i);
        }
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(path));
            o.writeObject(tab);
            o.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ДЕСЕРИАЛИЗАЦИЯ */
    public static ArrayList<HighScore> getHighScores() {
        if (!new File(path).exists())
            initializeFile();
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(path));
            ArrayList<HighScore> tab = (ArrayList<HighScore>) o.readObject();
            o.close();
            return tab;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* ДОБАВЛЕНИЕ НОВЫХ ЗАПИСЕЙ */
    public static void addHighScore(HighScore h) {
        ArrayList<HighScore> tab = getHighScores();
        /*
         * ЕСЛИ уже существует ИМЯ, то проверить на НОВЫЙ РЕКОРД, иначе ЗАПИСАТЬ НОВОЕ
         * ИМЯ и РЕКОРД в пустой I
         */
        for (int i = 0; i < tab.size(); i++) {
            if (tab.get(i).getName().equals(h.getName())) {
                if (tab.get(i).getScore() < h.getScore()) {
                    tab.get(i).setScore(h.getScore());
                    break;
                } else
                    break;
            } else if (tab.get(i).getName().equals(" ")) {
                tab.get(i).setName(h.getName());
                tab.get(i).setScore(h.getScore());
                break;
            }
        }
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(path));
            o.writeObject(tab);
            o.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(HighScore o) {
        if (score == o.getScore()) {
            return 0;
        }
        if (score < o.getScore()) {
            return -1;
        }
        return +1;
    }
}