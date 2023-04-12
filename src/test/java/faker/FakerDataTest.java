package faker;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dto.PassengerRequest;
import org.example.dto.PassengerResponse;
import org.example.rest.api.testservice.TestServiceApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Create user Tests Epic")
@Feature("Successful user creation")
public class FakerDataTest {

    public Faker faker;
    Logger LOGGER = LogManager.getLogger();

    @Test
    void createEnUserTest() {
        PassengerRequest passenger = randomEnPassengerData();
        PassengerRequest passenger2 = randomEnPassengerData();
        PassengerRequest passenger3 = randomEnPassengerData();

        System.out.println(passenger);
        System.out.println(passenger2);
        System.out.println(passenger3);

    }

    @Test
    void createRuUserTest() {
        PassengerRequest passenger = randomPassengerData();
        PassengerRequest passenger2 = randomPassengerData();
        PassengerRequest passenger3 = randomPassengerData();

        System.out.println(passenger);
        System.out.println(passenger2);
        System.out.println(passenger3);
    }

    @Test
    void fakerTest() {
        faker = new Faker();

        LOGGER.info(faker.currency().name());
        LOGGER.info(faker.address().fullAddress());
        LOGGER.info(faker.app().name());

        LOGGER.info(faker.letterify("QAAD-??-3DDD"));
        LOGGER.info(faker.letterify("???-3DDD", true));

        LOGGER.info(faker.numerify("89##34##-##-##"));

        LOGGER.info(faker.name().nameWithMiddle());

    }


    private PassengerRequest randomPassengerData() {
        faker = new Faker(new Locale("ru"));

        return PassengerRequest.builder().
                name(faker.name().fullName()).
                trips(faker.number().randomDigitNotZero()).
                airline(faker.number().numberBetween(0, 10)).build();
    }

    private PassengerRequest randomEnPassengerData() {
        faker = new Faker(new Locale("en"));

        return PassengerRequest.builder().
                name(faker.name().fullName()).
                trips(faker.number().randomDigitNotZero()).
                airline(faker.number().numberBetween(5, 25)).build();
    }
}
