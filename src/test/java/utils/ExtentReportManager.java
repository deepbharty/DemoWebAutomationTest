package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final Logger LOGGER = Logger.getLogger(ExtentReportManager.class.getName());

    public static ExtentReports getInstance() {
        if (extent == null) {
            try {
                // Load config.properties
                Properties props = new Properties();
                props.load(new FileInputStream("src/test/resources/configurations/config.properties"));

                // Define report paths (can be modified via config)
                String localPath = props.getProperty("extent.report.local.path", "reports/ExtentReport.html");
                String jenkinsPath = props.getProperty("extent.report.path", "target/ExtentReport/ExtentReport.html");

                // Ensure directories exist
                new File(localPath).getParentFile().mkdirs();
                new File(jenkinsPath).getParentFile().mkdirs();

                // Create Spark Reporters
                ExtentSparkReporter localReporter = new ExtentSparkReporter(localPath);
                ExtentSparkReporter jenkinsReporter = new ExtentSparkReporter(jenkinsPath);

                // Load shared config XML
                String configXML = "src/test/resources/configurations/extent-config.xml";
                localReporter.loadXMLConfig(configXML);
                jenkinsReporter.loadXMLConfig(configXML);

                extent = new ExtentReports();
                extent.attachReporter(localReporter, jenkinsReporter);

                // Set common system info
                extent.setSystemInfo("Project", props.getProperty("project.name", "Website Automation Work"));
                extent.setSystemInfo("Tester", props.getProperty("tester.name", "Deepak Bharty"));
                extent.setSystemInfo("Browser", props.getProperty("browser", "chrome"));
                extent.setSystemInfo("Base URL", props.getProperty("baseURL", "http://localhost"));
                extent.setSystemInfo("Username", props.getProperty("username", "testuser"));
                extent.setSystemInfo("Environment", props.getProperty("environment", "QA"));
                extent.setSystemInfo("Generated On", new Date().toString());

                // Jenkins build URL if available
                String jenkinsURL = System.getenv("BUILD_URL");
                if (jenkinsURL != null) {
                    extent.setSystemInfo("Jenkins Job", jenkinsURL);
                }

            } catch (IOException e) {
                LOGGER.warning("Failed to initialize ExtentReports: " + e.getMessage());
            }
        }
        return extent;
    }
}
