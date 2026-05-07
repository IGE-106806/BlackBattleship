package iscteiul.ista.blackbattleship;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://papergames.io/en/battleship
public class BattleshipPage {

    // US1 – Título do jogo na página principal
    public SelenideElement gameTitle = $x("//h1[contains(text(),'Battleship Online')]");

    // US3 – Botão "Play with a friend"
    public SelenideElement playWithFriendButton = $x("//span[contains(text(),'Play with a friend')]");

    // US3 – Input de username no modal "Who are you?" (só aparece na primeira sessão)
    public SelenideElement usernameInput = $("mat-dialog-container input");

    // US3 – Dropdown "Select a game" no modal de criação de partida
    public SelenideElement gameSelectDropdown = $("mat-dialog-container mat-select");

    // US3 – Opção Battleship na lista do dropdown
    public SelenideElement battleshipOption = $x("//mat-option[contains(.,'Battleship') or contains(.,'battleship')]");

    // US3 – Botão "Continue" nos modais (texto está num <span> filho, por isso usa contains(.))
    public SelenideElement continueButton = $x("//mat-dialog-container//button[contains(.,'Continue')]");
}
