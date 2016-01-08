package fr.univ_lorraine.oops.tests;

import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AuthentificationBeanTest {

    private static WebDriver driver;

    public AuthentificationBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }

    @Before
    public void setUp() {
        driver.get("http://localhost:8080/Oops-web/faces/login.xhtml");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.name("j_username")).clear();
    }

    @Test
    public void testConnectionSuccess() {
        driver.findElement(By.name("j_username")).sendKeys("noupi");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        String username = "noupi";
        assertEquals(driver.findElement(By.id("headerForm:username_button")).getText().toLowerCase(), username.toLowerCase());
    }

    @Test
    public void testConnectionFail() {
        driver.findElement(By.name("j_username")).sendKeys("noupi");
        driver.findElement(By.name("j_password")).sendKeys("0000");
        driver.findElement(By.name("submit")).click();
        String message = "Connexion échoué !";
        assertEquals(driver.findElement(By.className("ui-messages-error")).getText(), message);
    }
    
    @Test
    public void testDeconnectionSuccess() {
        driver.findElement(By.id("headerForm:username_button")).click();
        driver.findElement(By.id("headerForm:deconnexion")).click();
        String message = "Déconnexion réussie !";
        assertEquals(driver.findElement(By.className("ui-messages-info")).getText(), message);
    }    
}
