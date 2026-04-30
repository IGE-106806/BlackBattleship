package iscteiul.ista.blackbattleship;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://www.jetbrains.com/
public class MainPage {
  // Botão de pesquisa no cabeçalho
  public SelenideElement searchButton = $("[data-test='site-header-search-action']");

  // Menu "Products" na barra de navegação principal
  public SelenideElement toolsMenu = $x("//div[@data-test='main-menu-item' and @data-test-marker='Products']");

  // Mesmo botão de menu "Products" usado para abrir o submenu e navegar para a página de ferramentas
  public SelenideElement seeDeveloperToolsButton = $x("//div[@data-test='main-menu-item' and @data-test-marker='Products']");

  // Link "Find your tool" que aparece no submenu de Products
  public SelenideElement findYourToolsButton = $("[data-test='suggestion-link']");
}
