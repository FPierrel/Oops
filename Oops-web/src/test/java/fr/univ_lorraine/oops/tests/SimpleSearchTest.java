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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

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
        driver.get("http://localhost:8080/Oops-web/faces/index.xhtml");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.name("searchForm:ou")).clear();
        driver.findElement(By.name("searchForm:quoi")).clear();
    }
    
    @Test
    public void testSimpleSearchWithoutWhereField(){
        driver.findElement(By.name("searchForm:quoi")).sendKeys("Jacky");
        driver.findElement(By.name("searchForm:go")).click();
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }
    
    @Test
    public void testSimpleSearchWithoutWhatField(){
        driver.findElement(By.name("searchForm:ou")).sendKeys("NEUFCHATEAU (88300)");
        driver.findElement(By.name("searchForm:go")).click();
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }
    
    @Test
    public void testSimpleSearchNoResult(){ //Ou alors ce serait pas de bol !!
        driver.findElement(By.name("searchForm:quoi")).sendKeys("lfjieozfhsdjkfhequirfyhsqdfsdfrdsfsfsddvce");
        driver.findElement(By.name("searchForm:ou")).sendKeys("sdfezdifjiohvruaoidusdqifyeurydfvdxcvxcvreie");
        driver.findElement(By.name("searchForm:go")).click();
        Assert.assertTrue(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }
    
    @Test
    public void testSimpleSearch(){
        driver.findElement(By.name("searchForm:quoi")).sendKeys("Jacky");
        driver.findElement(By.name("searchForm:ou")).sendKeys("NEUFCHATEAU (88300)");
        driver.findElement(By.name("searchForm:go")).click();
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }
    
    @Test
    public void testSimpleSearchEmpty(){
        driver.findElement(By.name("searchForm:go")).click();
        Assert.assertTrue(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }
}
