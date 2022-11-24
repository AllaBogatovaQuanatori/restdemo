import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class LogsTest {

    Logger LOGGER = LogManager.getLogger();

    @Test
    void logTest() {
        LOGGER.debug("Debug message");
        LOGGER.info("Info message");
        LOGGER.warn("Warn message");
        LOGGER.error("Error message");
        LOGGER.fatal("Fatal message");
    }

    @Test
    void logErrorTest() {

        LOGGER.info("Example log from {}", LogsTest.class.getSimpleName());

        String message = "This is a String";
        Integer zero = 0;

        try {
            LOGGER.debug("Logging message: {}", message);
            LOGGER.debug("Going to divide {} by {}", 42, zero);
            int result = 42 / zero;
        } catch (Exception e) {
            LOGGER.error("Error dividing {} by {} ", 42, zero, e);
        }
    }

}
