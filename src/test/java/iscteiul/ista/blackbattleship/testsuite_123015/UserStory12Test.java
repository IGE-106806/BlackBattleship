package iscteiul.ista.blackbattleship.testsuite_123015;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.testsuite_123015.pages.UserStory12;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Test Class para US12.
 * Como jogador, quero aceder à loja para consultar
 * os itens e cosméticos disponíveis.
 */
public class UserStory12Test {

    /** Página-objeto da US12. */
    UserStory12 page = new UserStory12();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1550x830";
        Configuration.timeout = 8000;
        Configuration.pageLoadTimeout = 60000;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("https://papergames.io/en/shop");
        Selenide.sleep(2000);
    }

    /**
     * Testa navegação para a loja e consulta de itens.
     */
    @Test
    @DisplayName("US12 - Navegar para a loja e consultar itens")
    public void us12_navegarParaLoja() {
        Assertions.assertTrue(
                WebDriverRunner.url().contains("shop"),
                "URL deve conter 'shop'."
        );
        page.coinsImage.shouldBe(visible);
        page.abrirCoins();
        Selenide.sleep(1500);
        Assertions.assertTrue(
                WebDriverRunner.url().contains("shop"),
                "Deve estar numa página da loja após clicar em Coins."
        );
    }
}