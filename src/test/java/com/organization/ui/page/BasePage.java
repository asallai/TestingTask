package com.organization.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class BasePage {

    public WebDriver driver;
    public Wait<WebDriver> wait;

    private static final int TIMEOUT = 20;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void click(By locator) {
        WebElement element = waitUntilClickable(locator);
        element.click();
    }

    public void enter(By locator, String text) {
        WebElement element = waitUntilClickable(locator);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public void enter(WebElement element, String text) {
        element = waitUntilClickable(element);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public void hover(By locator) {
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.build()
               .perform();
    }

    public void hover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.build()
               .perform();
    }

    public String getText(By locator) {
        WebElement element = driver.findElement(locator);
        return element.getText();
    }

    private WebElement waitUntilClickable(By locator) {
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);

        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitUntilClickable(WebElement element) {
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);

        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
