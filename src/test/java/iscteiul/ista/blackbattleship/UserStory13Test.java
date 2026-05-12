package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a US13 – Como jogador, quero escolher quem começa o jogo
 * através das opções personalizadas do modo "Play vs robot".
 *
 * <p>Testa o fluxo de acesso às opções personalizadas e a configuração de
 * quem inicia o jogo em papergames.io/battleship.</p>
 */
public class UserStory13Test {

    private WebDriver driver;
    private UserStory13 page;

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
        page = new UserStory13(driver);
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
     * Verifica que é possível aceder às opções personalizadas do jogo contra o robô,
     * configurar quem começa a jogar e guardar as definições.
     */
    @Test
    public void us13_configurarQuemComecaJogo() throws InterruptedException {
        boolean clicou = page.clickGearIcon();
        assertTrue(clicou, "Não foi possível encontrar/clicar no ícone de engrenagem do 'Play vs robot'");
        Thread.sleep(1500);

        page.clickCustomOptions();
        Thread.sleep(1500);

        page.selectIStartFirst();
        Thread.sleep(1000);

        page.saveSettings();
        Thread.sleep(1500);
    }
}
