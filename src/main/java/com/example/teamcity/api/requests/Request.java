package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

public class Request {
    /**
     * Request - это класс, описывающий меняющиеся параменты запроса, такие как:
     * спецификация, эндпоинт (relative URL, model)
     */
    // объявление переменных. это переменные, которые принадлежат каждому объекту этого класса
    protected final RequestSpecification spec; // авторизация, baseUrl, фильтры, хедеры
    protected final Endpoint endpoint; // путь и модель

    // RequestSpecification и Endpoint — это типы данных
    // spec и endpoint — это переменные этих типов

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}

// Это универсальный запрос, который знает, куда идти и какой моделью пользоваться.