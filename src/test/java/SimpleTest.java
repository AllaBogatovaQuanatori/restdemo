import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.dto.DataItem;
import org.example.dto.ResponseByIdDto;
import org.example.dto.Support;
import org.example.dto.User;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleTest {

    //https://reqres.in/ - test
    //https://reqres.in/api-docs/

    @Test
    void simpleLogTest() {
        given().
                when().
                get("https://reqres.in/api/users").
                then().log().ifValidationFails().
                statusCode(HttpStatus.SC_OK).
                body("total", is(15));
    }

    @Test
    void simpleGetTest() {
        given().
                when().
                get("https://reqres.in/api/users").
                then().log().all().
                statusCode(HttpStatus.SC_OK).
                body("total", is(12));
    }

    @Test
    void simpleGetWithLogTest() {
        given().
                log().uri().
                when().
                get("https://reqres.in/api/users").
                then().
                log().body().
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
        assertEquals(2, (Integer) jsonPath.get("id"));
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

    @Test
    void simplePostTest() {
        User newUser = User.builder().name("Ben").job("QA").build();

        Response response = given().log().all().
                contentType(ContentType.JSON).
                body(newUser).
                when().
                post("https://reqres.in/api/users/").
                then().log().all().
                statusCode(HttpStatus.SC_CREATED).
                extract().response();

        String id = response.then().extract().path("id");
        assertThat(id).isNotNull();
    }

    @Test
    void simplePutTest() {
        int userId = 2;

        User changedUser = User.builder().name("Sam").job("Developer").build();

        Response response = given().log().all().
                contentType(ContentType.JSON).
                body(changedUser).
                when().
                put("https://reqres.in/api/users/" + userId).
                then().
                statusCode(HttpStatus.SC_OK).
                extract().response();

        assertThat(changedUser.getName()).isEqualTo(response.path("name"));
        assertThat(changedUser.getJob()).isEqualTo(response.path("job"));
    }

    @Test
    void simpleDeleteTest() {
        int userId = 2;

        given().log().all().
                contentType(ContentType.JSON).
                when().
                delete("https://reqres.in/api/users/" + userId).
                then().log().all().
                statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
