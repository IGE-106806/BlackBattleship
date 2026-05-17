package iscteiul.ista.blackbattleship.testsuite_123015.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Page Object Class para US14.
 * Como jogador, quero ver o histórico das minhas partidas
 * anteriores para acompanhar a minha evolução.
 */
public class UserStory14 {

    /** Link para o histórico de partidas. Só visível com sessão ativa. */
    public SelenideElement historyLink = $x("//a[contains(@href,'/en/match-history')]");

    /** Navega para o histórico via JavaScript para evitar bloqueios. */
    public void navegarParaHistorico() {
        executeJavaScript("arguments[0].click();", historyLink);
    }
}