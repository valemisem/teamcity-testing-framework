package com.example.teamcity.ui;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.data.BuildStepData;
import com.example.teamcity.ui.pages.BuildConfigurationPage;
import com.example.teamcity.ui.pages.admin.EditBuildPage;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.generators.RandomData.getString;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class EditBuildTest extends BaseUiTest {
    @Test(description = "User should be able to edit build configuration description", groups = {"Positive"})
    public void userEditsBuildConfigurationGeneralSettings() {
        loginAs(testData.getUser());
        var requests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        requests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());
        var buildType = generate(Arrays.asList(testData.getProject()), BuildType.class);
        var createdBuild = requests.<BuildType>getRequest(BUILD_TYPES).create(buildType);
        String expectedDescription = EditBuildPage.open(createdBuild.getId())
                .changeGeneralSettings(createdBuild.getName(), createdBuild.getId());
        String actualDescription = BuildConfigurationPage.open(createdBuild.getId()).getDescriptionText();
        softy.assertEquals(actualDescription, expectedDescription);
    }

    @Test(description = "User should be able to add build configuration step", groups = {"Positive"})
    public void userAddsBuildConfigurationStep() {
        loginAs(testData.getUser());
        var requests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        requests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());
        var buildType = generate(Arrays.asList(testData.getProject()), BuildType.class);
        var createdBuild = requests.<BuildType>getRequest(BUILD_TYPES).create(buildType);
        BuildStepData buildStepData = BuildStepData.builder()
                .name(getString())
                .script("echo \"Build step started\"")
                .build();
        EditBuildPage.open(createdBuild.getId()).addCommandLineStep(buildStepData);

        // check API call that we have this step
//        getBuildStep

        // check UI that we show this step
    }


}
