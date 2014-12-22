package se.pingstteknik.propresenter.stagedisplayviewer.util;

import static se.pingstteknik.propresenter.stagedisplayviewer.config.Property.RESPONSE_TIME_MILLIS;

public class ThreadUtil {
    public static void sleep() {
        try {
            Thread.sleep(RESPONSE_TIME_MILLIS.toInt());
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
