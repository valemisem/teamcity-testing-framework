package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.ProjectElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectPage extends BasePage {
    private static final String PROJECT_URL = "/project/%s";
    public SelenideElement title = $("span[class*='ProjectPageHeader']");
    public ElementsCollection buildElements = $$("div[class*='BuildsByBuildType__container']");

    public static ProjectPage open(String projectId) {
        return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
    }

    public ProjectPage() {
        title.shouldBe(Condition.visible, BASE_WAITING);
    }
    // the constructor
    // when you create a ProjectPage object, the page will wait until the title is visible

    public List<ProjectElement> getBuilds() {
        return generatePageElements(buildElements, ProjectElement::new);
    }
}
