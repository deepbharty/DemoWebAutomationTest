package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(
        features = "src/test/resources/features", // Feature file path
        glue = {"stepDefinitions", "hooks"},      // Step defs and hooks
        plugin = {
                "pretty", // Console output
                "html:target/cucumber-reports/htmlReport.html", // HTML report
                "json:target/cucumber-reports/report.json",     // JSON report
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", // Extent
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"                      // Allure
        },
        monochrome = true,
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

        @BeforeClass
        @Parameters({"browser", "environment", "cucumberTags"})  // ✅ updated parameter name
        public void setUp(@Optional("chrome") String browser,
                          @Optional("qa") String environment,
                          @Optional("@Login") String tags) {

                // ✅ Set expected system properties for framework use
                System.setProperty("browser", browser);
                System.setProperty("environment", environment);
                System.setProperty("cucumber.filter.tags", tags); // actual property required by Cucumber

                System.out.println("Running tests on Browser: " + browser +
                        ", Env: " + environment +
                        ", Tags: " + tags);
        }
}
