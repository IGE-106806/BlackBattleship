package iscteiul.ista.blackbattleship.testsuite_123015.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object Class para US15.
 * Como jogador, quero partilhar o link/QR code de convite
 * para desafiar os meus amigos.
 */
public class UserStory15 {

    /** Botão "Play with a friend" na página principal. */
    public SelenideElement playWithFriendButton = $(".w-100:nth-child(1) > .btn-lg .flex-grow-1");

    /** Link de convite clicável para copiar. */
    public SelenideElement inviteLink = $(".copy-text > span");

    /** Clica em "Play with a friend". */
    public void clicarJogarComAmigo() {
        playWithFriendButton.click();
    }

    /** Clica no link de convite para copiar. */
    public void copiarLinkConvite() {
        inviteLink.click();
    }
}