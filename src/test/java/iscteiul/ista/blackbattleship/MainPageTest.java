package iscteiul.ista.blackbattleship;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {
    MainPage mainPage = new MainPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.pageLoadStrategy = "eager";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("https://www.jetbrains.com/");
    }

    @Test
    public void search() throws InterruptedException {
        mainPage.searchButton.shouldBe(visible).click();
        Thread.sleep(1000);

        $("[data-test-id='search-input']").shouldBe(visible).sendKeys("Selenium");
        Thread.sleep(500);
        $("button[data-test='full-search-button']").click();
        Thread.sleep(2000);

        $("[data-test-id='search-input']").shouldHave(attribute("value", "Selenium"));
    }

    @Test
    public void toolsMenu() throws InterruptedException {
        mainPage.toolsMenu.shouldBe(visible).click();
        Thread.sleep(1000);

        $("div[data-test='main-submenu']").shouldBe(visible);
    }

    @Test
    public void navigationToAllTools() throws InterruptedException {
        mainPage.seeDeveloperToolsButton.shouldBe(visible).click();
        Thread.sleep(1000);
        mainPage.findYourToolsButton.shouldBe(visible).click(); // suggestion-link sobrepõe suggestion-action
        Thread.sleep(2000);

        $("#products-page").shouldBe(visible);
        assertEquals("All Developer Tools and Products by JetBrains", Selenide.title());
    }
}
