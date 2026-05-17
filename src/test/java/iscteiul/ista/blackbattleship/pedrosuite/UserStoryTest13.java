package iscteiul.ista.blackbattleship.pedrosuite;

import com.codeborne.selenide.Selenide;
import iscteiul.ista.blackbattleship.pedrosuite.pages.BattleshipPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes de aceitação para a US13 – Como jogador, quero escolher quem começa o jogo
 * através das opções personalizadas do modo "Play vs robot".
 * <p>
 * Usa Selenide com o padrão Page Factory ({@code @FindBy} + {@code Selenide.page()}).
 * </p>
 */
public class UserStoryTest13 extends TestBase {

    /**
     * Verifica que é possível aceder às opções personalizadas, configurar quem
     * começa a jogar e guardar as definições.
     */
    @Test
    void us13_configurarQuemComecaJogo() {
        page(BattleshipPage.class);

        // O botão de engrenagem está aninhado dentro do cartão "Play vs robot"
        Object clicou = executeJavaScript(
            "var botoes = document.querySelectorAll('button');" +
            "for (var i = 0; i < botoes.length; i++) {" +
            "    if (botoes[i].textContent.indexOf('Play vs robot') >= 0) {" +
            "        var gear = botoes[i].querySelector('button[mat-icon-button]');" +
            "        if (gear) { gear.click(); return true; }" +
            "    }" +
            "}" +
            "return false;"
        );
        assertEquals(Boolean.TRUE, clicou,
            "Não foi possível clicar no ícone de engrenagem do 'Play vs robot'");
        Selenide.sleep(1500);

        $x("//*[contains(text(),'Custom options') or contains(text(),'Custom')]")
            .shouldBe(visible).click();
        Selenide.sleep(1500);

        // Abre o dropdown "Who plays first?"
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
}
