package iscteiul.ista.blackbattleship.testsuite_94334.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

// page_url = https://papergames.io/en/battleship
public class US1_PaginaPrincipalPage {

    // US1 – Título principal da página
    public SelenideElement gameTitle = $x("//h1[contains(text(),'Battleship Online')]");

}
