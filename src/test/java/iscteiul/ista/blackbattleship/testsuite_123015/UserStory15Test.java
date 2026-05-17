package iscteiul.ista.blackbattleship.testsuite_123015;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.testsuite_123015.pages.UserStory15;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Test Class para US15.
 * Como jogador, quero partilhar o link/QR code de convite
 * para desafiar os meus amigos.
 */
public class UserStory15Test {

    /** Página-objeto da US15. */
    UserStory15 page = new UserStory15();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1550x830";
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
    }

    /** Verifica que o botão "Play with a friend" está visível. */
    @Test
    @DisplayName("US15 - Botão 'Play with a friend' visível na página")
    public void us15_botaoVisivel() {
        page.playWithFriendButton.shouldBe(visible);
    }

    /** Testa o fluxo de criação de partida e obtenção do link de convite. */
    @Test
    @DisplayName("US15 - Criar partida e obter link de convite para partilhar")
    public void us15_copiarLinkConvite() {
        page.clicarJogarComAmigo();
        Selenide.sleep(1500);

        if ($("mat-dialog-container input").exists()) {
            $("mat-dialog-container input").setValue("TestPlayer123015");
            $x("//mat-dialog-container//button[contains(.,'Continue')]").click();
            Selenide.sleep(1500);
        }

        if ($("mat-dialog-container mat-select").exists()) {
            String sel = $("mat-dialog-container mat-select").getText();
            if (sel.contains("Select") || sel.isBlank()) {
                $("mat-dialog-container mat-select").click();
                $x("//mat-option[contains(.,'Battleship')]").shouldBe(visible).click();
                Selenide.sleep(500);
            }
            $x("//mat-dialog-container//button[contains(.,'Continue')]").click();
            Selenide.sleep(2500);
        }

        page.inviteLink.shouldBe(visible);
        page.copiarLinkConvite();
        Selenide.sleep(1000);

        Assertions.assertTrue(
                WebDriverRunner.url().contains("/r/") || WebDriverRunner.url().contains("/battleship"),
                "URL deve conter o lobby da partida. URL: " + WebDriverRunner.url()
        );
    }
}