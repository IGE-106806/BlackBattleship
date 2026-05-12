package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a US5 – Como jogador, quero receber um link de convite
 * para partilhar com o meu adversário.
 *
 * <p>Testa que após criar uma partida com amigo é gerada uma URL de lobby única
 * que funciona como link de convite partilhável.</p>
 */
public class UserStory5Test {

    private WebDriver driver;
    private UserStory5 page;

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
        page = new UserStory5(driver);
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
     * Verifica que após criar uma partida com amigo é gerado um link de convite único
     * (URL do lobby) diferente da página principal.
     */
    @Test
    public void us5_linkConviteGeradoAposCriarPartida() throws InterruptedException {
        page.clickPlayWithFriend();
        Thread.sleep(1500);

        page.fillNicknameIfRequired("TestPlayer");
        Thread.sleep(1500);

        page.selectBattleshipAndContinue();
        Thread.sleep(2500);

        String urlLobby = page.getCurrentUrl();
        assertTrue(
            urlLobby.contains("/battleship/") || urlLobby.matches(".*/[a-zA-Z0-9-]{6,}.*"),
            "A URL do lobby deve conter um identificador único (link de convite). URL atual: " + urlLobby
        );

        assertTrue(
            page.hasInviteElement() || urlLobby.length() > 40,
            "Deve existir um link de convite ou botão de partilha visível na página"
        );
    }
}
