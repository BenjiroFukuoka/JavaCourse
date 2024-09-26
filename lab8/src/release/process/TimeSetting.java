package release.process;

public class TimeSetting {
    private static int accessoryTime = 3000;
    private static int bodyTime = 2000;
    private static int engineTIme = 2000;
    private static int workerTime = 10000;
    private static int dealerTime = 8000;

    public static int getAccessoryTime() {
        return accessoryTime;
    }

    public static void setAccessoryTime(int accessoryTime) {
        TimeSetting.accessoryTime = accessoryTime;
    }

    public static int getBodyTime() {
        return bodyTime;
    }

    public static void setBodyTime(int bodyTime) {
        TimeSetting.bodyTime = bodyTime;
    }

    public static int getEngineTIme() {
        return engineTIme;
    }

    public static void setEngineTIme(int engineTIme) {
        TimeSetting.engineTIme = engineTIme;
    }

    public static int getWorkerTime() {
        return workerTime;
    }

    public static void setWorkerTime(int workerTime) {
        TimeSetting.workerTime = workerTime;
    }

    public static int getDealerTime() {
        return dealerTime;
    }

    public static void setDealerTime(int dealerTime) {
        TimeSetting.dealerTime = dealerTime;
    }
}