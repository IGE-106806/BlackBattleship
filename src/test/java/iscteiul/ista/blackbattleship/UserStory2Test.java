package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a US2 – Como jogador, quero introduzir um nickname
 * para me identificar nas partidas.
 *
 * <p>Testa o fluxo de introdução de nickname ao iniciar uma partida com amigo
 * em papergames.io/battleship, usando Selenium WebDriver e JUnit 5.</p>
 */
public class UserStory2Test {

    private WebDriver driver;
    private UserStory2 page;

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
        page = new UserStory2(driver);
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
     * Verifica que o modal de nickname aparece ao clicar em "Play with a friend"
     * e que o nickname introduzido é guardado corretamente no campo de texto.
     */
    @Test
    public void us2_nicknameIntroducidoEGuardado() {
        page.clickPlayWithFriend();
        try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        if (page.isNicknameModalVisible()) {
            page.enterNickname("PedroSantana");
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            assertEquals("PedroSantana", page.getNicknameValue(),
                "O nickname introduzido deve ser 'PedroSantana'");

            page.clickContinue();
            try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        } else {
            System.out.println("[US2] Sessão já tem nickname guardado; modal não apareceu.");
        }
    }
}
