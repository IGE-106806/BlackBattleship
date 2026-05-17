package iscteiul.ista.blackbattleship.testsuite_94334.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

// page_url = https://papergames.io/en/battleship
public class US8_VerRegrasPage {

    // US8 – Secção de regras / "How to play" na página principal
    public SelenideElement howToPlaySection = $x(
        "//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'how to play') " +
        "or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'rules')]"
    );

}
