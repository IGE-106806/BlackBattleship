package iscteiul.ista.blackbattleship;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {
    MainPage mainPage = new MainPage();

@BeforeAll    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        Configuration.timeout = 8000; // aumenta o timeout global para 8 segundos
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

@BeforeEach    public void setUp() {
        open("https://www.jetbrains.com/");
        // O banner de cookies (CookieHub) carrega de forma assíncrona e bloqueia cliques.
        // Aguarda que o container apareça no DOM e depois remove-o via JavaScript,
        // pois o botão de aceitar pode estar em shadow DOM inacessível ao Selenide.
        Selenide.sleep(2000);
        executeJavaScript(
            "var el = document.querySelector('.ch2-container');" +
            "if (el) el.remove();"
        );
    }

    @Test
    public void search() {
        mainPage.searchButton.click();

        // O seletor do input mudou de 'search-input' para 'input__inner'
        $("[data-test='input__inner']").shouldBe(visible);
        $("[data-test='input__inner']").sendKeys("Selenium");

        // Verifica que o campo de pesquisa contém o texto introduzido
        // (pressionar Enter navega para outra página e limpa o campo)
        $("[data-test='input__inner']").shouldHave(attribute("value", "Selenium"));
    }

    @Test
    public void toolsMenu() {
        mainPage.toolsMenu.click();

        // Aguarda que o submenu do menu "Products" apareça
        $("div[data-test='main-submenu']").shouldBe(visible);
    }

    @Test
    public void navigationToAllTools() {
        // Abre o submenu de Products (era "Developer Tools" na versão anterior)
        mainPage.seeDeveloperToolsButton.click();

        // Aguarda que o botão "Find your tool" fique visível e clica
        mainPage.findYourToolsButton.shouldBe(visible);
        mainPage.findYourToolsButton.click();

        // Valida que a página de produtos carregou corretamente
        $("#products-page").shouldBe(visible);
        assertEquals("All Developer Tools and Products by JetBrains", Selenide.title());
    }
}
