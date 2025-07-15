package stepDefinitions;

import com.aventstack.extentreports.ExtentTest;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ElementFinder;
import org.testng.Assert;

import java.time.Duration;

public class OrangeHRMLoginSteps {

    private final WebDriver driver;
    private final ElementFinder finder;
    private final WebDriverWait wait;
    private final ExtentTest logger;


    public OrangeHRMLoginSteps() {
        this.driver = DriverFactory.getDriver();
        this.finder = new ElementFinder(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.logger = Hooks.getTest();
    }

    @Given("User is on the OrangeHRM login page")
    public void userIsOnLoginPage() {
        logger.info("Navigated to OrangeHRM login page.");
    }

    @When("User enters username")
    public void enterUsername() {
        WebElement usernameField = finder.getLocator("OHRM_Username_Input");
        usernameField.clear();
        usernameField.sendKeys(ConfigReader.getProperty("username"));
        logger.info("Entered username: " + ConfigReader.getProperty("username"));


    }

    @And("User enters password")
    public void enterPassword() {
        WebElement passwordField = finder.getLocator("OHRM_Password_Input");
        passwordField.clear();
        passwordField.sendKeys(ConfigReader.getProperty("password"));
        logger.info("Entered password: " + ConfigReader.getProperty("password"));
    }

    @And("User clicks on the Login button")
    public void clickLoginButton() {
        WebElement loginButton = finder.getLocator("OHRM_Login_Button");
        loginButton.click();
        logger.info("Clicked login button.");
    }

    @Then("User should be redirected to the Dashboard page")
    public void verifyDashboardRedirection() {
        WebElement dashboardHeader = finder.getLocator("OHRM_Dashboard_Header");
        wait.until(ExpectedConditions.visibilityOf(dashboardHeader));
        Assert.assertTrue(dashboardHeader.isDisplayed(), "Dashboard header is not visible.");
        logger.pass("User successfully redirected to the Dashboard page.");
    }



    //invalidUsername=Deepbharty
            //invalidPassword=admin1234

    @And("Page title should be {string}")
    public void verifyPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title mismatch");
        logger.pass("Page title is: " + actualTitle);
    }

    @Then("An error message {string} should be displayed")
    public void verifyErrorMessage(String expectedMessage) {
        WebElement errorMsg = finder.getLocator("OHRM_Login_Error_Message");
        wait.until(ExpectedConditions.visibilityOf(errorMsg));
        String actualMessage = errorMsg.getText();
        Assert.assertEquals(actualMessage, expectedMessage, "Error message mismatch");
        logger.pass("Displayed error message: " + actualMessage);
    }

    @When("User enters wrong username")
    public void userEntersWrongUsername() {

        WebElement usernameField = finder.getLocator("OHRM_Username_Input");
        usernameField.clear();
        usernameField.sendKeys(ConfigReader.getProperty("invalidUsername"));
        logger.info("Entered username: " + ConfigReader.getProperty("invalidUsername"));
    }

    @And("User enters wrong password")
    public void userEntersWrongPassword() {

        WebElement passwordField = finder.getLocator("OHRM_Password_Input");
        passwordField.clear();
        passwordField.sendKeys(ConfigReader.getProperty("invalidPassword"));
        logger.info("Entered password: " + ConfigReader.getProperty("invalidPassword"));

    }
}
