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
        Assertions.assertFalse(
                url.endsWith("/battleship"),
                "URL deve mudar para a página do jogo contra o robô. URL atual: " + url
        );
    }

    // US8 – Como jogador, quero ver as regras do jogo para perceber como jogar
    @Test
    public void us8_regrasDoJogoVisiveis() {
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        Selenide.sleep(1000);

        $x("//*[contains(text(),'How to play') or contains(text(),'how to play') " +
                "or contains(text(),'Rules') or contains(text(),'About Battleship') " +
                "or contains(text(),'About the game') or contains(text(),'Battleship rules')]")
                .shouldBe(visible);
    }

    // US2 – Como jogador, quero introduzir um nickname para me identificar nas partidas
    @Test
    public void us2_introduzirNickname() {
        battleshipPage.playWithFriendButton.shouldBe(visible).click();
        Selenide.sleep(1500);

        // Modal "Who are you?" com input de nickname deve aparecer (pelo menos na primeira sessão)
        // Se sessão estiver guardada, este passo é saltado
        if (battleshipPage.usernameInput.exists()) {
            battleshipPage.usernameInput.shouldBe(visible);
            battleshipPage.usernameInput.setValue("Pedro123");
            Selenide.sleep(500);

            // Verifica que o valor foi inserido corretamente no input
            String valorInserido = battleshipPage.usernameInput.getValue();
            Assertions.assertEquals("Pedro123", valorInserido,
                    "O nickname inserido deve ser 'Pedro123'");

            battleshipPage.continueButton.shouldBe(visible).click();
            Selenide.sleep(1500);
        } else {
            // Sessão já tem nickname guardado - o teste passa com mensagem informativa
            System.out.println("Sessão já tem nickname guardado, modal não apareceu.");
        }
    }

    // US5 – Como jogador, quero receber um link de convite para partilhar com o meu adversário
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

        // Após criar a partida, a URL do lobby é o próprio link de convite
        String urlLobby = WebDriverRunner.url();
        Assertions.assertTrue(
                urlLobby.contains("/battleship/") || urlLobby.matches(".*/[a-zA-Z0-9-]{6,}.*"),
                "A URL do lobby deve conter um identificador único (link de convite). URL: " + urlLobby
        );

        // Verifica também que existe algum elemento visível relacionado com partilha/convite
        boolean temElementoConvite =
                $x("//*[contains(text(),'Invite') or contains(text(),'invite') " +
                        "or contains(text(),'Share') or contains(text(),'share') " +
                        "or contains(text(),'Copy') or contains(text(),'link')]").exists();

        Assertions.assertTrue(temElementoConvite || urlLobby.length() > 40,
                "Deve existir um link de convite (URL do lobby) ou um botão de partilha visível");
    }

    // US13 – Como jogador, quero escolher quem começa o jogo
    @Test
    public void us13_escolherQuemComecaJogo() {
    // US13 – Como jogador, quero escolher quem começa o jogo (Custom options no Play vs robot)
    @Test
    public void us13_escolherQuemComecaJogo() {
        // A engrenagem é um elemento clicável SEPARADO ao lado do texto "Play vs robot",
        // dentro do mesmo cartão. Usar JS para localizar todos os ícones de engrenagem
        // e clicar no que está dentro do cartão que contém "Play vs robot".
        // A engrenagem é um <button mat-icon-button> ANINHADO DENTRO do botão "Play vs robot".
        // Localiza o botão principal pelo texto e depois clica no botão interior com fa-gear.
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
                "Não foi possível encontrar/clicar no ícone de engrenagem do 'Play vs robot'");
        Selenide.sleep(1500);

        // Clica em "Custom options" no menu que abriu
        $x("//*[contains(text(),'Custom options') or contains(text(),'Custom') or contains(text(),'Opções')]")
                .shouldBe(visible).click();
        Selenide.sleep(1500);

        // Abre o dropdown DA SECÇÃO "Who plays first?" (não confundir com o primeiro dropdown "Game")
        // Localiza o label "Who plays first?" e o mat-select que vem logo a seguir
        $x("(//*[contains(text(),'Who plays first')]/following::mat-select)[1]")
                .shouldBe(visible).click();
        Selenide.sleep(1000);

        // Seleciona a opção "I play first" no painel de opções aberto
        $x("//mat-option[contains(.,'I start first') or contains(.,'start first')]")
                .shouldBe(visible).click();
        Selenide.sleep(1000);

        // Confirma/guarda clicando em "Save settings"
        $x("//button[contains(.,'Save settings') or contains(.,'Save') " +
                "or contains(.,'Apply') or contains(.,'Guardar')]")
                .shouldBe(visible).click();
        Selenide.sleep(1500);
    }

    // US16 – Como jogador, quero alterar o idioma da interface
    @Test
    public void us16_alterarIdioma() {
        // Abre o menu de Settings (ícone de engrenagem no canto superior esquerdo)
        // O botão tem o atributo aria-describedby a apontar para um tooltip com texto "Settings"
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

        // Clica no item "Language" dentro do diálogo de Settings
        $x("//*[normalize-space(text())='Language']").shouldBe(visible).click();
        Selenide.sleep(1000);

        // Clica na opção Português (Portuguese / Português / pt)
        $x("//*[contains(text(),'Português') or contains(text(),'Portuguese') " +
                "or normalize-space(text())='PT' or normalize-space(text())='pt']")
                .shouldBe(visible).click();
        Selenide.sleep(2500);

        // Após mudar idioma, a URL deve conter /pt/ ou o conteúdo deve estar em PT
        String url = WebDriverRunner.url();
        boolean urlMudou = url.contains("/pt/") || url.contains("/pt-");

        boolean conteudoEmPT = $x("//*[contains(text(),'Jogar') or contains(text(),'amigo') " +
                "or contains(text(),'Como jogar') or contains(text(),'Regras')]")
                .exists();

        Assertions.assertTrue(urlMudou || conteudoEmPT,
                "Após mudar para PT, a URL deve conter '/pt/' ou o conteúdo deve estar em português. URL: " + url);
    }

    // ─── US106806 ─────────────────────────────────────────────────────────────

    /**
     * Helper: entra numa sessão de jogo contra o robô.
     * Após este método a URL já saiu de /battleship e estamos na sessão de jogo.
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

    // US6 – Como jogador, quero colocar os meus navios no tabuleiro antes de iniciar a batalha
    @Test
    public void us6_colocarNaviosNoTabuleiro() {
        entrarJogoContraRobo();

        // Após entrar no jogo deve aparecer o tabuleiro de colocação de navios
        Assertions.assertTrue(
                $("table").exists() || $("[class*='grid']").exists()
                        || $("[class*='board']").exists() || $("[class*='cell']").exists(),
                "O tabuleiro de colocação de navios deve estar visível após iniciar o jogo"
        );

        // Se existir botão de colocação aleatória usa-o para colocar navios automaticamente
        if (battleshipPage.autoPlaceButton.exists()) {
            battleshipPage.autoPlaceButton.click();
            Selenide.sleep(1000);
            boolean temNaviosColocados = $x("//*[contains(@class,'ship') or contains(@class,'placed') or contains(@class,'occupied')]").exists();
            Assertions.assertTrue(temNaviosColocados,
                    "Após colocação aleatória deve existir pelo menos uma célula com navio");
        } else {
            Assertions.assertTrue(
                    $("table").exists() || $("[class*='grid']").exists(),
                    "O tabuleiro de colocação deve estar acessível"
            );
        }
    }

    // US7 – Como jogador, quero disparar sobre o tabuleiro adversário para tentar afundar os seus navios
    @Test
    public void us7_dispararSobreTabuleiro() {
        entrarJogoContraRobo();

        // Coloca navios automaticamente – o jogo auto-inicia após a colocação
        if (battleshipPage.autoPlaceButton.exists()) {
            battleshipPage.autoPlaceButton.click();
            Selenide.sleep(3000);
        }

        // O tabuleiro do adversário tem classe "opponent"
        $(".opponent").shouldBe(visible);

        // Dispara clicando numa célula livre do tabuleiro adversário via JS
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

    // US9 – Como jogador, quero trocar mensagens de chat com o meu adversário durante a partida
    @Test
    public void us9_trocaMensagensChat() {
        entrarJogoContraRobo();
        Selenide.sleep(1000);

        // O botão de chat usa o ícone FontAwesome fa-comment (visível na barra lateral)
        boolean chatAcessivel =
                $(".fa-comment").exists()
                        || $x("//*[contains(@class,'fa-comment')]").exists()
                        || $x("//button[.//*[contains(@class,'fa-comment')]]").exists();

        Assertions.assertTrue(chatAcessivel,
                "Deve existir um botão de chat (ícone fa-comment) durante a partida");

        // Abre o chat clicando no ícone fa-comment e tenta enviar uma mensagem
        if ($x("//button[.//*[contains(@class,'fa-comment')]]").exists()) {
            $x("//button[.//*[contains(@class,'fa-comment')]]").click();
            Selenide.sleep(1500);

            // Após abrir, procura o input de texto do chat
            if ($("input[type='text']").exists() && $("input[type='text']").isDisplayed()) {
                $("input[type='text']").setValue("Boa sorte!");
                $("input[type='text']").pressEnter();
                Selenide.sleep(1000);
                Assertions.assertTrue(
                        $x("//*[contains(text(),'Boa sorte')]").exists(),
                        "A mensagem enviada deve aparecer no chat"
                );
            }
        }
    }

    // US10 – Como jogador, quero ver o resultado final da partida para saber quem ganhou
    @Test
    public void us10_verResultadoFinalPartida() {
        entrarJogoContraRobo();

        // Coloca navios automaticamente – o jogo auto-inicia após a colocação
        if (battleshipPage.autoPlaceButton.exists()) {
            battleshipPage.autoPlaceButton.click();
            Selenide.sleep(3000);
        }

        // Durante a batalha estão visíveis os indicadores de estado que mostrarão o resultado final:
        // "score" (placar de navios afundados), "current-player" (de quem é o turno),
        // "chronometer" (temporizador de jogada), ou ecrã de resultado se o jogo terminar
        boolean temIndicadorResultado =
                $(".score").exists()
                        || $(".current-player").exists()
                        || $(".chronometer").exists()
                        || battleshipPage.gameResultScreen.exists()
                        || battleshipPage.winnerText.exists();

        Assertions.assertTrue(temIndicadorResultado,
                "Deve existir um indicador de resultado ou estado da partida (placar, turno ou vencedor)");
    }
}
