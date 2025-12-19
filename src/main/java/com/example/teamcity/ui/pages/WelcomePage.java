package com.example.teamcity.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.admin.CreateBuildStepsPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class WelcomePage {
    private SelenideElement header = $("a[data-test='create-project']");

    public WelcomePage shouldBeOpened() {
        header.shouldHave(exactText("Create project..."));
        return this;
    }
}
