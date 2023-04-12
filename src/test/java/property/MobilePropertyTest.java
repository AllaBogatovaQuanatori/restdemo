package property;

import org.aeonbits.owner.ConfigFactory;
import org.example.utils.config.MobileConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MobilePropertyTest {

    @Test
    public void testAndroid() {
        System.setProperty("device", "xiaomi");
        MobileConfig config = ConfigFactory
                .create(MobileConfig.class, System.getProperties());
        assertThat(config.platformName()).isEqualTo("Android");
        assertThat(config.platformVersion()).isEqualTo("27.0");
        assertThat(config.deviceName()).isEqualTo("Google Pixel XL");
    }
    @Test
    public void testIphone12() {
        System.setProperty("device", "iphone13");
        MobileConfig config = ConfigFactory
                .create(MobileConfig.class, System.getProperties());
        assertThat(config.platformName()).isEqualTo("iOS");
        assertThat(config.platformVersion()).isEqualTo("15.0");
        assertThat(config.deviceName()).isEqualTo("iPhone 12 Pro");
    }
}
