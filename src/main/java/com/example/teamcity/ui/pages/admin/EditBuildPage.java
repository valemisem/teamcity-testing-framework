package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.*;
import com.example.teamcity.ui.data.BuildStepData;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class EditBuildPage extends EditBasePage {
    protected SelenideElement runTypeTub = $("#runType_Tab");
    protected SelenideElement header = $("h2.noBorder");
    protected ElementsCollection buttons = $$("a.btn");
    protected ElementsCollection runners = $$("tr[data-test *= 'runner-item']");
    protected SelenideElement buildStepName = $("#buildStepName");
    protected SelenideElement runSelectMode = $("#use\\.custom\\.script")
            .closest("span")
            .$("button");
    protected SelenideElement scriptCommand = $(Selectors.byName("prop:command.executable"));

    public static EditBuildPage open(String buildId) {
        return Selenide.open(EDIT_BUILD_URL.formatted(buildId), EditBuildPage.class);
    }

    public EditBuildPage() {
        advancedOptions.shouldBe(Condition.visible, BASE_WAITING);
    }

    public String changeGeneralSettings(String name, String id) {
        return generalSettings(name, id);
    }

    public void addCommandLineStep(BuildStepData buildStepData) {
        runTypeTub.click();
        header.should(Condition.appear, BASE_WAITING)
                .shouldHave(Condition.exactText("Build Steps"));
        buttons.findBy(Condition.exactText("Add build step"))
                .shouldBe(Condition.visible, BASE_WAITING).click();
        runners.findBy(Condition.text("Command Line"))
                .shouldBe(Condition.visible, BASE_WAITING).click();
        buildStepName.shouldBe(Condition.visible, BASE_WAITING);
        buildStepName.val(buildStepData.getName());
        runSelectMode.shouldBe(Condition.visible, BASE_WAITING).click();
        $$("li")
                .findBy(Condition.exactText("Executable with parameters"))
                .shouldBe(Condition.visible, BASE_WAITING)
                .click();
        scriptCommand.val(buildStepData.getScript());
        saveButton.shouldBe(Condition.visible, BASE_WAITING).click();
    }
}
