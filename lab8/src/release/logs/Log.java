package release.logs;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import release.Main;

public class Log {
    private static Logger loger;
    static {
        try (FileInputStream ins = new FileInputStream("src\\release\\logs\\log.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            loger = Logger.getLogger(Main.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public static void logDealer(String logging) {
        loger.info("\u001B[34m" + logging + "\u001B[0m");
    }

    public static void logWorker(String logging) {
        loger.info("\u001B[36m" + logging + "\u001B[0m");
    }

    public static Logger logging() {
        return loger;
    }
}