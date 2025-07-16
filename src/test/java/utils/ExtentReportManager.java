package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() throws IOException {
        if (extent == null) {
            // Define both local and Jenkins paths
            String localReportPath = "reports/ExtentReport.html";
            String jenkinsReportPath = "target/ExtentReport/ExtentReport.html";

            // Create directories if they don’t exist
            new File("reports").mkdirs();
            new File("target/ExtentReport").mkdirs();

            // Initialize both reporters
            ExtentSparkReporter reporterLocal = new ExtentSparkReporter(localReportPath);
            ExtentSparkReporter reporterJenkins = new ExtentSparkReporter(jenkinsReportPath);

            // ✅ Apply configuration manually instead of XML
            configureReporter(reporterLocal);
            configureReporter(reporterJenkins);

            // Initialize ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(reporterLocal, reporterJenkins);

            // System Info
            extent.setSystemInfo("Project", "Website Automation Work");
            extent.setSystemInfo("Tester", "Deepak Bharty");

            // Load additional system info from config.properties
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/configurations/config.properties");
            props.load(fis);

            extent.setSystemInfo("Browser", props.getProperty("browser"));
            extent.setSystemInfo("Base URL", props.getProperty("baseURL"));
            extent.setSystemInfo("Username", props.getProperty("username"));
            extent.setSystemInfo("Environment", "QA");
        }

        return extent;
    }

    private static void configureReporter(ExtentSparkReporter reporter) {
        reporter.config().setEncoding("utf-8");
        reporter.config().setTheme(Theme.DARK); // Or Theme.STANDARD
        reporter.config().setDocumentTitle("BDD Automation Test Report");
        reporter.config().setReportName("Automation Test Report");
        reporter.config().setTimelineEnabled(true); // ✅ Enables timeline view
        reporter.config().enableOfflineMode(true);  // ✅ Embeds CSS/JS for Jenkins

    }
}
