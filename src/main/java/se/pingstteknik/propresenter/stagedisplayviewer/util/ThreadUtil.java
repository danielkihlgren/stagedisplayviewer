package se.pingstteknik.propresenter.stagedisplayviewer.util;

public class ThreadUtil {
    public static void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
