package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Class para a US8 – Como jogador, quero ver as regras do jogo
 * para perceber como jogar.
 *
 * <p>Encapsula os localizadores e as operações sobre os elementos da página
 * necessários para testar a visibilidade das regras em papergames.io/battleship.</p>
 */
public class UserStory8 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** Localizador do título ou secção de regras/instruções do jogo. */
    private final By rulesSection = By.xpath(
        "//*[contains(text(),'How to play') or contains(text(),'how to play')" +
        " or contains(text(),'Rules') or contains(text(),'About Battleship')" +
        " or contains(text(),'About the game') or contains(text(),'Battleship rules')]"
    );

    /**
     * Constrói a Page Object com o driver fornecido.
     *
     * @param driver instância do WebDriver a utilizar
     */
    public UserStory8(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    /**
     * Faz scroll até ao fundo da página para tornar visível a secção de regras.
     */
    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Verifica se a secção de regras/instruções do jogo está visível na página.
     *
     * @return {@code true} se pelo menos um elemento com texto de regras estiver visível
     */
    public boolean isRulesSectionVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(rulesSection)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém o texto do elemento de regras encontrado na página.
     *
     * @return texto do elemento de regras, ou string vazia se não encontrado
     */
    public String getRulesSectionText() {
        try {
            return driver.findElement(rulesSection).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
