package com.example.teamcity.api;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {

    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());
        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "BuildType name does not match");
    }

    @Test(description = "User should not be able to create two build types with the same Id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());

        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());
        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        new UncheckedBase(Specifications.getSpec().authSpec(testData.getUser()), BUILD_TYPES).create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }


    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles", "BuildType"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {

        var createdProject1 = superUserCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        createdProject1.getId();

        var role = Role.builder()
                .roleId("PROJECT_ADMIN")
                .scope("p:" + createdProject1.getId())
                .build();

        var roles = Roles.builder()
                .role(List.of(role))
                .build();

        testData.getUser().setRoles(roles);
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var createdProject2 = superUserCheckRequests.<Project>getRequest(PROJECT).create(testData.getAnotherProject());
        createdProject2.getId();

        var role2 = Role.builder()
                .roleId("PROJECT_ADMIN")
                .scope("p:" + createdProject2.getId())
                .build();

        var roles2 = Roles.builder()
                .role(List.of(role2))
                .build();

        testData.getAnotherUser().setRoles(roles2);
        superUserCheckRequests.getRequest(USERS).create(testData.getAnotherUser());

        var buildTypeForForeignProject = generate(Arrays.asList(createdProject1), BuildType.class);

        new UncheckedBase(Specifications.getSpec().authSpec(testData.getAnotherUser()), BUILD_TYPES)
                .create(buildTypeForForeignProject)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString(
                        "You do not have enough permissions to edit project with id: " + createdProject1.getId()
                ));
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles", "BuildType"})
    public void projectAdminCreatesBuildTypeForTheirProjectTest() {
        superUserCheckRequests.getRequest(PROJECT).create(testData.getProject());
        var role = Role.builder().roleId("PROJECT_ADMIN").scope("p:" + testData.getProject().getId()).build();
        var roles = Roles.builder().role(List.of(role)).build();
        testData.getUser().setRoles(roles);

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var requests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        var buildType = generate(Arrays.asList(testData.getProject()), BuildType.class); // Это BuildType, который реально ушёл в запрос
        var createdBuildType = requests.<BuildType>getRequest(BUILD_TYPES).create(buildType);
        softy.assertEquals(buildType.getName(), createdBuildType.getName(), "BuildType name does not match");
        // testData.getBuildType() - Этот BuildType создаётся в TestData при инициализации,
        // У него ДРУГОЕ случайное name, Он не участвовал в запросе
        // buildType = то, что ты отправила
        //createdBuildType = то, что вернул сервер
    }
}
