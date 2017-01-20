package xm.java8;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author xuming
 */
public class Log4j2Demo {
    //    private static final Logger LOGGER = LogManager.getLogger(LogDemo.class);
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.error("hello, world err");
        LOGGER.info("hello, world info");
        LOGGER.debug("Logging in user {} with my birthday {}, time: {}", "lili", "2014.2.3");
    }
}
