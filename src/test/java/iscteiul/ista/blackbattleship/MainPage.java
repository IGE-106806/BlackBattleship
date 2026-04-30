package iscteiul.ista.blackbattleship;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://www.jetbrains.com/
public class MainPage {
  // O menu "Developer Tools" passou a chamar-se "Products" no site atual
  public SelenideElement seeDeveloperToolsButton = $x("//div[@data-test='main-menu-item' and @data-test-marker='Products']");
  // Link "Find your tool" dentro do submenu de Products
  public SelenideElement findYourToolsButton = $("[data-test='suggestion-link']");
  // Item "Products" no menu principal
  public SelenideElement toolsMenu = $x("//div[@data-test='main-menu-item' and @data-test-marker='Products']");
  // Botão de pesquisa no header
  public SelenideElement searchButton = $("[data-test='site-header-search-action']");
}
