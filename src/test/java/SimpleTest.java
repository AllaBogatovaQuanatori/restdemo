import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.dto.DataItem;
import org.example.dto.ResponseByIdDto;
import org.example.dto.Support;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleTest {

    //https://reqres.in/ - test
    //https://reqres.in/api-docs/

    @Test
    void simpleGetTest() {
        given().
                when().
                get("https://reqres.in/api/users").
                then().
                statusCode(HttpStatus.SC_OK).
                body("total", is(12));
    }

    @Test
    void simpleGetWithLogTest() {
        given().
                when().
                get("https://reqres.in/api/users").
                then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("total_pages", is(2));
    }

    @Test
    void simpleGetWithJsonPathTest() {
        Response response = given().
                when().
                get("https://reqres.in/api/users/2").
                then().log().body().
                statusCode(HttpStatus.SC_OK).
                extract().response();

        JsonPath jsonPath = new JsonPath(response.asString()).setRootPath("data");
        assertEquals("9", jsonPath.get("id"));
        assertEquals("Janet", jsonPath.get("first_name"));
        assertEquals("Weaver", jsonPath.get("last_name"));
    }

    @Test
    void simpleGetWithDTOTest() {
        ResponseByIdDto response = given().
                when().
                get("https://reqres.in/api/users/2").
                then().log().body().
                statusCode(HttpStatus.SC_OK).
                extract().response().as(ResponseByIdDto.class);

        DataItem dataItem = DataItem.builder().id(2).email("janet.weaver@reqres.in").firstName("Janet").
                lastName("Weaver").avatar("https://reqres.in/img/faces/2-image.jpg").build();

        ResponseByIdDto responseDto = ResponseByIdDto.builder().data(dataItem).support(Support.of()).build();

        assertThat(responseDto).isEqualTo(response);
    }


}
