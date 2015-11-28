package se.pingstteknik.propresenter.stagedisplayviewer.util;

import java.time.LocalTime;

/**
 * @author Daniel Kihlgren
 * @version 1.2.0
 * @since 1.2.0
 */
public class Logger {

    public static void error(String message, Object... objects) {
        print("Error", message, objects);
    }

    public static void warn(String message, Object... objects) {
        print("Warn", message, objects);
    }

    public static void info(String message, Object... objects) {
        print("Info", message, objects);
    }

    public static void debug(String message, Object... objects) {
        print("Debug", message, objects);
    }

    private static void print(String level, String message, Object... objects) {
        for (Object object : objects) {
            message = message.replace("{}", object.toString());
        }
        System.out.println(LocalTime.now().toString() +  " " + level + " " + message);
    }

}
