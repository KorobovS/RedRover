package Lesson26;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.fail;

public class AlertTest extends BaseTest {

    void goToAlerts() {
        getDriver().findElement(By.linkText("alerts.html")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
    }

    @Test
        // получить текст, alert загружается быстро
    void testReadAlertText() {
        goToAlerts();

        getDriver().findElement(By.id("alert")).click();

        final Alert alert = getDriver().switchTo().alert();
        String alertText = alert.getText();

        Assert.assertEquals(alertText, "cheese");
    }

    @Test
        // получить текст, alert загружается медленно
    void testSlowAlert() {
        goToAlerts();

        getDriver().findElement(By.id("slow-alert")).click();

        getWait10().until(ExpectedConditions.alertIsPresent());
        String slowAlert = getDriver().switchTo().alert().getText();

        Assert.assertEquals(slowAlert, "Slow");
    }

    @Test
        // кликнуть единственную кнопку
    void testClickOkOnAlert() {
        goToAlerts();

        getDriver().findElement(By.id("alert")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();
        try {
            getDriver().switchTo().alert();
            fail("Error");
        } catch (NoAlertPresentException e) {
            //pass
        }
    }

    @Test
        // кликнуть ОК и перенаправиться на другую страницу (ждем пока отработает JS)
    void testAcceptAlert() {
        goToAlerts();

        getDriver().findElement(By.id("confirm")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        String textNewPage = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(textNewPage, "Heading");
    }

    @Test
        // кликнули Cancel
    void testDismissAlert() {
        goToAlerts();

        getDriver().findElement(By.id("confirm")).click();

        getDriver().switchTo().alert().dismiss();
        String text = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(text, "Testing Alerts and Stuff");
    }

    @Test
        // с одним полем
    void testAcceptPrompt() {
        goToAlerts();

        getDriver().findElement(By.id("prompt")).click();

        Alert alert = getWait10().until(ExpectedConditions.alertIsPresent());
        final String enteredText = "Something ------";
        alert.sendKeys(enteredText);
        alert.accept();

        String actualText = getDriver().findElement(By.id("text")).getText();

        Assert.assertEquals(actualText, enteredText);

    }
}
