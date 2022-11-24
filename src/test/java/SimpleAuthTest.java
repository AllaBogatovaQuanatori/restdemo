import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.example.dto.AirlineItem;
import org.example.rest.api.testservice.TestServiceApi;
import org.example.utils.RestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class SimpleAuthTest extends RestHelper {

    //https://www.instantwebtools.net/secured-fake-rest-api
    static TestServiceApi testServiceApi;

    @BeforeAll
    public static void beforeAll() {
        testServiceApi = new TestServiceApi();
    }

    @Test
    void simplePostAuthTest() {
        Response response = testServiceApi.getAllAirlines().then().statusCode(HttpStatus.SC_OK).extract().response();

        List<String> names = response.path("name");
        assertThat(names).
                contains("Singapore Airlines", "Emirates");
    }

    @Test
    void containsNegativeTest() {
        Response response = testServiceApi.getAllAirlines().then().statusCode(HttpStatus.SC_OK).extract().response();

        List<String> names = response.path("name");
        assertThat(names).isNotEmpty().
                doesNotContain("Singapore Airlines");
    }

    @Test
    void softAssertsTest() {
        AirlineItem response = testServiceApi.getAirlineById(1).then().statusCode(HttpStatus.SC_OK).
                extract().response().as(AirlineItem.class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getId()).as("id").isEqualTo(1);
        softly.assertThat(response.getName()).as("name").isEqualTo("Quatar Airways");
        softly.assertThat(response.getCountry()).as("country").isEqualTo("Quatar");
        softly.assertThat(response.getLogo()).isNotEmpty();
        softly.assertThat(response.getSlogan()).as("slogan").isEqualTo("Going Places Together");
        softly.assertThat(response.getHeadQuaters()).as("head_quaters").isEqualTo("Qatar Airways Towers, Doha, Qatar");
        softly.assertThat(response.getWebsite()).isNotEmpty();
        softly.assertThat(response.getEstablished()).isEqualTo("1994");
        softly.assertAll();
    }


}
