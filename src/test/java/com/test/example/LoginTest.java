package com.test.example;

import com.test.example.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static com.codeborne.selenide.Selenide.open;
import static com.test.example.Configuration.properties;

public class LoginTest {

  String baseUrl = properties.getProperty("base.url");
  String validUsername = properties.getProperty("valid.login");
  String validPassword = properties.getProperty("valid.password");

  /**
   * Verifies that user is able to login with valid credentials
   */
  @Test
  void loginWithValidCredentials() {
    LoginPage page = open(baseUrl, LoginPage.class);
    page.login(validUsername, validPassword);
    page.verifyUserIsLoggedIn();
  }

  /**
   * Verifies that user is not able to login with invalid password
   */
  @Test
  void loginWithInvalidPassword() {
    LoginPage page = open(baseUrl, LoginPage.class);
    page.login(validUsername, UUID.randomUUID().toString());
    page.verifyPasswordErrorMessageIsDisplayed();
    page.verifyUserIsNotLoggedIn();
  }

  /**
   * Verifies that user is not able to login with invalid credentials
   */
  @Test
  void loginWithInvalidCredentials() {
    LoginPage page = open(baseUrl, LoginPage.class);
    page.login(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    page.verifyLoginErrorMessageIsDisplayed();
    page.verifyUserIsNotLoggedIn();
  }

  /**
   * Verifies that notification is shown to the user with incorrent credentials
   */
  @Test
  void checkErrorAlerts() {
    LoginPage page = open(baseUrl, LoginPage.class);
    page.login(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    page.verifyLoginAlertIsShown();
    page.verifyUserIsNotLoggedIn();

    page.login(validUsername, UUID.randomUUID().toString());
    page.verifyPasswordAlertIsShown();
    page.verifyUserIsNotLoggedIn();
  }

  /**
   * Verifies that empty field alert is shown to the user with empty credentials
   */
  @Test
  void loginWithEmptyFields() {
    LoginPage page = open(baseUrl, LoginPage.class);
    page.login("", UUID.randomUUID().toString());
    page.verifyUserIsNotLoggedIn();
    page.verifyEmptyLoginFieldAlertIsShown();

    page.login(UUID.randomUUID().toString(), "");
    page.verifyEmptyPasswordFieldAlertIsShown();
    page.verifyUserIsNotLoggedIn();
  }

  /**
   * Verifies that user is not able to access site pages by direct url
   *
   * @param partialUrl
   */
  @ParameterizedTest
  @ValueSource(strings = {
      "/dep//",
      "/operator/all",
      "/operator/edit/933871/products"
  })
  void accessByDirectUrl(String partialUrl) {
    LoginPage page = open(baseUrl + partialUrl, LoginPage.class);
    page.verifyUserIsNotLoggedIn();
  }

  /**
   * Verifies text content on login page
   */
  @Test
  void textContent() {
    LoginPage page = open(baseUrl, LoginPage.class);
    page.verifyTextContent();
  }
}
