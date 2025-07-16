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
import java.util.Base64;

public class Hooks {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    @Before(order = 0)
    public void setUpDriver() {
        DriverFactory.initializeDriver();
    }

    @Before(order = 1)
    public void setUpExtent(Scenario scenario) throws IOException {
        if (extent == null) {
            extent = ExtentReportManager.getInstance();
        }

        // ✅ Get Feature Name from URI path
        String uri = scenario.getUri().toString();  // e.g., file:/.../features/Login.feature
        String featureName = uri.substring(uri.lastIndexOf("/") + 1).replace(".feature", "");

        // ✅ Get scenario name
        String scenarioName = scenario.getName();

        // ✅ Get tags
        String tags = String.join(", ", scenario.getSourceTagNames());

        // ✅ Create Extent Test with feature and scenario name
        ExtentTest test = extent.createTest(featureName + " - " + scenarioName)
                .assignCategory(featureName) // Feature as a category
                .assignCategory(tags);       // Tags as categories

        testThreadLocal.set(test);
    }


    @After(order = 1)
    public void tearDownScenario(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        ExtentTest test = testThreadLocal.get();

        if (scenario.isFailed()) {
            test.log(Status.FAIL, "❌ Scenario Failed: " + scenario.getName());

            if (driver != null) {
                // Capture screenshot as base64 and attach
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);

                test.fail("📸 Screenshot of failure:",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            }
        } else {
            test.log(Status.PASS, "✅ Scenario Passed: " + scenario.getName());
        }

        // Flush the report (safe to do per scenario in Cucumber)
        extent.flush();
    }

    @After(order = 0)
    public void tearDownDriver() {
        DriverFactory.quitDriver();
        testThreadLocal.remove(); // Clean up thread-local memory
    }

    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }
}
