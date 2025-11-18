package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {
    @Test(description = "User should be able to create project", groups = {"Positive", "CRUD"})
    public void userShouldBeAbleCreateProject() {
        var project1 = generate(Project.class);
        var createProject = new CheckedBase<Project>(Specifications.getSpec().superUserAuth(), Endpoint.PROJECT).create(project1);
    }
}
