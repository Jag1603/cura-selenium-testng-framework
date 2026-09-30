package com.cura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AppointmentPage extends BasePage {
    private final By facility = By.id("combo_facility");
    private final By readmissionYes = By.id("chk_hospotal_readmission");
    private final By readmissionNo = By.cssSelector("input[name='hospital_readmission'][value='No']");
    private final By programMedicare = By.id("radio_program_medicare");
    private final By programMedicaid = By.id("radio_program_medicaid");
    private final By programNone = By.id("radio_program_none");
    private final By visitDate = By.id("txt_visit_date");
    private final By comment = By.id("txt_comment");
    private final By book = By.id("btn-book-appointment");
    private final By confirmation = By.cssSelector("h2");

    public AppointmentPage(WebDriver driver) {
        super(driver);
    }

    public AppointmentPage selectFacility(String value) {
        selectByVisibleText(facility, value);
        return this;
    }

    public AppointmentPage setReadmission(String value) {
        if ("Yes".equalsIgnoreCase(value)) click(readmissionYes);
       
        return this;
    }

    public AppointmentPage selectProgram(String value) {
        switch (value.toLowerCase()) {
            case "medicare" -> click(programMedicare);
            case "medicaid" -> click(programMedicaid);
            case "none" -> click(programNone);
            default -> throw new IllegalArgumentException("Unknown program: " + value);
        }
        return this;
    }

    public AppointmentPage setVisitDate(String date) {
        type(visitDate, date);
        return this;
    }

    public AppointmentPage setComment(String text) {
        type(comment, text);
        return this;
    }

    public AppointmentPage bookAppointment() {
        click(book);
        return this;
    }

    public boolean isAppointmentConfirmed() {
        return isDisplayed(confirmation);
    }
}
