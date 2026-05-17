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
        Configuration.pageLoadTimeout = 60000;
        Configuration.pageLoadStrategy = "eager";
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

    // US1
    @Test
    public void us1_paginaPrincipalCarrega() {
        Assertions.assertTrue(
                Selenide.title().toLowerCase().contains("battleship"),
                "Título da página deve conter 'Battleship'"
        );
        battleshipPage.gameTitle.shouldBe(visible);
    }

    // US2
    @Test
    public void us2_introduzirNickname() {
        battleshipPage.playWithFriendButton.shouldBe(visible).click();
        Selenide.sleep(1500);
        if (battleshipPage.usernameInput.exists()) {
            battleshipPage.usernameInput.shouldBe(visible);
            battleshipPage.usernameInput.setValue("Pedro123");
            Selenide.sleep(500);
            String valorInserido = battleshipPage.usernameInput.getValue();
            Assertions.assertEquals("Pedro123", valorInserido,
                    "O nickname inserido deve ser 'Pedro123'");
            battleshipPage.continueButton.shouldBe(visible).click();
            Selenide.sleep(1500);
        } else {
            System.out.println("Sessão já tem nickname guardado, modal não apareceu.");
        }
    }

    // US3
    @Test
    public void us3_criarPartidaComAmigo() {
        battleshipPage.playWithFriendButton.shouldBe(visible);
        battleshipPage.playWithFriendButton.click();
        Selenide.sleep(1500);
        if (battleshipPage.usernameInput.exists()) {
            battleshipPage.usernameInput.setValue("TestPlayer");
            battleshipPage.continueButton.click();
            Selenide.sleep(1500);
        }
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
        String url = WebDriverRunner.url();
        Assertions.assertFalse(url.endsWith("/battleship"),
                "URL deve mudar para o lobby. URL atual: " + url);
    }

    // US4
    @Test
    public void us4_jogarContraRobo() {
        battleshipPage.playVsRobotButton.shouldBe(visible);
        battleshipPage.playVsRobotButton.click();
        Selenide.sleep(1500);
        if (battleshipPage.usernameInput.exists()) {
            battleshipPage.usernameInput.setValue("TestPlayer");
            battleshipPage.continueButton.click();
            Selenide.sleep(1500);
        }
        $x("//mat-dialog-container[contains(.,'Play vs robot')]").shouldBe(visible);
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
        String url = WebDriverRunner.url();
        Assertions.assertFalse(url.endsWith("/battleship"),
                "URL deve mudar para o jogo. URL atual: " + url);
    }

    // US5
    @Test
    public void us5_receberLinkConvite() {
        battleshipPage.playWithFriendButton.shouldBe(visible).click();
        Selenide.sleep(1500);
        if (battleshipPage.usernameInput.exists()) {
            battleshipPage.usernameInput.setValue("TestPlayer");
            battleshipPage.continueButton.click();
            Selenide.sleep(1500);
        }
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
            Selenide.sleep(2500);
        }
        String urlLobby = WebDriverRunner.url();
        Assertions.assertTrue(
                urlLobby.contains("/battleship/") || urlLobby.matches(".*/[a-zA-Z0-9-]{6,}.*"),
                "URL do lobby deve ter identificador único. URL: " + urlLobby);
        boolean temElementoConvite =
                $x("//*[contains(text(),'Invite') or contains(text(),'invite') " +
                        "or contains(text(),'Share') or contains(text(),'share') " +
                        "or contains(text(),'Copy') or contains(text(),'link')]").exists();
        Assertions.assertTrue(temElementoConvite || urlLobby.length() > 40,
                "Deve existir link de convite ou botão de partilha");
    }

    // US8
    @Test
    public void us8_regrasDoJogoVisiveis() {
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        Selenide.sleep(1000);
        $x("//*[contains(text(),'How to play') or contains(text(),'how to play') " +
                "or contains(text(),'Rules') or contains(text(),'About Battleship') " +
                "or contains(text(),'About the game') or contains(text(),'Battleship rules')]")
                .shouldBe(visible);
    }

    // US13
    @Test
    public void us13_escolherQuemComecaJogo() {
        Object clicado = executeJavaScript(
                "var botoes = document.querySelectorAll('button');" +
                        "for (var i = 0; i < botoes.length; i++) {" +
                        "    if (botoes[i].textContent.indexOf('Play vs robot') >= 0) {" +
                        "        var gear = botoes[i].querySelector('button[mat-icon-button]');" +
                        "        if (gear) { gear.click(); return true; }" +
                        "    }" +
                        "}" +
                        "return false;"
        );
        Assertions.assertEquals(Boolean.TRUE, clicado,
                "Não foi possível clicar na engrenagem do 'Play vs robot'");
        Selenide.sleep(1500);
        $x("//*[contains(text(),'Custom options') or contains(text(),'Custom')]")
                .shouldBe(visible).click();
        Selenide.sleep(1500);
        $x("(//*[contains(text(),'Who plays first')]/following::mat-select)[1]")
                .shouldBe(visible).click();
        Selenide.sleep(1000);
        $x("//mat-option[contains(.,'I start first') or contains(.,'start first')]")
                .shouldBe(visible).click();
        Selenide.sleep(1000);
        $x("//button[contains(.,'Save settings') or contains(.,'Save') or contains(.,'Apply')]")
                .shouldBe(visible).click();
        Selenide.sleep(1500);
    }

    // ─── US106806 ─────────────────────────────────────────────────────────────

    /**
     * Helper: entra numa sessão de jogo contra o robô.
     */
    private void entrarJogoContraRobo() {
        battleshipPage.playVsRobotButton.shouldBe(visible).click();
        Selenide.sleep(1500);
        if (battleshipPage.usernameInput.exists()) {
            battleshipPage.usernameInput.setValue("TestPlayer106806");
            battleshipPage.continueButton.click();
            Selenide.sleep(1500);
        }
        if ($("mat-dialog-container").exists()) {
            if (battleshipPage.gameSelectDropdown.exists()) {
                String sel = battleshipPage.gameSelectDropdown.getText();
                if (sel.contains("Select") || sel.isBlank()) {
                    battleshipPage.gameSelectDropdown.click();
                    battleshipPage.battleshipOption.shouldBe(visible).click();
                    Selenide.sleep(500);
                }
            }
            battleshipPage.continueButton.click();
            Selenide.sleep(3000);
        }
    }

    // US6
    @Test
    public void us6_colocarNaviosNoTabuleiro() {
        entrarJogoContraRobo();
        $(".opponent").shouldBe(visible);
        if (battleshipPage.autoPlaceButton.exists()) {
            battleshipPage.autoPlaceButton.click();
            Selenide.sleep(1000);
            boolean temNaviosColocados = $x("//*[contains(@class,'ship') or contains(@class,'placed') or contains(@class,'occupied')]").exists();
            Assertions.assertTrue(temNaviosColocados,
                    "Após colocação aleatória deve existir pelo menos uma célula com navio");
        } else {
            Assertions.assertTrue(
                    $("table").exists() || $("[class*='grid']").exists(),
                    "O tabuleiro de colocação deve estar acessível");
        }
    }

    // US7
    @Test
    public void us7_dispararSobreTabuleiro() {
        entrarJogoContraRobo();
        if (battleshipPage.autoPlaceButton.exists()) {
            battleshipPage.autoPlaceButton.click();
            Selenide.sleep(3000);
        }
        $(".opponent").shouldBe(visible);
        Boolean disparou = (Boolean) executeJavaScript(
                "var celulas = document.querySelectorAll('.opponent td');" +
                        "for (var i = 0; i < celulas.length; i++) {" +
                        "    var cls = celulas[i].className || '';" +
                        "    if (cls.indexOf('hit') < 0 && cls.indexOf('miss') < 0) {" +
                        "        celulas[i].click(); return true;" +
                        "    }" +
                        "}" +
                        "if (celulas.length > 0) { celulas[0].click(); return true; }" +
                        "return false;"
        );
        Selenide.sleep(1500);
        Assertions.assertTrue(Boolean.TRUE.equals(disparou),
                "Deve ser possível clicar numa célula do tabuleiro adversário para disparar");
    }

    // US9
    @Test
    public void us9_trocaMensagensChat() {
        entrarJogoContraRobo();
        Selenide.sleep(1000);
        boolean chatAcessivel =
                $(".fa-comment").exists()
                        || $x("//*[contains(@class,'fa-comment')]").exists()
                        || $x("//button[.//*[contains(@class,'fa-comment')]]").exists();
        Assertions.assertTrue(chatAcessivel,
                "Deve existir um botão de chat (ícone fa-comment) durante a partida");
        if ($x("//button[.//*[contains(@class,'fa-comment')]]").exists()) {
            $x("//button[.//*[contains(@class,'fa-comment')]]").click();
            Selenide.sleep(1500);
            if ($("input[type='text']").exists() && $("input[type='text']").isDisplayed()) {
                $("input[type='text']").setValue("Boa sorte!");
                $("input[type='text']").pressEnter();
                Selenide.sleep(1000);
                Assertions.assertTrue(
                        $x("//*[contains(text(),'Boa sorte')]").exists(),
                        "A mensagem enviada deve aparecer no chat");
            }
        }
    }

    // US10
    @Test
    public void us10_verResultadoFinalPartida() {
        entrarJogoContraRobo();
        if (battleshipPage.autoPlaceButton.exists()) {
            battleshipPage.autoPlaceButton.click();
            Selenide.sleep(3000);
        }
        boolean temIndicadorResultado =
                $(".score").exists()
                        || $(".current-player").exists()
                        || $(".chronometer").exists()
                        || battleshipPage.gameResultScreen.exists()
                        || battleshipPage.winnerText.exists();
        Assertions.assertTrue(temIndicadorResultado,
                "Deve existir um indicador de resultado ou estado da partida");
    }

    // US11
    @Test
    public void us11_criarTorneio() {
        battleshipPage.createTournamentButton.shouldBe(visible);
        battleshipPage.createTournamentButton.click();
        Selenide.sleep(1500);
        String url = WebDriverRunner.url();
        Assertions.assertTrue(url.contains("tournament"),
                "URL deve conter 'tournament'. URL atual: " + url);
    }

    // US12
    @Test
    public void us12_acederLoja() {
        open("https://papergames.io/en/battleship");
        Selenide.sleep(5000);
        if ($(".fc-cta-consent").exists()) {
            $(".fc-cta-consent").click();
        }
        executeJavaScript("arguments[0].click();", battleshipPage.shopLink);
        Selenide.sleep(2000);
        Assertions.assertTrue(WebDriverRunner.url().contains("/shop"),
                "Deveria estar na página da loja.");
    }

    // US14
    @Test
    public void us14_verHistoricoPartidas() {
        battleshipPage.historyLink.shouldBe(visible);
        battleshipPage.historyLink.click();
        Selenide.sleep(1500);
        String url = WebDriverRunner.url();
        Assertions.assertTrue(url.contains("history") || url.contains("profile"),
                "URL deve conter 'history' ou 'profile'. URL atual: " + url);
    }

    // US15
    @Test
    public void us15_partilharResultado() {
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        Selenide.sleep(1000);
        battleshipPage.shareButton.shouldBe(visible);
        Assertions.assertTrue(battleshipPage.shareButton.exists(),
                "Deve existir um botão ou link de partilha nas redes sociais");
    }

    // US16
    @Test
    public void us16_alterarIdioma() {
        executeJavaScript(
                "var tooltips = document.querySelectorAll('[role=tooltip]');" +
                        "for (var i = 0; i < tooltips.length; i++) {" +
                        "    if (tooltips[i].textContent.trim() === 'Settings') {" +
                        "        var id = tooltips[i].id;" +
                        "        var btn = document.querySelector('[aria-describedby=\"' + id + '\"]');" +
                        "        if (btn) { btn.click(); break; }" +
                        "    }" +
                        "}"
        );
        Selenide.sleep(1500);
        $x("//*[normalize-space(text())='Language']").shouldBe(visible).click();
        Selenide.sleep(1000);
        $x("//*[normalize-space(text())='Language']").shouldBe(visible).click();
        Selenide.sleep(1000);
        $x("//*[contains(text(),'Português') or contains(text(),'Portuguese') " +
                "or normalize-space(text())='PT' or normalize-space(text())='pt']")
                .shouldBe(visible).click();
        Selenide.sleep(2500);
        String url = WebDriverRunner.url();
        boolean urlMudou = url.contains("/pt/") || url.contains("/pt-");
        boolean conteudoEmPT = $x("//*[contains(text(),'Jogar') or contains(text(),'amigo') " +
                "or contains(text(),'Como jogar') or contains(text(),'Regras')]").exists();
        Assertions.assertTrue(urlMudou || conteudoEmPT,
                "Após mudar para PT, URL deve conter '/pt/' ou conteúdo em português. URL: " + url);
    }

}
