package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a US8 – Como jogador, quero ver as regras do jogo
 * para perceber como jogar.
 *
 * <p>Testa que a secção de regras/instruções do jogo está visível na página principal
 * de papergames.io/battleship.</p>
 */
public class UserStory8Test {

    private WebDriver driver;
    private UserStory8 page;

    /**
     * Inicializa o WebDriver e abre a página antes de cada teste.
     */
    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://papergames.io/en/battleship");
        try { Thread.sleep(4000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        page = new UserStory8(driver);
    }

    /**
     * Fecha o browser após cada teste.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Verifica que a secção de regras do jogo é visível após fazer scroll
     * até ao fundo da página principal.
     */
    @Test
    public void us8_regrasDoJogoVisiveis() throws InterruptedException {
        page.scrollToBottom();
        Thread.sleep(1000);

        assertTrue(
            page.isRulesSectionVisible(),
            "A secção de regras/instruções do jogo deve estar visível na página"
        );
    }

    /**
     * Verifica que o texto da secção de regras não está vazio.
     */
    @Test
    public void us8_regrasContemTexto() throws InterruptedException {
        page.scrollToBottom();
        Thread.sleep(1000);

        String texto = page.getRulesSectionText();
        assertFalse(texto.isBlank(),
            "A secção de regras deve conter texto descritivo");
    }
}
