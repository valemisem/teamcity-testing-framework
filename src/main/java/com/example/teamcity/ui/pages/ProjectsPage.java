package com.example.teamcity.ui.pages;

import com.codeborne.selenide.*;
import com.example.teamcity.ui.elements.ProjectElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectsPage extends BasePage {
    private static final String PROJECTS_URL = "/favorite/projects";

    public ElementsCollection projectElements = $$("div[class*='Subproject__container']");

    //    private SelenideElement header = $("span[class*='ProjectPageHeader__title']"); // only for local setup with existing projects
    private SelenideElement header = $("a[data-test='create-project']");

    public static ProjectsPage open() {
        return Selenide.open(PROJECTS_URL, ProjectsPage.class);
    }

    public ProjectsPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }

    public ProjectsPage waitForProjects() {
        projectElements.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1), BASE_WAITING);
        return this;
    }

    public List<ProjectElement> getProjects() {
        return generatePageElements(projectElements, ProjectElement::new);
        // функцию, которую я передаю - это конструктор ProjectElement::new
        // ProjectElement у нас создается по selenide element (name, link, button)
        // десериализация по name, link, button

        // ProjectElement::new    ==   new ProjectElement(selenideElement)

        // ProjectElement::new - это method reference, ссылка на конструктор
    }
}
