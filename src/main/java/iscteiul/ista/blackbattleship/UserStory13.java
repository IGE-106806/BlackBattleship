package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Class para a US13 – Como jogador, quero escolher quem começa o jogo
 * através das opções personalizadas do modo "Play vs robot".
 *
 * <p>Encapsula os localizadores e as operações sobre os elementos da página
 * necessários para testar as opções personalizadas em papergames.io/battleship.</p>
 */
public class UserStory13 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** Localizador do item "Custom options" no menu de engrenagem. */
    private final By customOptionsItem = By.xpath(
        "//*[contains(text(),'Custom options') or contains(text(),'Custom')]"
    );

    /** Localizador do dropdown "Who plays first?" no painel de opções personalizadas. */
    private final By whoPlaysFirstDropdown = By.xpath(
        "(//*[contains(text(),'Who plays first')]/following::mat-select)[1]"
    );

    /** Localizador da opção "I start first" na lista do dropdown. */
    private final By iStartFirstOption = By.xpath(
        "//mat-option[contains(.,'I start first') or contains(.,'start first')]"
    );

    /** Localizador do botão "Save settings" para confirmar as opções. */
    private final By saveSettingsButton = By.xpath(
        "//button[contains(.,'Save settings') or contains(.,'Save') or contains(.,'Apply')]"
    );

    /**
     * Constrói a Page Object com o driver fornecido.
     *
     * @param driver instância do WebDriver a utilizar
     */
    public UserStory13(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    /**
     * Clica no ícone de engrenagem dentro do cartão "Play vs robot".
     * O botão está aninhado dentro do cartão e é localizado via JavaScript.
     *
     * @return {@code true} se o clique foi efetuado com sucesso
     */
    public boolean clickGearIcon() {
        Object result = ((JavascriptExecutor) driver).executeScript(
            "var botoes = document.querySelectorAll('button');" +
            "for (var i = 0; i < botoes.length; i++) {" +
            "    if (botoes[i].textContent.indexOf('Play vs robot') >= 0) {" +
            "        var gear = botoes[i].querySelector('button[mat-icon-button]');" +
            "        if (gear) { gear.click(); return true; }" +
            "    }" +
            "}" +
            "return false;"
        );
        return Boolean.TRUE.equals(result);
    }

    /**
     * Clica em "Custom options" no menu que abre após clicar na engrenagem.
     */
    public void clickCustomOptions() {
        wait.until(ExpectedConditions.elementToBeClickable(customOptionsItem)).click();
    }

    /**
     * Abre o dropdown "Who plays first?" e seleciona a opção "I start first".
     */
    public void selectIStartFirst() {
        wait.until(ExpectedConditions.elementToBeClickable(whoPlaysFirstDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(iStartFirstOption)).click();
    }

    /**
     * Clica em "Save settings" para guardar as opções personalizadas.
     */
    public void saveSettings() {
        wait.until(ExpectedConditions.elementToBeClickable(saveSettingsButton)).click();
    }
}
