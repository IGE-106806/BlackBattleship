package iscteiul.ista.blackbattleship.testsuite_94334;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.testsuite_94334.pages.US4_JogarContraRoboPage;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class US4_JogarContraRoboTest {

    US4_JogarContraRoboPage page = new US4_JogarContraRoboPage();

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

    // US4 – Como jogador, quero jogar contra um robô para praticar sem precisar de um adversário humano
    @Test
    public void us4_jogarContraRobo() {
        page.playVsRobotButton.shouldBe(visible);
        page.playVsRobotButton.click();
        Selenide.sleep(1500);

        // O modal "Who are you?" só aparece na primeira sessão
        if (page.usernameInput.exists()) {
            page.usernameInput.setValue("TestPlayer");
            page.continueButton.click();
            Selenide.sleep(1500);
        }

        // Modal de configuração do jogo contra robô
        $("mat-dialog-container").shouldBe(visible);

        // Selecionar o jogo no dropdown se necessário
        if (page.gameSelectDropdown.exists()) {
            String selectedText = page.gameSelectDropdown.getText();
            if (selectedText.contains("Select") || selectedText.isBlank()) {
                page.gameSelectDropdown.click();
                Selenide.sleep(500);
                page.battleshipOption.shouldBe(visible).click();
                Selenide.sleep(500);
            }
        }

        // Clicar Continue via JS para evitar bloqueio do backdrop Angular Material
        executeJavaScript(
            "var btns = document.querySelectorAll('mat-dialog-container button');" +
            "for (var i = 0; i < btns.length; i++) {" +
            "  if (btns[i].textContent.toLowerCase().indexOf('continue') >= 0) {" +
            "    btns[i].click(); break;" +
            "  }" +
            "}"
        );
        Selenide.sleep(4000);

        String url = WebDriverRunner.url();
        Assertions.assertFalse(
            url.endsWith("/battleship"),
            "URL deve mudar para a página do jogo contra o robô. URL atual: " + url
        );
    }
}
