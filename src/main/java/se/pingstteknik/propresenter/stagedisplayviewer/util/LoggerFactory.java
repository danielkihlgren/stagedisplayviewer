package se.pingstteknik.propresenter.stagedisplayviewer.util;

/**
 * @author Daniel Kihlgren
 * @version 1.2.0
 * @since 1.2.0
 */
public class LoggerFactory {
    private static final Logger logger = new Logger();

    public static Logger getLogger(Class<?> clazz) {
        return logger;
    }
}
