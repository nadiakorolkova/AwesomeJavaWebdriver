package Day3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;



public class WindowHandles {
    WebDriver driver;

    @BeforeSuite
    public void suiteSetup (){
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +  "/src/test/resources/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void testWindows() {
        String windowsPageURL = "https://the-internet.herokuapp.com/windows";
        By link = By.partialLinkText("Click Here");
        String textToAssert = "New Window";
        navigateToURL(windowsPageURL);
        String originWindowHandle = getCurrentHandle();
        clickOnTheLink(link);
        explicitWait();
        assertTwoWindows();
        switchToNewWindow(originWindowHandle);
        assertTextOnPage(textToAssert);

    }

    private void explicitWait() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("example")) );

    }

    private void assertTextOnPage(String textToAssert) {
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(textToAssert));
    }

    private void sleepThread() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void switchToNewWindow(String originWindowHandle) {

        Set<String> allWindows = driver.getWindowHandles();

        for (String eachWindow : allWindows) {
            if(!eachWindow.equals(originWindowHandle)) {
                driver.switchTo().window(eachWindow);
            }
        }
    }

    private void assertTwoWindows() {
        int expectedNumberOfWindows = 2;
        int actualNumberOfWindows = driver.getWindowHandles().size();
        Assert.assertEquals(actualNumberOfWindows, expectedNumberOfWindows);
    }

    private String getCurrentHandle() {
        return driver.getWindowHandle();
    }

    private void clickOnTheLink(By element) {
        driver.findElement(element).click();
    }


    private void navigateToURL(String URL) {
        driver.get(URL);

    }
}
