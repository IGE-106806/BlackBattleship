package iscteiul.ista.blackbattleship;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://papergames.io/en/battleship
public class BattleshipPage {

    // US1 – Título do jogo na página principal
    public SelenideElement gameTitle = $x("//h1[contains(text(),'Battleship Online')]");
}
