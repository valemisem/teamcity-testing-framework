package com.example.teamcity.api.models;

import lombok.Data;

@Data
public class TestData {
    private Project project;
    private Project anotherProject;
    private User user;
    private User anotherUser;
    private BuildType buildType;
    private Step step;
}
