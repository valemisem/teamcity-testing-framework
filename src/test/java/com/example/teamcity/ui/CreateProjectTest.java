package com.example.teamcity.ui;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.ui.pages.LoginPage;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class CreateProjectTest extends BaseUiTest {
    @Test(description = "User should be able to create project", groups = {"Positive"})
    public void userCreatesProject() {
        //  подготовка окружения
        step("login as user");
        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        LoginPage.open().login(testData.getUser());

        //  взаимодействие с UI
        step("Open 'Create Project Page' (/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL");
        step("Click 'Proceed'");
        step("Fix Project name and Build Type name values");
        step("Click 'Proceed'");

        // проверка состояния АПИ (корректность отправки данных с UI на API)
        // API: project with name, buildType for this project with buildType name
        step("Check that all entities (project, build type) were successfully created with correct data on API level");

        // проверка состояния UI (корректность считывания данных и отображение данных на UI)
        step("Check that project is visible on Projects Page (http://localhost:8111/favorite/projects)");
    }

    @Test(description = "User should not be able to create project without name", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {
        step("login as user");
        step("Check number of projects");

        //  взаимодействие с UI
        step("Open 'Create Project Page' (/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL");
        step("Click 'Proceed'");
        step("Set incorrect/empty Project name");
        step("Click 'Proceed'");

        // проверка состояния АПИ (корректность отправки данных с UI на API)
        step("Check that number of projects did not change");

        step("Check that error appears 'Project name must not be empty'");

    }
}
