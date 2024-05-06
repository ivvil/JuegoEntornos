package org.example;

public class Logger {
    private static boolean debug = false;

    private static final String[] colors = new String[]{
            "\u001B[30m", // 0 - Black
            "\u001B[31m", // 1 - Red
            "\u001B[32m", // 2 - Green
            "\u001B[33m", // 3 - Yellow
            "\u001B[34m", // 4 - Blue
            "\u001B[35m", // 5 - Purple
            "\u001B[36m", // 6 - Cyan
            "\u001B[37m", // 7 - White
            "\u001B[0m",  // 8 - Reset
    };

    public static void setDebug(boolean debug){
        Logger.debug = debug;
    }

    public static <T> void info(T msg){
        if (debug)
            System.out.println( "[" + colors[4] +"INFO" + colors[8] + "] - " + msg );
    }

    public static <T> void warning(T msg){
        if (debug)
            System.out.println("[" + colors[3] + "WARNING" + colors[8] + "] - " + msg);
    }

    public static <T> void error(T msg){
        System.out.println("[" + colors[1] + "ERROR" + colors[8] + "] - " + msg);
    }

}