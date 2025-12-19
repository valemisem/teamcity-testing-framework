package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.example.teamcity.api.generators.RandomData.getString;
import static com.example.teamcity.api.generators.RandomData.getStringDigits;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {
    @Test(description = "User should be able to create project", groups = {"Positive", "CRUD"})
    public void userShouldBeAbleCreateProjectTest() {
        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        var requests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        requests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());
        var createdProject = requests.<Project>getRequest(Endpoint.PROJECT).read(testData.getProject().getId());
        softy.assertEquals(testData.getProject().getName(), createdProject.getName(), "Project name does not match");
    }

    public void userCreatesTwoProjectsWithTheSameIdTest() {
        // 1. Создаём юзера
        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        // 2. Логинимся как юзер
        var requests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        // 3. Создаём первый проект
        var firstProject = requests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());
        String duplicatedId = firstProject.getId();
        // 4. Генерируем второй проект с тем же ID
        var duplicatedProject = generate(Project.class, duplicatedId);

        // 5. Проверяем что генератор действительно дал нам тот же ID
        softy.assertEquals(duplicatedProject.getId(), firstProject.getId(), "Project id does not match");

        // 6. Делаем негативный запрос
        var response = new UncheckedBase(Specifications.getSpec().authSpec(testData.getUser()), Endpoint.PROJECT)
                .create(duplicatedProject);

        response.then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project ID \"%s\" is already used by another project"
                        .formatted(firstProject.getId())));
    }

    public void userCreatesTwoProjectsWithTheSameNameTest() {
        // 1. Создаём юзера
        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        // 2. Логинимся как юзер
        var requests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        // 3. Создаём первый проект
        var firstProject = requests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());
        String duplicatedName = firstProject.getName();
        // 4. Генерируем второй проект с тем же name
        var duplicatedProject = generate(Project.class, null, duplicatedName);

        // 5. Проверяем что генератор действительно дал нам тот же ID
        softy.assertEquals(duplicatedProject.getName(), firstProject.getName(), "Project name does not match");

        // 6. Делаем негативный запрос
        var response = new UncheckedBase(Specifications.getSpec().authSpec(testData.getUser()), Endpoint.PROJECT)
                .create(duplicatedProject);

        response.then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project with this name already exists: " + firstProject.getName()));
    }

    public void userCreatesProjectWithMalformattedIdTest() {
        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());

        var invalidId = getStringDigits(1) + getString();
        var project1 = generate(Project.class, invalidId);

        var response = new UncheckedBase(Specifications.getSpec().authSpec(testData.getUser()), Endpoint.PROJECT)
                .create(project1);

        response.then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID \"" + invalidId + "\" is invalid"));
    }

    public void userCreatesProjectWithEmptyIdTest() {
        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        var invalidId = "";
        var project1 = generate(Project.class, invalidId);

        var response = new UncheckedBase(Specifications.getSpec().authSpec(testData.getUser()), Endpoint.PROJECT)
                .create(project1);
        response.then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID must not be empty."));
    }
}

