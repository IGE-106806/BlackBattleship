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
        // O banner de cookies do papergames usa a framework Fides (prefixo fc-)
        // Aguarda que o banner carregue completamente antes de o remover
        Selenide.sleep(4000);
        executeJavaScript(
            // Esconde e remove todos os elementos com classe fc- (cookie banner Fides)
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
        // Verifica que o título HTML da página contém "Battleship"
        Assertions.assertTrue(
            Selenide.title().toLowerCase().contains("battleship"),
            "Título da página deve conter 'Battleship'"
        );
        // Verifica que o título h1 "Battleship Online" está visível
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
        // Se aparecer, interage com ele; se não aparecer, o lobby foi aberto diretamente
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

        // Em qualquer caso, a URL deve ter mudado para o lobby da partida com amigo
        String url = WebDriverRunner.url();
        Assertions.assertFalse(
            url.endsWith("/battleship"),
            "URL deve mudar para o lobby da partida com amigo. URL atual: " + url
        );
    }

    // US4 – Como jogador, quero jogar contra um robô para praticar sem precisar de adversário humano
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

        // Modal "Play vs robot" deve estar visível (Battleship pode já estar pré-selecionado)
        $x("//mat-dialog-container[contains(.,'Play vs robot')]").shouldBe(visible);

        // Se o dropdown de seleção de jogo existir e ainda não tiver nada escolhido, seleciona Battleship
        if (battleshipPage.gameSelectDropdown.exists()) {
            String selectedText = battleshipPage.gameSelectDropdown.getText();
            if (selectedText.contains("Select") || selectedText.isBlank()) {
                battleshipPage.gameSelectDropdown.click();
                battleshipPage.battleshipOption.shouldBe(visible).click();
                Selenide.sleep(500);
            }
        }

        // Confirma o início do jogo contra o robô
        battleshipPage.continueButton.click();

        // O jogo contra o robô inicia: a URL muda para a página de jogo
        Selenide.sleep(2000);
        String url = WebDriverRunner.url();
        Assertions.assertFalse(
            url.endsWith("/battleship"),
            "URL deve mudar para a página do jogo contra o robô. URL atual: " + url
        );
    }

    // US8 – Como jogador, quero ver as regras do jogo para perceber como jogar
    @Test
    public void us8_regrasDoJogoVisiveis() {
        // As regras estão na secção inferior da página principal; faz scroll até lá
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        Selenide.sleep(1000);

        // Verifica que existe conteúdo com as regras/instruções do jogo
        $x("//*[contains(text(),'How to play') or contains(text(),'how to play') " +
           "or contains(text(),'Rules') or contains(text(),'About Battleship') " +
           "or contains(text(),'About the game') or contains(text(),'Battleship rules')]")
            .shouldBe(visible);
    }
}
