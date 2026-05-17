package iscteiul.ista.blackbattleship.testsuite_94334;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.testsuite_94334.pages.US8_VerRegrasPage;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class US8_VerRegrasTest {

    US8_VerRegrasPage page = new US8_VerRegrasPage();

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

    // US8 – Como jogador, quero ver as regras do jogo para perceber como jogar
    @Test
    public void us8_verRegrasDoJogo() {
        // As regras estão a cerca de metade da página principal
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight / 2)");
        Selenide.sleep(1500);

        page.howToPlaySection.shouldBe(visible);
    }
}
