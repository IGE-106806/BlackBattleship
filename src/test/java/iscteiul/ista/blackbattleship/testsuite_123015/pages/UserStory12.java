package iscteiul.ista.blackbattleship.testsuite_123015.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Page Object Class para US12.
 * Como jogador, quero aceder à loja para consultar
 * os itens e cosméticos disponíveis.
 */
public class UserStory12 {

    /** Imagem dos Coins clicável na loja. */
    public SelenideElement coinsImage = $("img[alt='Coins']");

    /**
     * Clica na secção de Coins via JavaScript para evitar bloqueios.
     */
    public void abrirCoins() {
        executeJavaScript("arguments[0].click();", coinsImage);
    }
}