package release.process;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import release.logs.Log;

public class PropertiesFactory {
    private String path = "src\\release\\process\\factory.properties";
    private int accessoryCount;
    private int bodyCount;
    private int engineCount;
    private int workerCount;
    private int dealerCount;
    private int carCount = 1;
    private int accessoryWarehouseSize;
    private int bodyWarehouseSize;
    private int engineWarehouseSize;
    private int carWarehouseSize;
    private boolean logSale;

    public PropertiesFactory() {
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
        accessoryCount = Integer.parseInt(properties.getProperty("AccessorySuppliers"));
        bodyCount = Integer.parseInt(properties.getProperty("BodySuppliers"));
        engineCount = Integer.parseInt(properties.getProperty("EngineSuppliers"));
        workerCount = Integer.parseInt(properties.getProperty("Workers"));
        dealerCount = Integer.parseInt(properties.getProperty("Dealers"));
        accessoryWarehouseSize = Integer.parseInt(properties.getProperty("AccessoryWarehouse"));
        bodyWarehouseSize = Integer.parseInt(properties.getProperty("BodyWarehouse"));
        engineWarehouseSize = Integer.parseInt(properties.getProperty("EngineWarehouse"));
        carWarehouseSize = Integer.parseInt(properties.getProperty("CarWarehouse"));
        logSale = Boolean.parseBoolean(properties.getProperty("LogSale"));
        if (!logSale) {
            Log.logging().setLevel(Level.OFF);
        }
    }

    public String getPath() {
        return path;
    }

    public int getAccessoryCount() {
        return accessoryCount;
    }

    public int getBodyCount() {
        return bodyCount;
    }

    public int getEngineCount() {
        return engineCount;
    }

    public int getWorkerCount() {
        return workerCount;
    }

    public int getDealerCount() {
        return dealerCount;
    }

    public int getCarCount() {
        return carCount;
    }

    public int getAccessoryWarehouseSize() {
        return accessoryWarehouseSize;
    }

    public int getBodyWarehouseSize() {
        return bodyWarehouseSize;
    }

    public int getEngineWarehouseSize() {
        return engineWarehouseSize;
    }

    public int getCarWarehouseSize() {
        return carWarehouseSize;
    }
}