package com.cura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By makeAppointment = By.id("btn-make-appointment");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickMakeAppointment() {
        click(makeAppointment);
        return new LoginPage(driver);
    }
}
