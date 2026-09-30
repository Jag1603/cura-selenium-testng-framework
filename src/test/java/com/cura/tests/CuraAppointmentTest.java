package com.cura.tests;

import com.cura.base.BaseTest;
import com.cura.listeners.ExtentReportListener;
import com.cura.listeners.TestListener;
import com.cura.pages.AppointmentPage;
import com.cura.pages.HomePage;
import com.cura.pages.LoginPage;
import com.cura.retry.RetryAnalyzer;
import com.cura.utils.ConfigReader;
import com.cura.utils.CsvDataProvider;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners({TestListener.class, ExtentReportListener.class})
public class CuraAppointmentTest extends BaseTest {

    @Test(
        description = "Parameterized login and appointment booking flow",
        retryAnalyzer = RetryAnalyzer.class
    )
    @Parameters({"browser", "headless"})
    public void parameterizedAppointmentTest() {
        AppointmentPage appointment = new HomePage(driver)
                .clickMakeAppointment()
                .login(ConfigReader.get("username"), ConfigReader.get("password"));

        appointment
                .selectFacility("Tokyo CURA Healthcare Center")
                .setReadmission("No")
                .selectProgram("Medicare")
                .setVisitDate("30/09/2026")
                .setComment("Parameterized TestNG appointment")
                .bookAppointment();

        Assert.assertTrue(appointment.isAppointmentConfirmed(),
                "Appointment confirmation was not displayed");
    }

    @Test(
        description = "CSV data-driven appointment booking",
        dataProvider = "appointmentData",
        dataProviderClass = CsvDataProvider.class,
        retryAnalyzer = RetryAnalyzer.class
    )
    public void dataDrivenAppointmentTest(
            String facility,
            String readmission,
            String program,
            String visitDate,
            String comment) {

        AppointmentPage appointment = new HomePage(driver)
                .clickMakeAppointment()
                .login(ConfigReader.get("username"), ConfigReader.get("password"));

        appointment
                .selectFacility(facility)
                .setReadmission(readmission)
                .selectProgram(program)
                .setVisitDate(visitDate)
                .setComment(comment)
                .bookAppointment();

        Assert.assertTrue(appointment.isAppointmentConfirmed(),
                "Appointment confirmation was not displayed");
    }
}
