package release.logs;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import release.server.Server;

public class Log {
    private static Logger loger;
    static {
        try (FileInputStream ins = new FileInputStream("src\\release\\logs\\log.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            loger = Logger.getLogger(Server.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public static void logServer(String logging) {
        loger.info("\u001B[34m" + logging + "\u001B[0m");
    }

    public static void logUsers(String logging) {
        loger.info("\u001B[36m" + logging + "\u001B[0m");
    }

    public static Logger logging() {
        return loger;
    }
}