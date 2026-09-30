package com.cura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By username = By.id("txt-username");
    private final By password = By.id("txt-password");
    private final By login = By.id("btn-login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public AppointmentPage login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(login);
        return new AppointmentPage(driver);
    }
}
