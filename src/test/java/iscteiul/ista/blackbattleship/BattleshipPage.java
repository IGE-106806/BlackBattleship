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
    public SelenideElement continueButton = $x("//mat-dialog-container//button[contains(.,'Continue')]");

    // US6 – Tabuleiro de colocação de navios (fase de setup)
    public SelenideElement autoPlaceButton = $x("//button[contains(.,'Random') or contains(.,'Auto') or contains(.,'Shuffle') or contains(.,'random') or contains(.,'Randomize')]");
    public SelenideElement readyButton = $x("//button[contains(.,'Ready') or contains(.,'Start battle') or contains(.,'Confirm') or contains(.,'Play')]");

    // US9 – Chat durante a partida
    public SelenideElement chatInput = $("input[placeholder*='message'], textarea[placeholder*='message']");
    public SelenideElement chatSendButton = $x("//button[contains(@class,'send') or contains(.,'Send') or contains(.,'Enviar')]");
    public SelenideElement chatMessageList = $x("//*[contains(@class,'message') or contains(@class,'chat-history') or contains(@class,'messages')]");

    // US10 – Resultado final da partida
    public SelenideElement gameResultScreen = $x("//*[contains(@class,'result') or contains(@class,'winner') or contains(@class,'game-over') or contains(@class,'victory') or contains(@class,'end')]");
    public SelenideElement winnerText = $x("//*[contains(text(),'win') or contains(text(),'Win') or contains(text(),'lose') or contains(text(),'Victory') or contains(text(),'Game over')]");
}
