package com.example.teamcity.ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * BasePageElement is a helper parent class
 * that lets you build UI components scoped to a specific root element,
 * instead of always searching the entire page.
 */
public abstract class BasePageElement {
    private final SelenideElement element;

    public BasePageElement(SelenideElement element) {
        this.element = element;
    }

    // функциональность (методы) по поиску элемента, но не внутри всего ДОМа, а только внутри элемента
    protected SelenideElement find(By selector) { // по By
        return element.$(selector);
    } // element.$(By.id("username"));

    protected SelenideElement find(String cssSelector) { // по строке
        return element.$(cssSelector); // $ - find
    }

    protected ElementsCollection findAll(By selector) {
        return element.$$(selector);
    }

    protected ElementsCollection findAll(String cssSelector) {
        return element.$$(cssSelector); // $$ - findAll
    }
}
