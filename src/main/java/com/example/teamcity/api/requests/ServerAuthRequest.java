package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.ServerAuthSettings;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class ServerAuthRequest { // new requester, not CRUD, only settings
    private static final String SERVER_AUTH_SETTINGS_URL = "/app/rest/server/authSettings";
    private RequestSpecification spec;

    public ServerAuthRequest(RequestSpecification spec) { // constructor
        // параметризация по спецификации чтобы логиниться с разными юзерами
        this.spec = spec;
    }

    public ServerAuthSettings read() { // возвращаем ServerAuthSettings ДТО
        return RestAssured.given()
                .spec(spec)
                .get(SERVER_AUTH_SETTINGS_URL) // запрос GET
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(ServerAuthSettings.class);
    }

    public ServerAuthSettings update(ServerAuthSettings auhSettings) {
        return RestAssured.given()
                .spec(spec)
                .body(auhSettings)
                .put(SERVER_AUTH_SETTINGS_URL)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(ServerAuthSettings.class);
    }

}
