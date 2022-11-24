import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
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
public class FakerTest {

    static TestServiceApi testServiceApi;
    public Faker faker;

    @BeforeAll
    public static void beforeAll() {
        testServiceApi = new TestServiceApi();
    }

    @Test
    @Story("Create user with random data")
    @Description("Create user with random data and check name, trips and airline")
    void createUserTest() {
        PassengerRequest passenger = randomPassengerData();

        PassengerResponse response = testServiceApi.createPassenger(passenger).then().statusCode(HttpStatus.SC_OK).
                extract().response().as(PassengerResponse.class);

        assertThat(response.getId()).isNotEmpty();

        try {
            assertThat(response.getName()).isEqualTo(passenger.getName());
            assertThat(response.getTrips()).isEqualTo(passenger.getTrips());
            assertThat(response.getAirline().get(0).getId()).isEqualTo(passenger.getAirline());
        } finally {
            testServiceApi.deletePassengerById(response.getId()).then().statusCode(HttpStatus.SC_OK).
                    assertThat().body("message", is("Passenger data deleted successfully."));
        }
    }

    @Test
    @Story("This is a Fail Story.")
    @Description("This is a Fail Story Description.")
    public void failTest() {
        assertTrue(false);
    }

    private PassengerRequest randomPassengerData() {
        faker = new Faker(new Locale("ru"));

        return PassengerRequest.builder().
                name(faker.name().fullName()).
                trips(faker.number().randomDigitNotZero()).
                airline(faker.number().numberBetween(0, 10)).build();
    }
}
