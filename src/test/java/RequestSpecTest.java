import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class RequestSpecTest {

    //https://reqres.in/ - test
    //https://reqres.in/api-docs/

    static RequestSpecification requestSpecification;
    static ResponseSpecification responseSpecification;

    @BeforeAll
    public static void setSpec() {
        requestSpecification = new RequestSpecBuilder().
                addHeader("Content-Type", "application/json").
                setBaseUri("https://reqres.in/api/").
                build();

        responseSpecification = new ResponseSpecBuilder().
                expectBody("total", is(12)).
                expectStatusCode(HttpStatus.SC_OK).
                expectContentType("application/json").
                build();
    }

    @Test
    void simpleGetTest() {
        given().spec(requestSpecification).log().all().
                when().
                get("users").
                then().log().all().
                spec(responseSpecification);
    }

    @Test
    void noSpecGetTest() {
        given().log().all().
                when().
                get("users").
                then().
                statusCode(HttpStatus.SC_OK).
                body("total", is(12));
    }
}
