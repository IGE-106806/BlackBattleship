package iscteiul.ista.blackbattleship;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BattleshipPageTest {

    BattleshipPage battleshipPage = new BattleshipPage();

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

    // US1 – Como jogador, quero aceder à página principal do jogo para poder começar a jogar
    @Test
    public void us1_paginaPrincipalCarrega() {
        Assertions.assertTrue(
            Selenide.title().toLowerCase().contains("battleship"),
            "Título da página deve conter 'Battleship'"
        );
        battleshipPage.gameTitle.shouldBe(visible);
    }

    // US3 – Como jogador, quero criar uma nova partida para poder desafiar um amigo
    @Test
    public void us3_criarPartidaComAmigo() {
        battleshipPage.playWithFriendButton.shouldBe(visible);
        battleshipPage.playWithFriendButton.click();
        Selenide.sleep(1500);

        // O modal "Who are you?" só aparece na primeira sessão; trata-o condicionalmente
        if (battleshipPage.usernameInput.exists()) {
            battleshipPage.usernameInput.setValue("TestPlayer");
            battleshipPage.continueButton.click();
            Selenide.sleep(1500);
        }

        // O modal de seleção de jogo pode ou não aparecer (depende do estado da sessão)
        if ($("mat-dialog-container").exists()) {
            if (battleshipPage.gameSelectDropdown.exists()) {
                String selectedText = battleshipPage.gameSelectDropdown.getText();
                if (selectedText.contains("Select") || selectedText.isBlank()) {
                    battleshipPage.gameSelectDropdown.click();
                    battleshipPage.battleshipOption.shouldBe(visible).click();
                    Selenide.sleep(500);
                }
            }
            battleshipPage.continueButton.click();
            Selenide.sleep(2000);
        }

        // A URL deve ter mudado para o lobby da partida com amigo
        String url = WebDriverRunner.url();
        Assertions.assertFalse(
            url.endsWith("/battleship"),
            "URL deve mudar para o lobby da partida com amigo. URL atual: " + url
        );
    }

    // US4 – Como jogador, quero jogar contra um robô para praticar sem precisar de um adversário humano
    @Test
    public void us4_jogarContraRobo() {
        battleshipPage.playVsRobotButton.shouldBe(visible);
        battleshipPage.playVsRobotButton.click();
        Selenide.sleep(1500);

        // O modal "Who are you?" só aparece na primeira sessão; trata-o condicionalmente
        if (battleshipPage.usernameInput.exists()) {
            battleshipPage.usernameInput.setValue("TestPlayer");
            battleshipPage.continueButton.click();
            Selenide.sleep(1500);
        }

        // Deve aparecer modal para configurar a partida contra robô
        $("mat-dialog-container").shouldBe(visible);
        battleshipPage.continueButton.shouldBe(visible).click();
        Selenide.sleep(3000);

        // Após confirmar, a URL deve mudar OU o tabuleiro de jogo deve aparecer na página
        String url = WebDriverRunner.url();
        boolean urlChanged = !url.endsWith("/battleship");
        boolean gameLoaded = $x("//*[contains(@class,'board') or contains(@class,'game-') or contains(@class,'ship') or contains(@class,'grid')]").exists();

        Assertions.assertTrue(
            urlChanged || gameLoaded,
            "Após iniciar jogo vs robô, URL deve mudar ou tabuleiro deve aparecer. URL atual: " + url
        );
    }

    // US8 – Como jogador, quero ver as regras do jogo para perceber como jogar
    @Test
    public void us8_verRegrasDoJogo() {
        // As regras estão visíveis mais abaixo na página principal – rolar até ao final
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        Selenide.sleep(1500);

        // Verificar que existe uma secção com título de regras / "how to play"
        battleshipPage.howToPlaySection.shouldBe(visible);
    }
}
