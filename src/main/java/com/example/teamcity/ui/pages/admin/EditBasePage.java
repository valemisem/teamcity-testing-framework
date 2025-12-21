package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.example.teamcity.api.generators.RandomData.getString;

public class EditBasePage extends BasePage {
    protected static final String EDIT_BUILD_URL = "/admin/editBuild.html?id=buildType:%s";
    protected static final String EDIT_PROJECT_URL = "/admin/editProject.html?projectId=%s";
    protected SelenideElement nameInput = $("#name");
    protected SelenideElement idInput = $("#externalId");
    protected SelenideElement descriptionInput = $("#description");
    protected SelenideElement saveButton = $(Selectors.byAttribute("value", "Save"));
    protected ElementsCollection successMessages = $$(".successMessage");
    protected SelenideElement advancedOptions = $("[data-hint-container-id='advanced-settings']");

    protected String generalSettings(String name, String id) {
        var descriptionData = getString();
        nameInput.shouldBe(Condition.visible, BASE_WAITING);
        nameInput.shouldHave(Condition.value(name));
        idInput.shouldBe(Condition.visible, BASE_WAITING);
        idInput.shouldHave(Condition.value(id));
        descriptionInput.shouldBe(Condition.visible, BASE_WAITING);
        descriptionInput.val(descriptionData);
        saveButton.shouldBe(Condition.visible, BASE_WAITING).click();
        successMessages
                .findBy(Condition.exactText("Your changes have been saved."))
                .should(Condition.appear, BASE_WAITING);
        descriptionInput.shouldHave(Condition.value(descriptionData));
        return descriptionData;
    }


}
