package iscteiul.ista.blackbattleship.IGE_94334;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://papergames.io/en/battleship
public class US3_CriarPartidaPage {

    // US3 – Botão "Play with a friend"
    public SelenideElement playWithFriendButton = $x("//span[contains(text(),'Play with a friend')]");

    // US3 – Input de username no modal "Who are you?"
    public SelenideElement usernameInput = $("mat-dialog-container input");

    // US3 – Dropdown de seleção de jogo no modal
    public SelenideElement gameSelectDropdown = $("mat-dialog-container mat-select");

    // US3 – Opção Battleship no dropdown
    public SelenideElement battleshipOption = $x("//mat-option[contains(.,'Battleship') or contains(.,'battleship')]");

    // US3 – Botão "Continue" no modal
    public SelenideElement continueButton = $x("//mat-dialog-container//button[contains(.,'Continue')]");

}
