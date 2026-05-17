package iscteiul.ista.blackbattleship.pedrosuite;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import iscteiul.ista.blackbattleship.pedrosuite.pages.BattleshipPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes de aceitação para a US16 – Como jogador, quero alterar o idioma da interface.
 * <p>
 * Usa Selenide com o padrão Page Factory ({@code @FindBy} + {@code Selenide.page()}).
 * </p>
 */
public class UserStoryTest16 extends TestBase {

    /**
     * Verifica que ao selecionar Português nas definições, a URL passa a conter
     * "/pt/" ou o conteúdo da página muda para português.
     */
    @Test
    void us16_alterarIdiomaParaPortugues() {
        BattleshipPage battleshipPage = page(BattleshipPage.class);

        // O botão de definições é identificado dinamicamente pelo seu tooltip "Settings"
        executeJavaScript(
            "var tooltips = document.querySelectorAll('[role=tooltip]');" +
            "for (var i = 0; i < tooltips.length; i++) {" +
            "    if (tooltips[i].textContent.trim() === 'Settings') {" +
            "        var btn = document.querySelector('[aria-describedby=\"' + tooltips[i].id + '\"]');" +
            "        if (btn) { btn.click(); break; }" +
            "    }" +
            "}"
        );
        Selenide.sleep(1500);

        battleshipPage.languageMenuItem.shouldBe(visible).click();
        Selenide.sleep(1000);

        battleshipPage.portugueseOption.shouldBe(visible).click();
        Selenide.sleep(2500);

        String url = WebDriverRunner.url();
        boolean urlEmPT = url.contains("/pt/") || url.contains("/pt-");
        boolean conteudoEmPT = $x("//*[contains(text(),'Jogar') or contains(text(),'amigo')]").exists();

        assertTrue(urlEmPT || conteudoEmPT,
            "Após mudar para PT, a URL deve conter '/pt/' ou o conteúdo deve estar em português. URL: " + url);
    }
}
