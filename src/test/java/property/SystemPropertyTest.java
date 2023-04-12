package property;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.dto.DataItem;
import org.example.dto.ResponseByIdDto;
import org.example.dto.Support;
import org.example.dto.User;
import org.example.utils.config.Config;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemPropertyTest {


    String serviceUrl = Config.getServiceTestConfig().apiTestUrl();

    public String getUrl(){
        String baseUrl = System.getProperty("baseUrl");

        if (Objects.isNull(baseUrl)){
            baseUrl = "https://reqres.in/api/users";
        }
        return baseUrl;
    }

    @Test
    void simpleLogTest() {
        given().
                when().log().uri().
                get(getUrl()).
                then().log().all().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    void simpleGetTest() {
        given().
                when().log().uri().
                get(getUrl()).
                then().log().all().
                statusCode(HttpStatus.SC_OK).
                body("total", is(12));
    }

    @Test
    void simpleOwnerTest() {
        System.out.println(serviceUrl);
    }

    @Test
    void simpleOwnerPropQTest() {
        System.out.println(serviceUrl);
    }
}
