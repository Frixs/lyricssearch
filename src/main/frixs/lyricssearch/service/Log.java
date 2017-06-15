package main.frixs.lyricssearch.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Frixs
 */
public class Log {
    /** singleton reference */
    private static Log instance = null;

    /**
     * No-parameter constructor
     */
    private Log() {
    }

    /**
     * Singleton option to get instance
     * @return      instance of this class
     */
    public static Log getInstance() {
        if(instance == null) {
            instance = new Log();
        }

        return instance;
    }

    /**
     * Prints error messages
     * @param type      Type of the error message
     * @param text      error message text
     */
    public void log(LogType type, String text) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        switch (type) {
            case CONFIG:
                System.err.println("[CONFIG]: "+ text +"("+ dateFormat.format(date) +")");
                break;
            case INFO:
                System.err.println("[INFO]: "+ text +"("+ dateFormat.format(date) +")");
                break;
            case WARNING:
                System.err.println("[WARNING]: "+ text +"("+ dateFormat.format(date) +")");
                break;
            case SEVERE:
                System.err.println("[SEVERE]: "+ text +"("+ dateFormat.format(date) +")");
                break;
        }
    }
}
