package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class UncheckedBase extends Request implements CrudInterface {
    public UncheckedBase(RequestSpecification spec, Endpoint endpoint) { // параметризирован по спецификации и эндпоинту
        super(spec, endpoint);
    } // Call parent's constructor to initialize shared fields (spec and endpoint)

    @Override
    public Response create(BaseModel model) {
        return RestAssured
                .given()
                .spec(spec)
                .body(model)
                .post(endpoint.getUrl());
    }

    public Response create(BaseModel model, Map<String, String> pathParams) {
        return RestAssured
                .given()
                .spec(spec)
                .pathParams(pathParams)
                .body(model)
                .post(endpoint.getUrl());
    }

    @Override
    public Response read(String locator) {
        return RestAssured
                .given()
                .spec(spec)
                .get(endpoint.getUrl() + "/" + locator);
    }

    public Response read(String locator, Map<String, String> pathParams) {
        return RestAssured
                .given()
                .spec(spec)
                .pathParams(pathParams)
                .get(endpoint.getUrl() + "/" + locator);
    }

    public Response readAll(Map<String, String> pathParams) {
        return RestAssured
                .given()
                .spec(spec)
                .pathParams(pathParams)
                .get(endpoint.getUrl());
    }

    @Override
    public Response update(String locator, BaseModel model) {
        return RestAssured
                .given()
                .body(model)
                .spec(spec)
                .put(endpoint.getUrl() + "/" + locator);
    }

    @Override
    public Response delete(String locator) {
        return RestAssured
                .given()
                .spec(spec)
                .delete(endpoint.getUrl() + "/" + locator);
    }

    public Response delete(Map<String, String> pathParams, String locator) {
        return RestAssured
                .given()
                .spec(spec)
                .pathParams(pathParams)
                .delete(endpoint.getUrl() + "/" + locator);
    }
}
