package com.example.teamcity.api;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})

    public void userCreatesBuildTypeTest() {
        var user = generate(User.class); // генерация данных по конкретному ДТО
        superUserCheckRequests.getRequest(USERS).create(user); // сгенерировали юзера, сгенерировали запрос для юзера и отправили
        // запрос по созданию юзера

        var userCheckRequests = new CheckedRequests(Specifications.getSpec().authSpec(user));
        var project = generate(Project.class); // сгенерировали проект
        /**
         * Создаёт объект Project со случайными тестовыми данными (например имя, ID)
         * и возвращает готовую модель для отправки в API
         */

        project = userCheckRequests.<Project>getRequest(PROJECT).create(project); // и переменную projectId, куда мы сохранили АйДишник созданного проекта
        // после рефакторинга мы также сохранили вообще все данные проекта, не только сгенерированные

        var buildType = generate(Arrays.asList(project), BuildType.class); // сгенерировали билд тайп

        userCheckRequests.getRequest(BUILD_TYPES).create(buildType);

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(buildType.getId()); // обратились к чтению билд тайп айДи
        // This time, T = BuildType, so return a BuildType object.
        // because getRequest() is generic
        // We convert T into a specific type (e.g. Project) so the generic method knows
        // which model class to work with and returns the correct typed object

        softy.assertEquals(buildType.getName(), createdBuildType.getName(), "BuildType name does not match");
        // убедились, что все данные были созданы корректно

    }

    @Test(description = "User should not be able to create two build types with the same Id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        step("Create user");
        step("Create project by user");
        step("Create buildType1 for project by user");
        step("Create buildType2 with the same Id as buildType1 for project by user");
        step("Check buildType2 was not created with 400 bad request code");
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        step("Create user");
        step("Create project by user");
        step("Grant user PROJECT_ADMIN role in project");

        step("Create buildType for project by user");
        step("Check buildType was created successfully");

    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        step("Create user1");
        step("Create project1 by user1");
        step("Grant user1 PROJECT_ADMIN role in project1");

        step("Create user2");
        step("Create project2 by user2");
        step("Grant user2 PROJECT_ADMIN role in project2");

        step("Create buildType for project1 by user2");
        step("Check buildType was not created with forbidden code");
    }
}
