package ConnectZab;


import java.util.Random;

public class Config {
    private static String hostid;
    private static String interfaceid;
    private static String name;
    private static String pass;
    private static String host;
    private static String graphName;
    private static String localGraphName;
    private static String url;
    private static String localFilePath;


    public static void setHost(String host) {
        Config.host = host;
    }
    public static String getLocalFilePath() {
        return localFilePath;
    }

    public static void setLocalFilePath(String localFilePath) {
        Config.localFilePath = localFilePath;
    }

    public static String getLocalGraphName() {
        return localGraphName;
    }

    public static void setLocalGraphName(String localFileName) {
        Config.localGraphName = localFileName;
    }


    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Config.url = url;
    }

    public static String getColor(){
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(0xffffff + 1);

        // format it as hexadecimal string (with hashtag and leading zeros)
        String colorCode = String.format("%06x", nextInt);

        return colorCode;
    }

    public static String getGraphName() {
        return graphName;
    }

    public static void setGraphName(String graphName) {
        Config.graphName = graphName;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Config.name = name;
    }

    public static String getHost() {
        return host;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        Config.pass = pass;
    }


    public static String getHostid() {
        return hostid;
    }

    public static void setHostid(String hostid) {
        Config.hostid = hostid;
    }

    public static String getInterfaceid() {
        return interfaceid;
    }

    public static void setInterfaceid(String interfaceid) {
        Config.interfaceid = interfaceid;
    }


}
