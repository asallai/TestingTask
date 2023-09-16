package com.organization.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ComponentPage extends BasePage {

    private static final By totalPriceValue = By.cssSelector("span.font-bold");

    private static final By basePriceRow = By.id("BasePrice");
    private static final By basePricePencilIcon = By.id("base-edit-icon");
    private static final By basePriceValueInput = By.id("base-value-input");
    private static final By basePriceCheckIcon = By.cssSelector(".fas.fa-check");

    private static final By componentRows = By.cssSelector("ul .border-yellow-400");
    private static final By componentPencilIcon = By.cssSelector(".fas.fa-pencil-alt");
    private static final By componentLabelInputNew = By.id("ghost-label-input");
    private static final By componentLabelInputExisting = By.cssSelector(".border-yellow-200");
    private static final By componentPriceInput = By.id("ghost-value-input");
    private static final By componentCheckIconNew = By.id("ghost-check-icon");
    private static final By componentCheckIconExisting = By.cssSelector(".fas.fa-check");

    private static final By componentTrashIcon = By.cssSelector(".fas.fa-trash-alt");
    private static final By componentLastAddedRow = By.cssSelector("ul .border-yellow-400:nth-last-child(2)");

    private static final String COMPONENT_LABEL = ".flex-grow.flex.flex-col>span";
    private static final String COMPONENT_PRICES = "ul .border-yellow-400 .text-right";
    private static final String COMPONENT_PRICE = ".text-right";

    public ComponentPage(WebDriver driver) {
        super(driver);
    }

    public void changeBasicPriceTo(String price) {
        hover(basePriceRow);
        click(basePricePencilIcon);
        enter(basePriceValueInput, price);
        click(basePriceCheckIcon);
    }

    public void addComponent(String label, String price) {
        enter(componentLabelInputNew, label);
        enter(componentPriceInput, price);
        click(componentCheckIconNew);
    }

    public void editComponent(String originalComponent, String newComponent) {
        WebElement row = getComponentRow(originalComponent);
        hover(row);
        WebElement pencilIcon = row.findElement(componentPencilIcon);
        pencilIcon.click();
        WebElement labelInputField = row.findElement(componentLabelInputExisting);
        enter(labelInputField, newComponent);
        WebElement checkIcon = row.findElement(componentCheckIconExisting);
        checkIcon.click();
    }

    public void removeComponent(String componentLabel) {
        WebElement row = getComponentRow(componentLabel);
        hover(row);
        WebElement trashIcon = row.findElement(componentTrashIcon);
        trashIcon.click();
    }

    public String sumComponentPrices() {
        double sum = 0;

        List<WebElement> priceValues = driver.findElements(By.cssSelector(COMPONENT_PRICES));

        for(WebElement priceValue : priceValues){
            String priceStr = priceValue.getText();
            sum = sum + Double.parseDouble(priceStr);
        }
        return Double.toString(sum);
    }

    public String getPriceForLastAddedComponent() {
        return driver.findElement(componentLastAddedRow)
                .findElement(By.cssSelector(COMPONENT_PRICE))
                .getText();
    }

    public String getTotalPrice() {
        return getText(totalPriceValue);
    }

    public boolean isLabelDisplayed(String labelToSearch) {
        return getComponentRow(labelToSearch) != null;
    }

    private WebElement getComponentRow(String labelToSearch) {
        List<WebElement> rows = driver.findElements(componentRows);

        for(WebElement row : rows) {
            String labelText = row.findElement(By.cssSelector(COMPONENT_LABEL))
                    .getText();

            if (labelText.equals(labelToSearch))
                return row;
        }
        return null;
    }
}