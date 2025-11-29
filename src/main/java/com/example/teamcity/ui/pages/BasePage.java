package com.example.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BasePageElement;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class BasePage {
    public static final Duration BASE_WAITING = Duration.ofSeconds(30);

    protected <T extends BasePageElement> List<T> generatePageElements( // возвращаем лист типа Дженерик
            ElementsCollection collection, Function<SelenideElement, T> creator)
    // передали коллекцию, из которой потом хотим сделать список элементов (провести генерацию)
    // И функцию (описывающую конструктор) создания, назвали creator, которая умеет работать с типом данных элемент селенида и дженерик
    {
        return collection.stream().map(creator).toList();
        // я сделала последовательность элементов, с которой я могу работать
    }

    // collection.stream() ElementCollection: Selenide Element 1, Selenide Element 2 и тд
    // collection.stream() -> Конвеер: Selenide Element 1, Selenide Element 2 и тд
    // Джава по разному работает со стримом и коллекцией!
    // creator(Selenide Element 1) -> T -> add to list
    // creator(Selenide Element 2) -> T -> add to list
}
