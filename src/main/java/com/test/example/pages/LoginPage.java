package com.test.example.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.url;

public class LoginPage {

  private static final String PASSWORD_ERROR = "Ошибка в пароле";
  private static final String PASSWORD_ERROR_MESSAGE = "Вы ошиблись в пароле";
  private static final String LOGIN_ERROR = "Ошибка в логине";
  private static final String LOGIN_ERROR_MESSAGE = "Вы ошиблись в логине";
  private static final String LOGIN = "Логин";
  private static final String PASSWORD = "Пароль";
  private static final String SUBMIT_MESSAGE = "ВОЙТИ";
  private static final String HEADING = "FoodbandIS";
  private static final String AUTHORIZATION_ERROR = "Проблемы с авторизацией!";

  private SelenideElement loginField = $(By.name("login"));
  private SelenideElement passwordField = $(By.name("password"));
  private SelenideElement submitButton = $(By.cssSelector(".btn-block"));
  private SelenideElement panelHeading = $(By.cssSelector(".panel-heading > h1"));
  private SelenideElement errorAlert = $(By.xpath("//div[contains(@class,'growl-message')]"));

  /**
   * Login with given credentials.
   *
   * @param username
   * @param password
   */
  public void login(String username, String password) {
    loginField.val(username);
    passwordField.val(password);
    submitButton.click();
  }

  public void verifyUserIsLoggedIn() {
    sleep(2000);
    assert url().endsWith("/dep//");
  }

  /**
   * Verifies that password error message is displayed
   */
  public void verifyPasswordErrorMessageIsDisplayed() {
    $(By.cssSelector(".error-text.pass")).shouldHave(exactText(PASSWORD_ERROR_MESSAGE));
  }

  /**
   * Verifies that login error message is displayed
   */
  public void verifyLoginErrorMessageIsDisplayed() {
    $(By.cssSelector(".error-text.login")).shouldHave(exactText(LOGIN_ERROR_MESSAGE));
  }

  /**
   * Verifies that notification is shown to the user in case of wrong password
   */
  public void verifyPasswordAlertIsShown() {
    errorAlert.shouldHave(exactText(AUTHORIZATION_ERROR + " + " + PASSWORD_ERROR));
  }

  /**
   * Verifies that notification is shown to the user in case of wrong login
   */
  public void verifyLoginAlertIsShown() {
    errorAlert.shouldHave(exactText(AUTHORIZATION_ERROR + " + " + LOGIN_ERROR));
  }

  /**
   * Verifies text content on login page
   */
  public void verifyTextContent() {
    assert loginField.attr("placeholder").equals(LOGIN);
    assert passwordField.attr("placeholder").equals(PASSWORD);
    submitButton.shouldHave(exactText(SUBMIT_MESSAGE));
    panelHeading.shouldHave(exactText(HEADING));
  }

  /**
   * Checking the attribute that would guarantee empty field validation on login field
   */
  public void verifyEmptyLoginFieldAlertIsShown() {
    assert loginField.attr("validationMessage").equals("Please fill out this field.");
  }

  /**
   * Checking the attribute that would guarantee empty field validation on password field
   */
  public void verifyEmptyPasswordFieldAlertIsShown() {
    assert passwordField.attr("validationMessage").equals("Please fill out this field.");
  }

  /**
   * Checking if user is not logged in
   */
  public void verifyUserIsNotLoggedIn() {
    assert url().endsWith("/login");
  }
}