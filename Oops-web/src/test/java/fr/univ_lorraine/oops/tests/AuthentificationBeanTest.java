package fr.univ_lorraine.oops.tests;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.openqa.selenium.support.ui.Select;

public class AuthentificationBeanTest {

    private static WebDriver driver;
    private static String host;

    public AuthentificationBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));
        host = System.getProperty(("glassfishHost"));
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }

    @Before
    public void setUp() {
        driver.get(host + "/login.xhtml");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.name("j_username")).clear();
    }

    @Test
    public void testConnectionSuccess() {
        driver.findElement(By.name("j_username")).sendKeys("noupi");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        String username = "noupi";
        assertEquals(driver.findElement(By.id("username_button")).getText().toLowerCase(), username.toLowerCase());
    }

    @Test
    public void testConnectionFail() {
        driver.findElement(By.name("j_username")).sendKeys("noupi");
        driver.findElement(By.name("j_password")).sendKeys("0000");
        driver.findElement(By.name("submit")).click();
        String message = "Connexion échouée !";
        assertEquals(driver.findElement(By.className("ui-messages-error")).getText(), message);
    }

    @Test
    public void testDeconnectionSuccess() {
        driver.findElement(By.id("username_button")).click();
        driver.findElement(By.id("headerForm:deconnexion")).click();
        String message = "Déconnexion réussie !";
        assertEquals(driver.findElement(By.className("ui-messages-info")).getText(), message);
    }

    @Test
    public void testBlockedByAdmin() {
        driver.get(host + "/login.xhtml");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.name("j_username")).sendKeys("jose");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        driver.get(host + "/fiche.xhtml?page=satan");
        driver.findElement(By.id("banish")).findElement(By.xpath("./button")).click();
     
        driver.get(host + "/login.xhtml");
        driver.findElement(By.name("j_username")).sendKeys("satan");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        String message = "Votre compte a été bloqué par un administrateur !";
        assertEquals(driver.findElement(By.className("ui-messages-error")).getText(), message);
        
        driver.get(host + "/login.xhtml");
        driver.findElement(By.name("j_username")).sendKeys("jose");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        driver.get(host + "/admin/index.xhtml");
        driver.findElement(By.id("bannis:datatableReport")).findElement(By.xpath(".//td[last()]/input")).click();
        assertTrue(true);
    }
}
