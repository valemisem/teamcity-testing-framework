package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class CreateProjectPage extends CreateBasePage {
    private static final String PROJECT_SHOW_MODE = "createProjectMenu";
    private SelenideElement projectNameInput = $("#projectName");
    private SelenideElement errorProject = $("#error_projectName");
    private SelenideElement errorBuildTypeName = $("#error_buildTypeName");

    public static CreateProjectPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE), CreateProjectPage.class);
    }

    public CreateProjectPage createForm(String url) {
        baseCreateForm(url);
        return this; // возвращаем тот же CreateProjectPage
    }

    public CreateProjectPage setupProject(String projectName, String buildTypeName) {
        projectNameInput.val(projectName);
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        return this;
    }

    public void verifyErrorMessage() {
        errorProject.shouldHave(exactText("Project name must not be empty"));
        errorBuildTypeName.shouldHave(exactText("Build configuration name must not be empty"));
    }
}
