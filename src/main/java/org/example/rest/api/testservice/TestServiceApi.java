package org.example.rest.api.testservice;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.constants.ApiServiceEndpoints;
import org.example.dto.PassengerRequest;
import org.example.utils.RestHelper;
import org.example.utils.config.Config;

import java.util.HashMap;
import java.util.Map;

public class TestServiceApi extends RestHelper {

    String loginUrl = Config.getServiceConfig().loginUrl();
    String serviceUrl = Config.getServiceConfig().apiUrl();

    public TestServiceApi() {
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        url = serviceUrl;
    }

    @Step("POST v1/token - Create authentication token")
    public Response login() {
        url = loginUrl;
        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "password");
        formParams.put("scope", "offline_access");
        formParams.put("client_id", Config.getServiceConfig().clientId());
        formParams.put("username", Config.getUserConfig().user());
        formParams.put("password", Config.getUserConfig().password());

        return postUrlEncoded(ApiServiceEndpoints.GET_TOKEN, formParams);
    }

    @Step("GET v2/airlines - Get all airlines details")
    public Response getAllAirlines() {
        return get(ApiServiceEndpoints.AIRLINES);
    }

    @Step("GET v2/airlines/{0} - Get airline by airline ID")
    public Response getAirlineById(int id) {
        return get(String.format(ApiServiceEndpoints.AIRLINES_BY_ID, id));
    }

    @Step("POST v2/passenger/ - Create passenger")
    public Response createPassenger(PassengerRequest passenger) {
        return post(ApiServiceEndpoints.PASSENGER, passenger);
    }

    @Step("GET v2/passenger/{0} - Get passenger by ID")
    public Response getPassengerById(String id) {
        return get(String.format(ApiServiceEndpoints.PASSENGER_BY_ID, id));
    }

    @Step("DELETE v2/passenger/{0} - Delete passenger by ID")
    public Response deletePassengerById(String id) {
        return delete(String.format(ApiServiceEndpoints.PASSENGER_BY_ID, id));
    }

    public String getToken() {
        return login().then().statusCode(HttpStatus.SC_OK).extract().path("access_token");
    }

}
