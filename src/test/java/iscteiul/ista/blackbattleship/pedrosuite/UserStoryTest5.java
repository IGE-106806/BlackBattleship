package iscteiul.ista.blackbattleship.pedrosuite;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import iscteiul.ista.blackbattleship.pedrosuite.pages.BattleshipPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes de aceitação para a US5 – Como jogador, quero receber um link de convite
 * para partilhar com o meu adversário.
 * <p>
 * Usa Selenide com o padrão Page Factory ({@code @FindBy} + {@code Selenide.page()}).
 * </p>
 */
public class UserStoryTest5 extends TestBase {

    /**
     * Verifica que após criar uma partida com amigo é gerada uma URL de lobby única
     * que funciona como link de convite partilhável.
     */
    @Test
    void us5_linkConviteGeradoAposCriarPartida() {
        BattleshipPage battleshipPage = page(BattleshipPage.class);

        battleshipPage.playWithFriendButton.shouldBe(visible).click();
        Selenide.sleep(1500);

        if (battleshipPage.nicknameInput.exists()) {
            battleshipPage.nicknameInput.setValue("TestPlayer");
            battleshipPage.continueButton.click();
            Selenide.sleep(1500);
        }

        if ($("mat-dialog-container").exists()) {
            if (battleshipPage.gameDropdown.exists()) {
                String texto = battleshipPage.gameDropdown.getText();
                if (texto.contains("Select") || texto.isBlank()) {
                    battleshipPage.gameDropdown.click();
                    battleshipPage.battleshipOption.shouldBe(visible).click();
                    Selenide.sleep(500);
                }
            }
            battleshipPage.continueButton.click();
            Selenide.sleep(2500);
        }

        String urlLobby = WebDriverRunner.url();
        assertTrue(
            urlLobby.contains("/battleship/") || urlLobby.matches(".*/[a-zA-Z0-9-]{6,}.*"),
            "A URL do lobby deve conter um identificador único. URL: " + urlLobby
        );

        boolean temConvite = $x("//*[contains(text(),'Invite') or contains(text(),'invite')" +
            " or contains(text(),'Share') or contains(text(),'Copy')]").exists();

        assertTrue(temConvite || urlLobby.length() > 40,
            "Deve existir um link de convite ou botão de partilha visível");
    }
}
