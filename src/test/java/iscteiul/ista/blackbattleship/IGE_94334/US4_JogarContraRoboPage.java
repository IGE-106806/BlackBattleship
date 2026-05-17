package iscteiul.ista.blackbattleship.IGE_94334;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://papergames.io/en/battleship
public class US4_JogarContraRoboPage {

    // US4 – Botão "Play vs robot"
    public SelenideElement playVsRobotButton = $x("//span[contains(text(),'Play vs robot')]");

    // US4 – Input de username no modal "Who are you?"
    public SelenideElement usernameInput = $("mat-dialog-container input");

    // US4 – Dropdown de seleção de jogo no modal
    public SelenideElement gameSelectDropdown = $("mat-dialog-container mat-select");

    // US4 – Opção Battleship no dropdown
    public SelenideElement battleshipOption = $x("//mat-option[contains(.,'Battleship') or contains(.,'battleship')]");

    // US4 – Botão "Continue" no modal
    public SelenideElement continueButton = $x("//mat-dialog-container//button[contains(.,'Continue')]");

}
