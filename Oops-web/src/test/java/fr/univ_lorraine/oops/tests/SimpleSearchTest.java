package fr.univ_lorraine.oops.tests;

import fr.univ_lorraine.oops.beans.RegistrationBean;
import static fr.univ_lorraine.oops.tests.RegistrationBeanTest.driver;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SimpleSearchTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setUpClass() {
        //driver = new FirefoxDriver();
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));
    }

    @Before
    public void setUp() {
        driver.get("http://localhost:8080/Oops-web/index.xhtml");
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.findElement(By.id("searchForm:ou")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.id("searchForm:ou")).sendKeys(Keys.DELETE);
        driver.findElement(By.id("searchForm:quoi")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.id("searchForm:quoi")).sendKeys(Keys.DELETE);
    }

    @Test
    public void testSimpleSearchWithoutWhereField() throws InterruptedException {
        driver.findElement(By.id("searchForm:quoi")).sendKeys("Jacky");
        driver.findElement(By.id("searchForm:go")).click();
        WebDriverWait wdw = new WebDriverWait(driver, 40);
        wdw.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("nbResult")));
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testSimpleSearchWithoutWhatField() throws InterruptedException {
        driver.findElement(By.id("searchForm:ou")).sendKeys("NEUFCHATEAU (88300)");
        driver.findElement(By.id("searchForm:go")).click();
        WebDriverWait wdw = new WebDriverWait(driver, 40);
        wdw.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("nbResult")));
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testSimpleSearchNoResult() throws InterruptedException { //Ou alors ce serait pas de bol !!
        driver.findElement(By.id("searchForm:quoi")).sendKeys("lfjieozfhsdjkfhequirfyhsqdfsdfrdsfsfsddvce");
        driver.findElement(By.id("searchForm:ou")).sendKeys("sdfezdifjiohvruaoidusdqifyeurydfvdxcvxcvreie");
        driver.findElement(By.id("searchForm:go")).click();
        WebDriverWait wdw = new WebDriverWait(driver, 40);
        wdw.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("nbResult")));
        Assert.assertTrue(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testSimpleSearch() throws InterruptedException {
        driver.findElement(By.id("searchForm:quoi")).sendKeys("Jacky");
        driver.findElement(By.id("searchForm:ou")).sendKeys("NEUFCHATEAU (88300)");
        driver.findElement(By.id("searchForm:go")).click();
        WebDriverWait wdw = new WebDriverWait(driver, 40);
        wdw.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("nbResult")));
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testSimpleSearchEmpty() {
        driver.findElement(By.id("searchForm:go")).click();
        WebDriverWait wdw = new WebDriverWait(driver, 40);
        wdw.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("nbResult")));
        Assert.assertTrue(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }
}
