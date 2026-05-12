package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Class para a US5 – Como jogador, quero receber um link de convite
 * para partilhar com o meu adversário.
 *
 * <p>Encapsula os localizadores e as operações sobre os elementos da página
 * necessários para testar a geração de link de convite em papergames.io/battleship.</p>
 */
public class UserStory5 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** Localizador do botão "Play with a friend" na página principal. */
    private final By playWithFriendButton = By.xpath("//span[contains(text(),'Play with a friend')]");

    /** Localizador do campo de nickname no modal "Who are you?". */
    private final By nicknameInput = By.cssSelector("mat-dialog-container input");

    /** Localizador do botão "Continue" no modal. */
    private final By continueButton = By.xpath("//mat-dialog-container//button[contains(.,'Continue')]");

    /** Localizador do dropdown de seleção do jogo no modal. */
    private final By gameDropdown = By.cssSelector("mat-dialog-container mat-select");

    /** Localizador da opção "Battleship" na lista do dropdown. */
    private final By battleshipOption = By.xpath("//mat-option[contains(.,'Battleship')]");

    /** Localizadores de elementos relacionados com convite/partilha no lobby. */
    private final By inviteElements = By.xpath(
        "//*[contains(text(),'Invite') or contains(text(),'invite')" +
        " or contains(text(),'Share') or contains(text(),'share')" +
        " or contains(text(),'Copy') or contains(text(),'link')]"
    );

    /**
     * Constrói a Page Object com o driver fornecido.
     *
     * @param driver instância do WebDriver a utilizar
     */
    public UserStory5(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    /**
     * Clica no botão "Play with a friend".
     */
    public void clickPlayWithFriend() {
        wait.until(ExpectedConditions.elementToBeClickable(playWithFriendButton)).click();
    }

    /**
     * Preenche o nickname se o modal de identificação estiver visível.
     *
     * @param nickname nickname a introduzir
     */
    public void fillNicknameIfRequired(String nickname) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(nicknameInput)).sendKeys(nickname);
            wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        } catch (Exception ignored) {
            // Modal não apareceu — sessão já tem nickname guardado
        }
    }

    /**
     * Seleciona o jogo Battleship no dropdown e confirma, criando a partida.
     */
    public void selectBattleshipAndContinue() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(gameDropdown)).click();
            wait.until(ExpectedConditions.elementToBeClickable(battleshipOption)).click();
            Thread.sleep(500);
        } catch (Exception ignored) {
            // Jogo pode já estar pré-selecionado
        }
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    /**
     * Obtém a URL atual do browser (URL do lobby gerado após criar a partida).
     *
     * @return URL atual
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Verifica se existe algum elemento de convite/partilha visível na página.
     *
     * @return {@code true} se existir pelo menos um elemento de convite visível
     */
    public boolean hasInviteElement() {
        List<?> elements = driver.findElements(inviteElements);
        return !elements.isEmpty();
    }
}
