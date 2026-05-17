package iscteiul.ista.blackbattleship.testsuite_123015;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.testsuite_123015.pages.UserStory14;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Test Class para US14.
 * Como jogador, quero ver o histórico das minhas partidas
 * anteriores para acompanhar a minha evolução.
 */
public class UserStory14Test {

    /** Página-objeto da US14. */
    UserStory14 page = new UserStory14();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "550x639";
        Configuration.timeout = 8000;
        Configuration.pageLoadTimeout = 60000;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    /** Verifica que o link do histórico está visível após criar sessão guest. */
    @Test
    @DisplayName("US14 - Link 'History' visível após sessão guest")
    public void us14_linkHistoricoVisivel() {
        page.historyLink.shouldBe(visible);
    }

    /** Testa a navegação para o histórico de partidas. */
    @Test
    @DisplayName("US14 - Navegar para o histórico de partidas")
    public void us14_navegarParaHistorico() {
        page.navegarParaHistorico();
        Selenide.sleep(1500);

        Assertions.assertTrue(
                WebDriverRunner.url().contains("match-history"),
                "URL deve conter 'match-history'. URL atual: " + WebDriverRunner.url()
        );
    }
}