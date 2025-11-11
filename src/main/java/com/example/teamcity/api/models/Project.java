package com.example.teamcity.api.models;

import com.example.teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Project extends BaseModel {
    @Random
    private String id;
    @Random
    private String name;
    private String locator;
}
