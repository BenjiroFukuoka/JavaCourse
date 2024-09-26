package release.process;

public class ListId {
    private static volatile int aId = 0;
    private static volatile int bId = 0;
    private static volatile int cId = 0;
    private static volatile int eId = 0;

    public static synchronized int getaID() {
        return aId++;
    }

    public static synchronized int getbID() {
        return bId++;
    }

    public static synchronized int geteID() {
        return eId++;
    }

    public static synchronized int getcID() {
        return cId++;
    }

    public static int gaID() {
        return aId;
    }

    public static int gbID() {
        return bId;
    }

    public static int geID() {
        return eId;
    }

    public static int gcID() {
        System.out.println(cId);
        return cId;
    }
}