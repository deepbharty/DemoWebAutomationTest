package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class ElementFinder {

    private WebDriver driver;

    public ElementFinder(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getLocator(String identifierName) {

        Map<String, String> locator = CSVReader.getLocator(identifierName);

        // Debug logs (uncomment if needed)
        // System.out.println("Identifier Passed: " + identifierName);
        // System.out.println("Locator Map Returned: " + locator);

        if (locator.isEmpty()) {
            throw new IllegalArgumentException("No locator found for identifier: " + identifierName);
        }

        String type = locator.get("locatorType").toLowerCase();
        String value = locator.get("locatorValue");

        // Debug logs (uncomment if needed)
        // System.out.println("Locator Type: " + type);
        // System.out.println("Locator Value: " + value);

        switch (type.toUpperCase()) {
            case "ID" -> {
                return driver.findElement(By.id(value));
            }
            case "XPATH" -> {
                return driver.findElement(By.xpath(value));
            }
            case "CSS" -> {
                return driver.findElement(By.cssSelector(value));
            }
            case "NAME" -> {
                return driver.findElement(By.name(value));
            }
            case "LINKTEXT" -> {
                return driver.findElement(By.linkText(value));
            }
            case "PARTIALLINKTEXT" -> {
                return driver.findElement(By.partialLinkText(value));
            }
            case "CLASSNAME" -> {
                return driver.findElement(By.className(value));
            }
            case "TAGNAME" -> {
                return driver.findElement(By.tagName(value));
            }
            default -> throw new IllegalArgumentException("Unsupported locator type: " + type);
        }
    }
}
