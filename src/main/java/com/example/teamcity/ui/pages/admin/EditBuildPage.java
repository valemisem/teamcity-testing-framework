package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class EditBuildPage {
    private SelenideElement header = $("h2.noBorder");

    public static EditBuildPage open(String buildId) {
        return Selenide.open("/admin/editBuildRunners.html?id=buildType:%s".formatted(buildId), EditBuildPage.class);
    }

    public EditBuildPage() {
        header.shouldHave(exactText("Build Steps"));
    }
}
