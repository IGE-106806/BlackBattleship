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

    // US8 – Secção de regras / "How to play" na página principal
    public SelenideElement howToPlaySection = $x("//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'how to play') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'rules')]");

    // US9 – Chat durante a partida
    public SelenideElement chatInput = $("input[placeholder*='message'], textarea[placeholder*='message']");
    public SelenideElement chatSendButton = $x("//button[contains(@class,'send') or contains(.,'Send') or contains(.,'Enviar')]");
    public SelenideElement chatMessageList = $x("//*[contains(@class,'message') or contains(@class,'chat-history') or contains(@class,'messages')]");

    // US10 – Resultado final da partida
    public SelenideElement gameResultScreen = $x("//*[contains(@class,'result') or contains(@class,'winner') or contains(@class,'game-over') or contains(@class,'victory') or contains(@class,'end')]");
    public SelenideElement winnerText = $x("//*[contains(text(),'win') or contains(text(),'Win') or contains(text(),'lose') or contains(text(),'Victory') or contains(text(),'Game over')]");

    // US11 – Botão/link para criar torneio
    public SelenideElement createTournamentButton = $x("//a[contains(@href,'tournament') or contains(text(),'Tournament') or contains(text(),'tournament')]");

    // US12 – Botão de acesso à Loja (Shop)
    public SelenideElement shopLink = $x("//a[contains(@href, '/shop') or .//*[contains(text(), 'Shop')]]");

    // US14 – Link para histórico de partidas no perfil
    public SelenideElement historyLink = $x("//a[contains(@href,'history') or contains(text(),'History') or contains(text(),'Histórico')]");

    // US15 – Botão de partilha nas redes sociais
    public SelenideElement shareButton = $x("//*[contains(@class,'share') or contains(text(),'Share') or contains(text(),'Facebook') or contains(text(),'Twitter')]");

}
