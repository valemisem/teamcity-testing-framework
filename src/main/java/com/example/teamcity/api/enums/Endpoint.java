package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.BuildType;

public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class)
    private final String url;
    private final Class<? extends BaseModel> modelClass;

}

