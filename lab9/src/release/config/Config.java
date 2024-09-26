package release.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import release.logs.Log;

public class Config {
    private String path = "src\\release\\config\\factory.properties";
    private int serverPort;
    private String messangerType;
    private int timeout;
    private int maxClients;
    private int historyMessages;
    private boolean logSale;

    public Config() {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        serverPort = Integer.parseInt(properties.getProperty("serverPort"));
        messangerType = properties.getProperty("messangerType");
        timeout = Integer.parseInt(properties.getProperty("timeout"));
        maxClients = Integer.parseInt(properties.getProperty("maxClients"));
        historyMessages = Integer.parseInt(properties.getProperty("historyMessages"));
        logSale = Boolean.parseBoolean(properties.getProperty("Log"));
        if (!logSale) {
            Log.logging().setLevel(Level.OFF);
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getMessangerType() {
        return messangerType;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public int getHistoryMessages() {
        return historyMessages;
    }
}