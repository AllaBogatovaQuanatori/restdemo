package restassured;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.dto.AirlineItem;
import org.example.dto.DataItem;
import org.example.dto.ResponseDto;
import org.example.rest.api.testservice.TestServiceApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class RestAssuredTest {

    //https://instantwebtools.net/secured-fake-rest-api
    static TestServiceApi testServiceApi;

    @BeforeAll
    public static void beforeAll() {
        testServiceApi = new TestServiceApi();
    }

    @Test
    void findAllUserTest() {
        given().log().uri().
                when().
                get("https://reqres.in/api/users").
                then().
                log().all().
                body("data.findAll { it.id < 0 }.size()", greaterThan(0));
    }

    @Test
    void findAllUser2Test() {
        Response response = given().log().uri().
                when().
                get("https://reqres.in/api/users").then().log().all().extract().response();

/*        List<Map<String, String>> users = response.jsonPath().
                getList("data.findAll { user -> user.last_name.matches('.*o.*') }");
        System.out.println("users " + users);*/

        List<Map<String, String>> usersNames = response.jsonPath().
                getList("data.findAll { user -> user.last_name.matches('Ra.*') }." +
                        "collect { [first_name: it.first_name, last_name: it.last_name] }");
        System.out.println("usersNames " + usersNames);
    }


    @Test
    void getSortedUsersTest() {
        ResponseDto response = given().log().uri().
                when().
                get("https://reqres.in/api/users").
                then().
                log().body().
                statusCode(HttpStatus.SC_OK).extract().response().as(ResponseDto.class);

        List<DataItem> dataItems = response.getData();
        List<DataItem> sortedUsers = dataItems.stream().
                filter(i -> i.getId() >= 5).
                filter(n -> n.getLastName().contains("o")).
                collect(Collectors.toList());

      System.out.println(sortedUsers.stream().map(DataItem::getEmail).collect(Collectors.toList()));
    }


    @Test
    void findAllIndiaAirlinesTest() {
        ValidatableResponse response = testServiceApi.getAllAirlines().then().statusCode(HttpStatus.SC_OK).
                body("airlines.findAll { it.country.equalsIgnoreCase('india')}.size", greaterThan(0));
    }

    @Test
    void findIndianLinesTest() {
        Response response = testServiceApi.getAllAirlines().then().statusCode(HttpStatus.SC_OK).extract().response();
        List<AirlineItem> items = response.getBody().as(new TypeRef<>() {
        });
        List<AirlineItem> indianAirlines = items.stream().
                filter(f -> f.getCountry().equals("India")).collect(Collectors.toList());

        System.out.println(indianAirlines.stream().
                map(AirlineItem::getName).collect(Collectors.toList()));
    }


}
