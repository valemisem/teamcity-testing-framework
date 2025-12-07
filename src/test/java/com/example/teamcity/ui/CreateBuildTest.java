package com.example.teamcity.ui;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigPage;
import org.testng.annotations.Test;

@Test(groups = {"Regression"})
public class CreateBuildTest extends BaseUiTest {
    private static final String GIT_URL = "https://github.com/valemisem/mapal-testing";

    @Test(description = "User should be able to create build configuration", groups = {"Positive"})
    public void userCreatesBuildConfiguration() {
        loginAs(testData.getUser());
        var requests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        requests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());
        var createdProjectId = requests.<Project>getRequest(Endpoint.PROJECT).read("id:" + testData.getProject().getId());

        CreateBuildConfigPage.open(createdProjectId.getId()).createForm(GIT_URL).setupBuild(testData.getBuildType().getName());
        var createdBuild = requests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuild);

        var buildExists = ProjectPage.open(testData.getProject().getId())
                .getBuilds().stream()
                .filter(build -> build.getName().text().equals(testData.getBuildType().getName())).count();
        softy.assertEquals(buildExists, 1);
    }

    @Test(description = "User should not be able to create build without name", groups = {"Negative"})
    public void userCreatesBuildConfigurationWithoutProject() {
        loginAs(testData.getUser());
        var requests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        requests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());
        var createdProjectId = requests.<Project>getRequest(Endpoint.PROJECT).read("id:" + testData.getProject().getId());

        CreateBuildConfigPage.open(createdProjectId.getId()).createForm(GIT_URL).setupBuild(null).verifyBuildName();
    }
}
