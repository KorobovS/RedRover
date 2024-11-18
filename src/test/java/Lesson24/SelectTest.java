package Lesson24;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.stream.Collectors;

public class SelectTest extends BaseTest {

    void goToSelect() {
        getDriver().findElement(By.linkText("selectPage.html")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("selectWithoutMultiple")));
    }

    void goToWebForm() {
        getDriver().findElement(By.linkText("web-form.html")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("main")));
    }

    @Test
    void testSimpleDropDown() {
        goToSelect();

        final WebElement selectWithoutMultiple = getDriver().findElement(By.id("selectWithoutMultiple"));
        Select simpleDropDown = new Select(selectWithoutMultiple);
        simpleDropDown.selectByValue("two");
        String newValue = selectWithoutMultiple.getAttribute("value");
        System.out.println(newValue);

        Assert.assertEquals(newValue, "two");
    }

    @Test
    void testMultipleSelect() {
        goToSelect();

        WebElement selectElement = getDriver().findElement(By.id("selectWithMultipleEqualsMultiple"));
        Select multiSelect = new Select(selectElement);

        multiSelect.selectByIndex(2);
        multiSelect.deselectByIndex(0);
        multiSelect.selectByVisibleText("Cheddar");

        System.out.println(selectElement.getAttribute("value"));
        System.out.println(multiSelect.getAllSelectedOptions().stream().map(WebElement::getText).collect(Collectors.toList()));
    }

    @Test
    void testLongList() {
        goToSelect();

        WebElement selectElement = getDriver().findElement(By.id("selectWithMultipleLongList"));
        Select select = new Select(selectElement);

        select.selectByVisibleText("five");
        select.selectByVisibleText("six");

        System.out.println(selectElement.getAttribute("value"));
        System.out.println(select.getAllSelectedOptions().stream().map(WebElement::getText).collect(Collectors.toList()));
    }
}
