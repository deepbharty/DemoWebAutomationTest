package stepDefinitions;

import com.aventstack.extentreports.ExtentTest;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;
import utils.ElementFinder;

import java.time.Duration;

public class JiraLogin {

    private final WebDriver driver;
    private final ElementFinder finder;
    private final WebDriverWait wait;
    private final ExtentTest logger;

    public JiraLogin() {
        driver = DriverFactory.getDriver();
        finder = new ElementFinder(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger = Hooks.getTest();
    }

    @Given("User is on the Jira login page")
    public void userIsOnTheJiraLoginPage() {
        driver.get("https://adsso.airtel.com/adfs/ls/...your_url_here...");
        logger.info("Navigated to Jira login page.");
    }

    @When("User Select account to login")
    public void userSelectAccountToLogin() {
        WebElement selectAccount = finder.getLocator("Select_Login_Account");
        selectAccount.click();
        logger.info("Selected account to login.");
    }

    @When("User enters valid OLM ID")
    public void userEntersValidOLMID() {
        WebElement olmIdField = finder.getLocator("OLM_ID_Entry");
        olmIdField.sendKeys("A1XIZD7D");
        logger.info("Entered valid OLM ID.");
    }

    @And("User enters valid password")
    public void userEntersValidPassword() {
        WebElement passwordField = finder.getLocator("Password_Entry");
        passwordField.sendKeys("Hard#2580");
        logger.info("Entered valid password.");
    }

    @And("User clicks on the Login button")
    public void userClicksOnTheLoginButton() {
        WebElement signInButton = finder.getLocator("Btn_Sign_in");
        signInButton.click();
        logger.info("Clicked on the Login button.");
    }

    @Then("User should be prompted to enter OTP")
    public void userShouldBePromptedToEnterOTP() throws InterruptedException {
        logger.info("Waiting for OTP prompt...");
        Thread.sleep(15000); // Consider replacing with wait.until in future
    }

    @When("User enters valid OTP")
    public void userEntersValidOTP() throws InterruptedException {
        WebElement otpField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("verificationCodeInput")));
        String enteredOtp = otpField.getDomProperty("value");
        logger.info("Entered OTP value: " + enteredOtp);
        Thread.sleep(6000);
    }

    @And("User submits the OTP")
    public void userSubmitsTheOTP() {
        WebElement otpSubmitButton = finder.getLocator("OTPSubmitButton");
        otpSubmitButton.click();
        logger.info("Submitted the OTP.");
    }

    @Then("User should be redirected to the Jira dashboard")
    public void userShouldBeRedirectedToTheJiraDashboard() throws InterruptedException {
        WebElement dashboardText = finder.getLocator("Dashboard_Text");
        String dashboardTitle = dashboardText.getText();
        logger.info("Jira Dashboard Title is: " + dashboardTitle);
        Thread.sleep(2000);
    }
}
