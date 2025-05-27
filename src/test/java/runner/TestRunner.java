package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"}, // ✅ Proper array of glue paths
        plugin = {
                "pretty",
                "html:target/cucumber-reports/htmlReport.html",
                "json:target/cucumber-reports/report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // ✅ Extent Report Adapter
        },
        monochrome = true,
        dryRun = false,
        tags = "@Login"
)
public class TestRunner extends AbstractTestNGCucumberTests {

        @BeforeClass
        @Parameters({"browser", "environment", "cucumber.filter.tags"})
        public void setUp(@Optional("chrome") String browser,
                          @Optional("qa") String environment,
                          @Optional("@Login") String tags) {
                // Setting as system properties for global access
                System.setProperty("browser", browser);
                System.setProperty("environment", environment);
                System.setProperty("cucumber.filter.tags", tags);

                System.out.println("Running tests on Browser: " + browser + ", Env: " + environment + ", Tags: " + tags);
        }
}
