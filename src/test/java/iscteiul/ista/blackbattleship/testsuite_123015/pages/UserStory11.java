package iscteiul.ista.blackbattleship.testsuite_123015.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Page Object Class para US11.
 * Como organizador, quero criar um torneio para organizar
 * um campeonato entre vários jogadores.
 */
public class UserStory11 {

    /** Botão "Create tournament" na página principal. */
    public SelenideElement createTournamentButton = $(".position-relative > .front");

    /** Dropdown de seleção do tipo de jogo. */
    public SelenideElement gameSelectDropdown = $("#mat-select-value-serverApp0 > .mat-mdc-select-placeholder");

    /** Opção Battleship no dropdown. */
    public SelenideElement battleshipOption = $("#mat-option-serverApp0 .fw-bold");

    /** Botão "Create and share". */
    public SelenideElement createAndShareButton = $x("//button[contains(.,'Create and share')]");

    /** Botão "Go to tournament". */
    public SelenideElement goToTournamentButton = $x("//button[contains(.,'Go to tournament')]");

    /** Clica no botão de criar torneio via JavaScript para evitar bloqueios. */
    public void clicarCriarTorneio() {
        executeJavaScript("arguments[0].click();", createTournamentButton);
        Selenide.sleep(2000);
        executeJavaScript(
                "document.querySelectorAll('[class]').forEach(function(el) {" +
                        "    var cls = typeof el.className === 'string' ? el.className : '';" +
                        "    if (cls.indexOf('fc-') >= 0) {" +
                        "        el.style.display = 'none'; el.style.pointerEvents = 'none';" +
                        "        try { el.remove(); } catch(e) {}" +
                        "    }" +
                        "});"
        );
    }

    /** Seleciona o jogo Battleship no dropdown. */
    public void selecionarBattleship() {
        executeJavaScript("arguments[0].click();", gameSelectDropdown);
        Selenide.sleep(500);
        executeJavaScript("arguments[0].click();", battleshipOption);
    }

    /** Submete o formulário clicando em "Create and share". */
    public void criarEPartilhar() {
        executeJavaScript("arguments[0].click();", createAndShareButton);
    }
    /** Navega para a página do torneio criado. */
    public void irParaTorneio() {
        goToTournamentButton.click();
    }

    /** Campo do nome do torneio. */
    public SelenideElement tournamentNameInput = $("#mat-input-serverApp0");

    /**
     * Preenche o nome do torneio.
     * @param nome nome a atribuir ao torneio
     */
    public void preencherNome(String nome) {
        executeJavaScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", tournamentNameInput, nome);
    }
}