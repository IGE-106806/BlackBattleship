package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Class para a US16 – Como jogador, quero alterar o idioma da interface.
 *
 * <p>Encapsula os localizadores e as operações sobre os elementos da página
 * necessários para testar a alteração de idioma em papergames.io/battleship.</p>
 */
public class UserStory16 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** Localizador do item "Language" no diálogo de definições. */
    private final By languageMenuItem = By.xpath("//*[normalize-space(text())='Language']");

    /** Localizador da opção Português na lista de idiomas. */
    private final By portugueseOption = By.xpath(
        "//*[contains(text(),'Português') or contains(text(),'Portuguese')" +
        " or normalize-space(text())='PT' or normalize-space(text())='pt']"
    );

    /** Localizador de conteúdo em português após mudança de idioma. */
    private final By portugueseContent = By.xpath(
        "//*[contains(text(),'Jogar') or contains(text(),'amigo')" +
        " or contains(text(),'Como jogar') or contains(text(),'Regras')]"
    );

    /**
     * Constrói a Page Object com o driver fornecido.
     *
     * @param driver instância do WebDriver a utilizar
     */
    public UserStory16(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    /**
     * Abre o menu de definições clicando no botão de engrenagem.
     * O botão é localizado pelo tooltip "Settings" via JavaScript,
     * pois o seu identificador dinâmico impede o uso direto de CSS/XPath.
     */
    public void openSettingsMenu() {
        ((JavascriptExecutor) driver).executeScript(
            "var tooltips = document.querySelectorAll('[role=tooltip]');" +
            "for (var i = 0; i < tooltips.length; i++) {" +
            "    if (tooltips[i].textContent.trim() === 'Settings') {" +
            "        var id = tooltips[i].id;" +
            "        var btn = document.querySelector('[aria-describedby=\"' + id + '\"]');" +
            "        if (btn) { btn.click(); break; }" +
            "    }" +
            "}"
        );
    }

    /**
     * Clica no item "Language" dentro do menu de definições.
     */
    public void clickLanguageOption() {
        wait.until(ExpectedConditions.elementToBeClickable(languageMenuItem)).click();
    }

    /**
     * Seleciona o idioma Português na lista de idiomas disponíveis.
     */
    public void selectPortuguese() {
        wait.until(ExpectedConditions.elementToBeClickable(portugueseOption)).click();
    }

    /**
     * Obtém a URL atual do browser.
     *
     * @return URL atual
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Verifica se existe conteúdo em português visível na página.
     *
     * @return {@code true} se algum texto em português estiver visível
     */
    public boolean hasPortugueseContent() {
        return !driver.findElements(portugueseContent).isEmpty();
    }
}
