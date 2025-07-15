package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() throws IOException {
        if (extent == null) {
            String reportPath = "reports/ExtentReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.loadXMLConfig("src/test/resources/configurations/extent-config.xml");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Project", "Website Automation Work");
            extent.setSystemInfo("Tester", "Deepak Bharty");

            // Load system info dynamically from config.properties
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/configurations/config.properties");
            props.load(fis);

            extent.setSystemInfo("Browser", props.getProperty("browser"));
            extent.setSystemInfo("Base URL", props.getProperty("baseURL"));
            extent.setSystemInfo("Username", props.getProperty("username"));
            extent.setSystemInfo("Environment", "QA"); // You can add this statically or from a prop

            // Optional (avoid passwords for security)
            // extent.setSystemInfo("Password", props.getProperty("password"));

            extent.setSystemInfo("Project", "Automation Framework");
            extent.setSystemInfo("Tester", "Deepak Bharty");
        }

        return extent;
    }
}
