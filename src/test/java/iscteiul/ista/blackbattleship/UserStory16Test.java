package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a US16 – Como jogador, quero alterar o idioma da interface.
 *
 * <p>Testa que é possível alterar o idioma da interface para Português através
 * do menu de definições, verificando a mudança na URL ou no conteúdo da página.</p>
 */
public class UserStory16Test {

    private WebDriver driver;
    private UserStory16 page;

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
        page = new UserStory16(driver);
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
     * Verifica que ao selecionar Português nas definições, a URL passa a conter
     * o segmento "/pt/" ou o conteúdo da página muda para português.
     */
    @Test
    public void us16_alterarIdiomaParaPortugues() throws InterruptedException {
        page.openSettingsMenu();
        Thread.sleep(1500);

        page.clickLanguageOption();
        Thread.sleep(1000);

        page.selectPortuguese();
        Thread.sleep(2500);

        String url = page.getCurrentUrl();
        boolean urlEmPT = url.contains("/pt/") || url.contains("/pt-");
        boolean conteudoEmPT = page.hasPortugueseContent();

        assertTrue(urlEmPT || conteudoEmPT,
            "Após mudar para PT, a URL deve conter '/pt/' ou o conteúdo deve estar em português. URL: " + url);
    }
}
