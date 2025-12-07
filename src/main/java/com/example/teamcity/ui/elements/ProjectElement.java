package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class ProjectElement extends BasePageElement {
    // наш элемент - список проектов
    // нам нужно забрать: имя, ссылка, кнопка

    private SelenideElement name;
    private SelenideElement link;
    private SelenideElement button;

    // конструктор для поиска только внутри элемента
    public ProjectElement(SelenideElement element) {
        super(element); // чтобы вызвать конструктор родителя
        /**
         * this calls: BasePageElement(SelenideElement element)
         * and sets: this.element = element
         */
        this.name = find("span[class*='MiddleEllipsis']"); // ищем ТОЛЬКО ВНУТРИ ЭЛЕМЕНТА
        this.link = find("a");
        this.button = find("button");
    }
}
