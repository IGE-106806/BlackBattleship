package iscteiul.ista.blackbattleship.pedrosuite.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object para a página principal do jogo Battleship Online (https://papergames.io/en/battleship).
 * <p>
 * Implementa o padrão <em>Page Factory</em> com anotações {@code @FindBy}.
 * A instância deve ser criada via {@code Selenide.page(BattleshipPage.class)},
 * que inicializa os campos anotados com proxies do Selenide.
 * </p>
 */
public class BattleshipPage {

    /** Botão "Play with a friend" — inicia uma partida com convite para amigo. */
    @FindBy(xpath = "//span[contains(text(),'Play with a friend')]")
    public SelenideElement playWithFriendButton;

    /** Botão "Play vs robot" — inicia uma partida contra o robô. */
    @FindBy(xpath = "//span[contains(text(),'Play vs robot')]")
    public SelenideElement playVsRobotButton;

    /** Campo de texto de nickname no modal "Who are you?" (só aparece na primeira sessão). */
    @FindBy(css = "mat-dialog-container input")
    public SelenideElement nicknameInput;

    /** Botão "Continue" presente nos modais de criação de partida. */
    @FindBy(xpath = "//mat-dialog-container//button[contains(.,'Continue')]")
    public SelenideElement continueButton;

    /** Dropdown de seleção do jogo no modal de criação de partida. */
    @FindBy(css = "mat-dialog-container mat-select")
    public SelenideElement gameDropdown;

    /** Opção "Battleship" na lista do dropdown de seleção de jogo. */
    @FindBy(xpath = "//mat-option[contains(.,'Battleship')]")
    public SelenideElement battleshipOption;

    /** Item "Language" dentro do diálogo de definições. */
    @FindBy(xpath = "//*[normalize-space(text())='Language']")
    public SelenideElement languageMenuItem;

    /** Opção de idioma Português na lista de idiomas. */
    @FindBy(xpath = "//*[contains(text(),'Português') or contains(text(),'Portuguese')]")
    public SelenideElement portugueseOption;
}
