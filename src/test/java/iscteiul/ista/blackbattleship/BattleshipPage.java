package iscteiul.ista.blackbattleship;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://papergames.io/en/battleship
public class BattleshipPage {

    // US1 – Título do jogo na página principal
    public SelenideElement gameTitle = $x("//h1[contains(text(),'Battleship Online')]");

    // US1/US3 – Botão "Play with a friend"
    public SelenideElement playWithFriendButton = $x("//span[contains(text(),'Play with a friend')]");

    // US4 – Botão "Play vs robot"
    public SelenideElement playVsRobotButton = $x("//span[contains(text(),'Play vs robot')]");

    // US2 – Botão de Login (acesso ao perfil/nickname)
    public SelenideElement loginButton = $x("//a[contains(@href,'login') or contains(text(),'Login') or contains(text(),'Sign')]");

    // US8 – Thumbnail do jogo (imagem de capa)
    public SelenideElement gameThumbnail = $("img[alt='Battleship Online']");

    // US8 – Descrição/regra principal do jogo visível na página
    public SelenideElement gameRuleDescription = $x("//*[contains(text(),'First to sink') or contains(text(),'sink all opponent')]");

    // US3/US4 – Input de username no modal "Who are you?" (só aparece na primeira sessão)
    public SelenideElement usernameInput = $("mat-dialog-container input");

    // US3/US4 – Dropdown "Select a game" no modal de criação de partida
    public SelenideElement gameSelectDropdown = $("mat-dialog-container mat-select");

    // US3/US4 – Opção Battleship na lista do dropdown
    public SelenideElement battleshipOption = $x("//mat-option[contains(.,'Battleship') or contains(.,'battleship')]");

    // US3/US4 – Botão "Continue" nos modais
    // Usa contains(.) em vez de contains(text()) porque Angular Material coloca o texto num <span> filho
    public SelenideElement continueButton = $x("//mat-dialog-container//button[contains(.,'Continue')]");
}
