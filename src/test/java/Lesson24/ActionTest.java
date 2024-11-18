package Lesson24;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ActionTest extends BaseTest {

    private Actions actions;

    private Actions getActions() {
        if (actions == null) {
            actions = new Actions(getDriver());
        }
        return actions;
    }

    void goToWebForm() {
        getDriver().findElement(By.linkText("web-form.html")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("main")));
    }

    void goToDragAndDrop() {
        getDriver().findElement(By.linkText("dragAndDropTest.html")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    }

    @Test
    void testSliderArrowKeys() {
        goToWebForm();

        WebElement slider = getDriver().findElement(By.name("my-range"));
        slider.sendKeys(Keys.LEFT, Keys.LEFT);
        slider.sendKeys(Keys.LEFT, Keys.LEFT);

        System.out.println(slider.getAttribute("value"));
        Assert.assertEquals(slider.getAttribute("value"), "1");
    }

    @Test
    void testSliderAction() {
        goToWebForm();

        WebElement slider = getDriver().findElement(By.name("my-range"));
        getActions().clickAndHold(slider).moveByOffset(-200, 0).release().perform();

        System.out.println(slider.getAttribute("value"));
        Assert.assertEquals(slider.getAttribute("value"), "0");
    }

    @Test
    void testSliderClick() {
        goToWebForm();

        WebElement slider = getDriver().findElement(By.name("my-range"));
        final Dimension size = slider.getSize();
        int sliderWidth = size.getWidth();

        getActions().moveToElement(slider).moveByOffset(sliderWidth * 2 / 5, 0).click().perform();

        System.out.println(slider.getAttribute("value"));
    }

    @Test
    void testKeyActions() {
        goToWebForm();

        WebElement textArea = getDriver().findElement(By.name("my-textarea"));
        getActions().click(textArea).keyDown(Keys.SHIFT).sendKeys("abCDe").keyUp(Keys.SHIFT).perform();

        System.out.println(textArea.getAttribute("value"));
        Assert.assertEquals(textArea.getAttribute("value"), "ABCDE");
    }

    @Test
    void testDragAndDrop() {
        goToDragAndDrop();

        WebElement test1 = getDriver().findElement(By.id("test1"));
        getActions().clickAndHold(test1)
                .moveByOffset(100, 50)
                .pause(1000)
                .moveByOffset(100, 50)
                .pause(1000)
                .release()
                .perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDragAndDropBy() {
        goToDragAndDrop();

        WebElement test1 = getDriver().findElement(By.id("test1"));
        getActions().dragAndDropBy(test1, 200, 400)
                .perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDragAndDropToElement() {
        goToDragAndDrop();

        WebElement test1 = getDriver().findElement(By.id("test1"));
        System.out.println(test1.getLocation());

        WebElement test4 = getDriver().findElement(By.id("test4"));
        System.out.println(test4.getLocation());

        getActions().dragAndDrop(test1, test4).perform();

        System.out.println(test1.getLocation());
        Assert.assertEquals(test1.getLocation(), test4.getLocation());
    }

    @Test
    void testColor() {
        goToWebForm();

        final WebElement colorPicker = getDriver().findElement(By.name("my-colors"));
        System.out.println(colorPicker.getAttribute("value"));

        colorPicker.click();
        getActions()
                .sendKeys(Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB).sendKeys("255")
                .pause(1000)
                .sendKeys(Keys.TAB).sendKeys("0")
                .pause(1000)
                .sendKeys(Keys.TAB).sendKeys("0")
                .pause(1000)
                .sendKeys(Keys.ENTER)
                .perform();

        System.out.println(colorPicker.getAttribute("value"));
        Assert.assertEquals(colorPicker.getAttribute("value"), "#ff0000");
    }
}
