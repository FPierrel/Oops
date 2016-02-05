/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author romain
 */
public class EditFicheBeanTest {

    private static WebDriver driver;

    public EditFicheBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));
        driver.get("http://localhost:8080/Oops-web/login.xhtml");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.name("j_username")).sendKeys("noupi");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }

    @Before
    public void setUp() {
        driver.get("http://localhost:8080/Oops-web/ficheedit.xhtml");
        driver.findElement(By.name("ficheForm:companyName")).clear();
        driver.findElement(By.name("ficheForm:companyName")).sendKeys("aaaaa");
        driver.findElement(By.id("ficheForm:website")).clear();
        driver.findElement(By.id("ficheForm:website")).sendKeys("aaaaa.aa");
        driver.findElement(By.id("ficheForm:nbEmployee")).clear();
        driver.findElement(By.id("ficheForm:nbEmployee")).sendKeys("0");
        driver.findElement(By.id("ficheForm:turnoverNumber")).clear();
        driver.findElement(By.id("ficheForm:turnoverNumber")).sendKeys("0");
        driver.findElement(By.id("ficheForm:description")).clear();
        driver.findElement(By.id("ficheForm:description")).sendKeys("aaa");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEditFichePrestataireNameEmpty() {
        driver.findElement(By.name("ficheForm:companyName")).clear();
        driver.findElement(By.id("ficheForm:save")).click();
        String message = "Nom du Prestaire non renseigné !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
        assertEquals(driver.findElement(By.className("ui-messages-error-summary")).getText(), message);
    }

    @Test
    public void testEditFichePrestataireName() {
        String val = "TestnameAndCo.";
        driver.findElement(By.name("ficheForm:companyName")).clear();
        driver.findElement(By.name("ficheForm:companyName")).sendKeys(val);
        driver.findElement(By.id("ficheForm:save")).click();
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=noupi");
        assertEquals(driver.findElement(By.id("companyName")).getText(), val);
    }

    @Test
    public void testEditFicheWebsite() {
        String val = "Testname.fr";
        driver.findElement(By.id("ficheForm:website")).clear();
        driver.findElement(By.id("ficheForm:website")).sendKeys(val);
        driver.findElement(By.id("ficheForm:save")).click();
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=noupi");
        assertEquals(driver.findElement(By.id("website")).getText(), "Site Web : " + val);
    }

    @Test
    public void testEditFicheEmployeesNumberIncorrect() {
        driver.findElement(By.id("ficheForm:nbEmployee")).clear();
        String message = "Nombre d'employé(s) incorrect !";
        driver.findElement(By.id("ficheForm:save")).click();
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
        assertEquals(driver.findElement(By.className("ui-messages-error-summary")).getText(), message);
    }

    @Test
    public void testEditFicheEmployeesNumber() {
        driver.findElement(By.id("ficheForm:nbEmployee")).clear();
        String val = "69874";
        driver.findElement(By.id("ficheForm:nbEmployee")).sendKeys(val);
        driver.findElement(By.id("ficheForm:save")).click();
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=noupi");
        assertEquals(driver.findElement(By.id("employeeNumber")).getText(), "Nombre d'employé(s) : " + val);
    }

    @Test
    public void testEditFicheTurnoverIncorrect() {
        driver.findElement(By.id("ficheForm:turnoverNumber")).clear();
        String message = "Chiffre d'affaire incorrect !";
        driver.findElement(By.id("ficheForm:save")).click();
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
        assertEquals(driver.findElement(By.className("ui-messages-error-summary")).getText(), message);
    }

    @Test
    public void testEditFicheTurnover() {
        driver.findElement(By.id("ficheForm:turnoverNumber")).clear();
        String val = "123456789";
        driver.findElement(By.id("ficheForm:turnoverNumber")).sendKeys(val);
        driver.findElement(By.id("ficheForm:save")).click();
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=noupi");
        assertEquals(driver.findElement(By.id("turnover")).getText(), "Chiffre d'affaire : " + val + " €");
    }

    @Test
    public void testEditFicheDescriptionEmpty() {
        driver.findElement(By.id("ficheForm:description")).clear();
        String message = "Description du Prestataire non renseignée !";
        driver.findElement(By.id("ficheForm:save")).click();
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
        assertEquals(driver.findElement(By.className("ui-messages-error-summary")).getText(), message);
    }

    @Test
    public void testEditFicheDescription() {
        driver.findElement(By.id("ficheForm:description")).clear();
        String val = "Ceci est une description de test";
        driver.findElement(By.id("ficheForm:description")).sendKeys(val);
        driver.findElement(By.id("ficheForm:save")).click();
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=noupi");
        assertEquals(driver.findElement(By.id("description")).getText(), val);
    }

}
