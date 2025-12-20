package com.example.teamcity.ui;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.admin.EditBuildPage;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class EditBuildTest extends BaseUiTest {
    @Test(description = "User should be able to edit build configuration", groups = {"Positive"})
    public void userEditsBuildConfiguration() {
        loginAs(testData.getUser());
        var requests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        requests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());
        var buildType = generate(Arrays.asList(testData.getProject()), BuildType.class);
        var createdBuildId = requests.<BuildType>getRequest(BUILD_TYPES).create(buildType);

        EditBuildPage.open(createdBuildId.getId());

    }


}
