package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

public class Request {
    /**
     * Request - это класс, описывающий меняющиеся параменты запроса, такие как:
     * спецификация, эндпоинт (relative URL, model)
     */
    protected final RequestSpecification spec; // объявление переменных
    protected final Endpoint endpoint; // это переменные, которые принадлежат каждому объекту этого класса

    // RequestSpecification и Endpoint — это типы данных
    // spec и endpoint — это переменные этих типов

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }

}
