package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class CreateBuildConfigPage extends CreateBasePage {
    private static final String BUILD_SHOW_MODE = "createBuildTypeMenu";
    private SelenideElement buildNameInput = $("#buildTypeName");
    private SelenideElement buildNameError = $("#error_buildTypeName");

    public static CreateBuildConfigPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_SHOW_MODE), CreateBuildConfigPage.class);
    }

    public CreateBuildConfigPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public CreateBuildConfigPage setupBuild(String buildName) {
        buildNameInput.val(buildName);
        submitButton.click();
        return this;
    }

    public void verifyBuildName() {
        buildNameError.shouldHave(exactText("Build configuration name must not be empty"));
    }
}
