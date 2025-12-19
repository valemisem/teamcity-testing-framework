package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
    PROJECT("/app/rest/projects", Project.class),
    USERS("/app/rest/users", User.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass; // возвращаем ответ

}

/*
Endpoint — это enum с метаданными, то есть с описанием:
какой URL используется
какая модель связана с этим эндпоинтом

Каждый элемент хранит URL + тип DTO, например:
BUILD_TYPES
URL = /app/rest/buildTypes
Модель = BuildType.class

Это нужно, чтобы потом:
отправлять запросы на нужный URL
десериализовать ответ в правильный Java-класс.
 */
