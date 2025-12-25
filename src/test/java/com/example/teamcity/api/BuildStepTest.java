package com.example.teamcity.api;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Step;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class BuildStepTest extends BaseApiTest {
    @Test(description = "User should be able to add build step to build type", groups = {"Positive", "CRUD"})
    public void userShouldBeAbleToAddBuildStepToBuildType() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        var buildType = generate(Arrays.asList(testData.getProject()), BuildType.class);
        userCheckRequests.<BuildType>getRequest(BUILD_TYPES).create(buildType);
        var createdStep = userCheckRequests.<Step>getRequest(STEP).create(testData.getStep(), Map.of("btLocator", "id:"+ buildType.getId()));
        var fetchedStep = userCheckRequests.<Step>getRequest(STEP).read(createdStep.getId(), Map.of("btLocator", "id:" + buildType.getId()));
        softy.assertEquals(testData.getStep().getName(), fetchedStep.getName());
    }





}
