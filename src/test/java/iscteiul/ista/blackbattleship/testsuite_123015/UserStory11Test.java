package iscteiul.ista.blackbattleship.testsuite_123015;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.testsuite_123015.pages.UserStory11;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Test Class para US11.
 * Como organizador, quero criar um torneio para organizar
 * um campeonato entre vários jogadores.
 */
public class UserStory11Test {

    /** Página-objeto da US11. */
    UserStory11 page = new UserStory11();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "550x639";
        Configuration.timeout = 8000;
        Configuration.pageLoadTimeout = 60000;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("https://papergames.io/en/battleship");
        Selenide.sleep(4000);
        executeJavaScript(
                "document.querySelectorAll('[class]').forEach(function(el) {" +
                        "    var cls = typeof el.className === 'string' ? el.className : '';" +
                        "    if (cls.indexOf('fc-') >= 0) {" +
                        "        el.style.display = 'none'; el.style.pointerEvents = 'none';" +
                        "        try { el.remove(); } catch(e) {}" +
                        "    }" +
                        "});"
        );
        Selenide.sleep(1000);
        if ($("mat-dialog-container input").exists()) {
            $("mat-dialog-container input").setValue("TestPlayer123015");
            $x("//mat-dialog-container//button[contains(.,'Continue')]").click();
            Selenide.sleep(2000);
        } else if ($x("//button[contains(.,'Play with a friend')]").exists()) {
            $x("//button[contains(.,'Play with a friend')]").click();
            Selenide.sleep(1500);
            if ($("mat-dialog-container input").exists()) {
                $("mat-dialog-container input").setValue("TestPlayer123015");
                $x("//mat-dialog-container//button[contains(.,'Continue')]").click();
                Selenide.sleep(2000);
            }
            open("https://papergames.io/en/battleship");
            Selenide.sleep(2000);
        }
    }

    /** Verifica que o botão de criar torneio está visível. */
    @Test
    @DisplayName("US11 - Botão de criar torneio visível na página principal")
    public void us11_botaoVisivel() {
        page.createTournamentButton.shouldBe(visible);
    }

    /** Testa o fluxo completo de criação de torneio. */
    @Test
    @DisplayName("US11 - Criar torneio redireciona para autenticação")
    public void us11_criarTorneio() {
        page.clicarCriarTorneio();
        Selenide.sleep(2000);

        String url = WebDriverRunner.url();
        Assertions.assertTrue(
                url.contains("tournament") || url.contains("login") || url.contains("sign"),
                "Deve navegar para criar torneio ou pedir autenticação. URL: " + url
        );
    }
}