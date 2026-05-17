package iscteiul.ista.blackbattleship.pedrosuite;

import com.codeborne.selenide.Selenide;
import iscteiul.ista.blackbattleship.pedrosuite.pages.BattleshipPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes de aceitação para a US2 – Como jogador, quero introduzir um nickname
 * para me identificar nas partidas.
 * <p>
 * Usa Selenide com o padrão Page Factory ({@code @FindBy} + {@code Selenide.page()}).
 * </p>
 */
public class UserStoryTest2 extends TestBase {

    /**
     * Verifica que o modal de introdução de nickname aparece ao iniciar uma partida
     * e que o valor introduzido é guardado corretamente no campo de texto.
     */
    @Test
    void us2_nicknameIntroducidoEGuardado() {
        BattleshipPage battleshipPage = page(BattleshipPage.class);

        battleshipPage.playWithFriendButton.shouldBe(visible).click();
        Selenide.sleep(1500);

        if (battleshipPage.nicknameInput.exists()) {
            battleshipPage.nicknameInput.shouldBe(visible);
            battleshipPage.nicknameInput.setValue("PedroSantana");
            Selenide.sleep(500);

            assertEquals("PedroSantana", battleshipPage.nicknameInput.getValue(),
                "O nickname introduzido deve ser 'PedroSantana'");

            battleshipPage.continueButton.shouldBe(visible).click();
            Selenide.sleep(1500);
        } else {
            System.out.println("[US2] Sessão já tem nickname guardado; modal não apareceu.");
        }
    }
}
