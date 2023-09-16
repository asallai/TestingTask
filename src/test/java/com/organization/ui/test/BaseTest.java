package com.organization.ui.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.organization.ui.page.ComponentPage;

public class BaseTest {

    private static final String URL = "http://192.168.0.22:3000/";

    public WebDriver driver;
    public ComponentPage componentPage;

    @BeforeTest
    void setupAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(URL);

        componentPage = new ComponentPage(driver);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
