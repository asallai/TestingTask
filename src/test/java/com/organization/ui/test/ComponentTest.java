package com.organization.ui.test;

import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ComponentTest extends BaseTest {

    private static final String BASIC_PRICE = "5";
    private static final String LABEL_TO_REMOVE ="Internal surcharge";
    private static final String LABEL_TO_EDIT = "Storage surcharge";
    private static final String TOO_SHORT_LABEL = "T";
    private static final String INVALID_PRICE = "-2.15";
    private static final String VALID_PRICE = "1.79";

    public Map<String, String> components = new HashMap<>();

    @Test
    public void testPriceComponents() {

         // 1. Change Base Price value to 5
        componentPage.changeBasicPriceTo(BASIC_PRICE);
        assertEquals(componentPage.getTotalPrice(), String.format("%s.00", BASIC_PRICE), "Total sum should be the base price!");

        // 2. Add all price components from Testdata
        loadComponentsTestData();

        for (var entry : components.entrySet()) {
            componentPage.addComponent(entry.getKey(), entry.getValue());
            assertEquals(componentPage.getPriceForLastAddedComponent(), getFormattedPrice(entry.getValue()), "Component price should be correctly formatted!");
        }

        // 3. Remove price component: Internal surcharge
        componentPage.removeComponent(LABEL_TO_REMOVE);
        assertFalse(componentPage.isLabelDisplayed(LABEL_TO_REMOVE), "Removed component should NOT be displayed!");
        assertEquals(componentPage.sumComponentPrices(), componentPage.getTotalPrice(), "Total price should be the sum of component prices on the page!");
        assertEquals(componentPage.getTotalPrice(), sumPricesWithoutRemovedItem(), "Total price should be the sum of component prices on the page!");

        // 4. Edit price component: Storage surcharge
        componentPage.editComponent(LABEL_TO_EDIT, TOO_SHORT_LABEL);
        assertTrue(componentPage.isLabelDisplayed(LABEL_TO_EDIT), "In case of too short label the last valid label should be displayed!");


        // TODO
        // 5. Edit price component: Scrap surcharge
        /*
        a. Hover row
        b. Click on ‘Pencil’ icon
        c. Enter new value: -2.15
        d. Click on ‘Check’ icon
        e. Verify Expected Results D.

        D. Value input validation
        a. Values cannot be negative
        b. If input is invalid, restore last valid state
         */

        // TODO
        // 6. Edit price component: Alloy surcharge
        /*
        a. Hover row
        b. Click on ‘Pencil’ icon
        c. Enter new value: 1.79
        d. Click on ‘Check’ icon
        e. Verify Expected Results A.
        A. Displayed sum shows correct sum
         */
    }

    private String getFormattedPrice(String price) {
        DecimalFormat df;
        int numberOfDecimal = getNumberOfDecimal(price);

        if(numberOfDecimal == 0 || numberOfDecimal == 1) {
            df = new DecimalFormat("0.0");
        }else{
            df = new DecimalFormat("0.00");
        }

        Double dd = Double.parseDouble(price);

        String formattedPrice = df.format(dd);
        formattedPrice = formattedPrice.replace( "," , "." );

        return formattedPrice;
    }

    private int getNumberOfDecimal(String price) {
        if(price.contains(".")) {
            return price.replaceAll(".*\\.(?=\\d?)", "").length();
        }else{
            return 0;
        }
    }

    private String sumPricesWithoutRemovedItem() {
        double sum = Double.parseDouble(BASIC_PRICE);

        for (var entry : components.entrySet()) {
            String price = entry.getValue();
            sum = sum + Double.parseDouble(price);
        }
        sum = sum - Double.parseDouble(components.get(LABEL_TO_REMOVE));

        return Double.toString(sum);
    }

    private void loadComponentsTestData() {
        components.put("Alloy surcharge", "2.15");
        components.put("Scrap surcharge", "3.14");
        components.put("Internal surcharge", "0.7658");
        components.put("External surcharge", "1");
        components.put("Storage surcharge", "0.3");
    }
}