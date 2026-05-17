package iscteiul.ista.blackbattleship.testsuite_94334;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.testsuite_94334.pages.US3_CriarPartidaPage;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class US3_CriarPartidaTest {

    US3_CriarPartidaPage page = new US3_CriarPartidaPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        Configuration.timeout = 8000;
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
            "        el.style.display = 'none';" +
            "        el.style.pointerEvents = 'none';" +
            "        try { el.remove(); } catch(e) {}" +
            "    }" +
            "});"
        );
        Selenide.sleep(1000);
    }

    // US3 – Como jogador, quero criar uma nova partida para poder desafiar um amigo
    @Test
    public void us3_criarPartidaComAmigo() {
        page.playWithFriendButton.shouldBe(visible);
        page.playWithFriendButton.click();
        Selenide.sleep(1500);

        // O modal "Who are you?" só aparece na primeira sessão
        if (page.usernameInput.exists()) {
            page.usernameInput.setValue("TestPlayer");
            page.continueButton.click();
            Selenide.sleep(1500);
        }

        // O modal de seleção de jogo pode ou não aparecer
        if ($("mat-dialog-container").exists()) {
            if (page.gameSelectDropdown.exists()) {
                String selectedText = page.gameSelectDropdown.getText();
                if (selectedText.contains("Select") || selectedText.isBlank()) {
                    page.gameSelectDropdown.click();
                    page.battleshipOption.shouldBe(visible).click();
                    Selenide.sleep(500);
                }
            }
            page.continueButton.click();
            Selenide.sleep(2000);
        }

        String url = WebDriverRunner.url();
        Assertions.assertFalse(
            url.endsWith("/battleship"),
            "URL deve mudar para o lobby da partida com amigo. URL atual: " + url
        );
    }
}
