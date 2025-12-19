package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CreateBuildConfigPage extends CreateBasePage {
    private static final String BUILD_SHOW_MODE = "createBuildTypeMenu";
    private SelenideElement buildNameInput = $("#buildTypeName");
    private SelenideElement buildNameError = $("#error_buildTypeName");
    private SelenideElement header = $("#restPageTitle");
    private SelenideElement branch = $("#branch");
    private SelenideElement mainBranchNote = $("div.smallNote");

    public static CreateBuildConfigPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_SHOW_MODE), CreateBuildConfigPage.class);
    }

    public CreateBuildConfigPage() {
        header.shouldBe(visible, BASE_WAITING);
    }

    public CreateBuildConfigPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public void setupBuild(String buildName) {
        buildNameInput.shouldBe(Condition.visible, BASE_WAITING);
        buildNameInput.val(buildName);
        branch.shouldBe(Condition.visible, BASE_WAITING);
        mainBranchNote.shouldHave(exactText("The main branch or tag to be monitored"));
        submitButton.shouldBe(visible, BASE_WAITING).click();
    }

    public CreateBuildConfigPage shouldBeOpened() {
        header.shouldBe(visible, BASE_WAITING);
        return this;
    }

    public void verifyBuildName() {
        buildNameError.shouldHave(exactText("Build configuration name must not be empty"));
    }
}
