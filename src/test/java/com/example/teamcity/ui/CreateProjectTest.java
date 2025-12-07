package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import org.testng.annotations.Test;

@Test(groups = {"Regression"})
public class CreateProjectTest extends BaseUiTest {
    private static final String GIT_URL = "https://github.com/valemisem/mapal-testing";

    @Test(description = "User should be able to create project", groups = {"Positive"})
    public void userCreatesProject() {
        //  подготовка окружения
        loginAs(testData.getUser());

        //  взаимодействие с UI
        CreateProjectPage.open("_Root")
                .createForm(GIT_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        // проверка состояния АПИ (корректность отправки данных с UI на API)
        // API: project with name, buildType for this project with buildType name
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECT).read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        // проверка состояния UI (корректность считывания данных и отображение данных на UI)
        ProjectPage.open(createdProject.getId())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));

        var projectExists = ProjectsPage.open()
                .waitForProjects()
                .getProjects().stream()
                .filter(project -> project.getName().text().equals(testData.getProject().getName())).count();
        softy.assertEquals(projectExists, 1);
    }

    @Test(description = "User should not be able to create project without name", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {
        loginAs(testData.getUser());
        CreateProjectPage.open("_Root")
                .createForm(GIT_URL).setupProject(null, null).verifyErrorMessage();
    }
}
