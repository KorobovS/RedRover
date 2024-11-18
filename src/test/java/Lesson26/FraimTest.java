package Lesson26;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FraimTest extends BaseTest {

    void goToFrames() {
        getDriver().findElement(By.linkText("iframes.html")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
    }

    void goToOtherFrames() {
        getDriver().findElement(By.linkText("slow_loading_iframes.html")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("iframe")));
    }

    @Test
    void testElementInIFrame() {
        goToFrames();

        try {
            System.out.println(getDriver().findElement(By.id("email")));
            Assert.fail("Элемент найден");
        } catch (NoSuchElementException e) {
            System.out.println("Элемент не найден, едем дальше");
        }

        WebElement frame = getDriver().findElement(By.id("iframe1"));
        getDriver().switchTo().frame(frame);
        WebElement email = getDriver().findElement(By.id("email"));

        Assert.assertEquals("input", email.getTagName());
    }

    @Test
    void testElementOutsideIFrame() {
        goToFrames();
        Assert.assertEquals("h1", getDriver().findElement(By.id("iframe_page_heading")).getTagName());

        getDriver().switchTo().frame("iframe1");

        try {
            getDriver().findElement(By.id("iframe_page_heading"));
            Assert.fail("Элемент найден");
        } catch (NoSuchElementException e) {
            System.out.println("Элемент не найден, Находимся в iframe");
        }

        getDriver().switchTo().defaultContent();
        Assert.assertEquals("h1", getDriver().findElement(By.id("iframe_page_heading")).getTagName());
    }

    @Test
    void testSwitchBetweenIFrame() {
        goToOtherFrames();

        WebElement secondFrame = getDriver().findElement(By.id("noSrc"));
        getDriver().switchTo().frame("noSrc");
        getDriver().switchTo().defaultContent();
        getDriver().switchTo().frame(0);

        Assert.assertEquals("section", getDriver().findElement(By.id("announcement-banner")).getTagName());
    }
}
