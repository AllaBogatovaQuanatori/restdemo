import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.example.dto.AirlineItem;
import org.example.rest.api.testservice.TestServiceApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Get Airlines Tests Epic")
@Feature("Get Airline")
public class DataProviderTest {

    static TestServiceApi testServiceApi;

    @BeforeAll
    public static void beforeAll() {
        testServiceApi = new TestServiceApi();
    }

    @ParameterizedTest()
    @ValueSource(ints = {1, 3, 5, 3, 15})
    @Story("Get Airline by ID")
    @Description("Get Airline by ID: {0}")
    void getAirlineByIdTest(int id) {
        AirlineItem response = testServiceApi.getAirlineById(id).then().statusCode(HttpStatus.SC_OK).
                extract().response().as(AirlineItem.class);

        assertThat(response.getId()).isPositive();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource
    @Story("Get Airline by ID")
    @Description("Get Airline by ID: {0}")
    void getAirlineByIdArgumentsTest(int id, String name, String headQuarter) {
        AirlineItem response = testServiceApi.getAirlineById(id).then().statusCode(HttpStatus.SC_OK).
                extract().response().as(AirlineItem.class);

        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getHeadQuaters()).containsAnyOf(headQuarter);


    }

    private static Stream<Arguments> getAirlineByIdArgumentsTest() {
        return Stream.of(
                Arguments.of(Named.of("Check Quatar", 1), "Quatar Airways", "Doha"),
                Arguments.of(Named.of("Check Hong Kong", 3), "Cathay Pacific", "Chek Lap Kok")
        );
    }

}
