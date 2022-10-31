package ru.rototype.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class test {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 10, 2000);

        actions = new Actions(driver);

        driver.get("https://www.ratatype.com/ru/");
    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[1][0]);
    }

    @Test
    public void loco() {

        WebElement loginButton = driver.findElement(By.xpath("//a[contains(text(), 'Вход')]"));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();

        /**
         * Заполнить мыло на месте ****
         */
        wait.until(ExpectedConditions.titleIs("Войти — Ratatype"));
        WebElement mail = driver.findElement(By.xpath("//input[contains(@name, 'login')]"));
        mail.sendKeys("****");

        /**
         * Заполнить пароль на месте ****
         */
        WebElement pass = driver.findElement(By.xpath("//input[contains(@name, 'password')]"));
        pass.sendKeys("****");

        WebElement buttonSubmit = driver.findElement(By.xpath("//button[contains(@type, 'submit')]"));
        wait.until(ExpectedConditions.elementToBeClickable(buttonSubmit));
        buttonSubmit.click();

        wait.until(ExpectedConditions.titleIs("Dmitriy — Ratatype"));

        WebElement testirovanieButton = driver.findElement(By.xpath("//a[contains(text(), 'Тестирование')]"));
        wait.until(ExpectedConditions.elementToBeClickable(testirovanieButton));
        testirovanieButton.click();

        wait.until(ExpectedConditions.titleIs("Тест скорости печати онлайн — Ratatype"));

        WebElement proitiTest = driver.findElement(By.xpath("//a[contains(text(), 'ПРОЙТИ')]"));
        wait.until(ExpectedConditions.elementToBeClickable(proitiTest));
        proitiTest.click();

        WebElement submitGR = driver.findElement(By.xpath("//button[contains(@class, 'submit gr')]"));
        submitGR.click();

        WebElement greenElem = driver.findElement(By.xpath("//div[contains(@class, 'mainTxt')]//span[contains(@class, 'wgreen')]"));
        actions.keyDown(Keys.SHIFT)
                .sendKeys(greenElem.getText())
                .keyUp(Keys.SHIFT)
                .build()
                .perform();

        List<WebElement> massElems = driver.findElements(By.xpath("//div[contains(@class, 'mainTxt')]//span[contains(@class, 'w')]"));
        for (WebElement elem : massElems) {
            if (elem.getText().equals("")) {
                actions.sendKeys(" ").perform();
            } else {
                try {
                    Thread.sleep(14, 400000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                actions.sendKeys(elem.getText()).perform();
            }
            System.out.print(elem.getText());
        }

    }

    @Test
    public void nestWithNoShift() {
        WebElement start = driver.findElement(By.xpath("//button[contains(text(), 'Начать печатать')]"));
        wait.until(ExpectedConditions.elementToBeClickable(start));
        start.click();

        WebElement startPechat = driver.findElement(By.xpath("//div[contains(@class, 'f2')]"));
        String str = startPechat.getText();
        actions.sendKeys(str).perform();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void after() {
        driver.quit();
    }
}
