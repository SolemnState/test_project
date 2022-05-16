package ui;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class YandexHomePageTest extends BaseUiTest {

    @Test
    public void checkSearchField() {
        open("https://ya.ru");
        $(By.xpath("//div[@class='search2__input']")).shouldBe(visible);
        $(By.id("text")).setValue("test");
        String searchButtonText = $(By.xpath("//span[@class='button__text']")).getText();
        $(By.xpath("//div[@class='search2__button']//button")).click();
    }


}
