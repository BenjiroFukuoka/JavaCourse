package logs;

import java.io.FileInputStream;
import java.util.logging.*;

import release.Main;

public class Log {
    public static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("src\\logs\\log.properties")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }
}