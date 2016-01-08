package fr.univ_lorraine.oops.tests;

import java.util.concurrent.TimeUnit;
import org.junit.After;
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

public class ProfilBeanTest {

    private static WebDriver driver;

    public ProfilBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));
        driver.get("http://localhost:8080/Oops-web/faces/profil.xhtml");
        driver.findElement(By.id("loginForm:username")).sendKeys("john");
        driver.findElement(By.id("loginForm:password")).sendKeys("123456");
        driver.findElement(By.id("loginForm:login")).click();
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }

    @Before
    public void setUp() {
        driver.get("http://localhost:8080/Oops-web/faces/profil.xhtml");
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
                driver.findElement(By.id("profilForm:newMail")).clear();
        driver.findElement(By.id("profilForm:newMailConfirm")).clear();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEditProfilSuccess() {
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Modification(s) enregistrée(s) !";
        assertEquals(driver.findElement(By.className("ui-message-info-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongOldPassword() {
        driver.findElement(By.id("profilForm:oldPassword")).sendKeys("0000");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Mot de passe incorrect !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongNewPassword() {
        driver.findElement(By.id("profilForm:oldPassword")).sendKeys("123456");
        driver.findElement(By.id("profilForm:newPassword")).sendKeys("0000");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Mot de passe trop petit (inférieur à 6), veuillez recommencer !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongNewPasswordConfirm() {
        driver.findElement(By.id("profilForm:oldPassword")).sendKeys("123456");
        driver.findElement(By.id("profilForm:newPassword")).sendKeys("123456789");
        driver.findElement(By.id("profilForm:newPasswordConfirm")).sendKeys("0000");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Confirmation du nouveau mot de passe incorrecte !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongNewMail() {
        driver.findElement(By.id("profilForm:newMail")).sendKeys("a@a");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Adresse mail non valide, veuillez recommencer !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongNewMailConfirm() {
        driver.findElement(By.id("profilForm:newMail")).sendKeys("bonjour@hello.fr");
        driver.findElement(By.id("profilForm:newMailConfirm")).sendKeys("hello@hello.fr");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Confirmation de la nouvelle adresse mail incorrecte !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }
}
