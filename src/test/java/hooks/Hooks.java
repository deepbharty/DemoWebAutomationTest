package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import utils.ExtentReportManager;

import java.io.IOException;

public class Hooks {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    @Before(order = 0)
    public void setUpDriver() {
        DriverFactory.initializeDriver();
    }

    @Before(order = 1)
    public void setUpExtent(Scenario scenario) throws IOException {
        if (extent == null) {
            extent = ExtentReportManager.getInstance();
        }
        ExtentTest test = extent.createTest(scenario.getName());
        testThreadLocal.set(test);
    }

    @After(order = 1)
    public void tearDownScenario(Scenario scenario) {
        ExtentTest test = testThreadLocal.get();
        WebDriver driver = DriverFactory.getDriver();

        if (scenario.isFailed()) {
            test.log(Status.FAIL, "Scenario Failed: " + scenario.getName());

            if (driver != null) {
                // Take screenshot and add to report
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                test.fail("Screenshot of failure",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(
                                java.util.Base64.getEncoder().encodeToString(screenshot)).build());
            }
        } else {
            test.log(Status.PASS, "Scenario Passed: " + scenario.getName());
        }

        // Flush report after each scenario
        extent.flush();
    }

    @After(order = 0)
    public void tearDownDriver() {
        DriverFactory.quitDriver();
        testThreadLocal.remove();
    }

    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }
}
