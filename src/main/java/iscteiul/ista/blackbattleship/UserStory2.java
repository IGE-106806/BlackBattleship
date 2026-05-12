package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Class para a US2 – Como jogador, quero introduzir um nickname
 * para me identificar nas partidas.
 *
 * <p>Encapsula os localizadores e as operações sobre os elementos da página
 * necessários para testar a introdução de nickname em papergames.io/battleship.</p>
 */
public class UserStory2 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** Localizador do botão "Play with a friend" na página principal. */
    private final By playWithFriendButton = By.xpath("//span[contains(text(),'Play with a friend')]");

    /** Localizador do campo de texto do nickname no modal "Who are you?". */
    private final By nicknameInput = By.cssSelector("mat-dialog-container input");

    /** Localizador do botão "Continue" no modal. */
    private final By continueButton = By.xpath("//mat-dialog-container//button[contains(.,'Continue')]");

    /**
     * Constrói a Page Object com o driver fornecido.
     *
     * @param driver instância do WebDriver a utilizar
     */
    public UserStory2(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    /**
     * Clica no botão "Play with a friend" para abrir o modal de criação de partida.
     */
    public void clickPlayWithFriend() {
        wait.until(ExpectedConditions.elementToBeClickable(playWithFriendButton)).click();
    }

    /**
     * Verifica se o modal de introdução de nickname está visível.
     *
     * @return {@code true} se o campo de nickname estiver presente e visível
     */
    public boolean isNicknameModalVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(nicknameInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Introduz o nickname no campo de texto do modal.
     *
     * @param nickname texto a introduzir como nickname
     */
    public void enterNickname(String nickname) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(nicknameInput));
        input.clear();
        input.sendKeys(nickname);
    }

    /**
     * Obtém o valor atual do campo de nickname.
     *
     * @return texto presente no campo de nickname
     */
    public String getNicknameValue() {
        return driver.findElement(nicknameInput).getAttribute("value");
    }

    /**
     * Clica no botão "Continue" do modal para confirmar o nickname.
     */
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }
}
