package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuildConfigurationPage extends BasePage {
    private static final String BUILD_CONFIG_URL = "/buildConfiguration/%s";
    public SelenideElement title = $("h1.ring-heading-heading");
    public ElementsCollection descriptions = $$("[class *= 'Description__text']");

    public static BuildConfigurationPage open(String buildTypeId) {
        return Selenide.open(BUILD_CONFIG_URL.formatted(buildTypeId), BuildConfigurationPage.class);
    }

    public BuildConfigurationPage() {
        title.shouldBe(Condition.visible, BASE_WAITING);
    }

    public String getDescriptionText() {
        return descriptions
                .filter(Condition.visible)
                .last() // deepest rendered text
                .getText();
    }
}
