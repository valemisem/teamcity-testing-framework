package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class CreateBuildStepsPage {
    private SelenideElement header = $("h2.noBorder");

    public CreateBuildStepsPage shouldBeOpened() {
        header.shouldHave(exactText("Auto-detected Build Steps"));
        return this;
    }
}
