package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() throws IOException {
        if (extent == null) {
            String reportPath = "test-output/ExtentReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.loadXMLConfig("src/test/resources/configurations/extent-config.xml");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Project", "Automation Framework");
            extent.setSystemInfo("Tester", "Deepak Bharty");
        }
        return extent;
    }
}
