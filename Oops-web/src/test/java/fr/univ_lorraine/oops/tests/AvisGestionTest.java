/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.tests;

import static fr.univ_lorraine.oops.tests.RegistrationBeanTest.driver;
import java.util.List;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Thibaut
 */
public class AvisGestionTest {

    private static WebDriver driver;

    public AvisGestionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));
        driver.get("http://localhost:8080/Oops-web/login.xhtml");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.name("j_username")).sendKeys("jose");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
    }

    @AfterClass
    public static void tearDownClass() {
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=satan");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("formOpinion:opinion")).clear();
        int notes[] = {0, 0, 0, 0};
        driver.findElement(By.id("formOpinion:rate1")).findElements(By.xpath("div")).get(notes[0]).click();
        driver.findElement(By.id("formOpinion:rate2")).findElements(By.xpath("div")).get(notes[1]).click();
        driver.findElement(By.id("formOpinion:rate3")).findElements(By.xpath("div")).get(notes[2]).click();
        driver.findElement(By.id("formOpinion:rate4")).findElements(By.xpath("div")).get(notes[3]).click();
        String comment = "Bonjour satan";
        driver.findElement(By.id("formOpinion:opinion")).sendKeys(comment);
        driver.findElement(By.id("formOpinion:opinionButton")).click();
        driver.quit();
    }

    @Before
    public void setUp() {
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testNumberOfAvisInWaiting() {
        int bonjour = driver.findElements(By.xpath(".//form//table//tr")).size();
        assertTrue(bonjour > 0);
    }

    @Test
    public void testAvisExisting() {
        assertTrue(driver.findElement(By.id("avisEnAttente:datatable")).getText().contains("jose"));
    }

    @Test
    public void testAvisNotExisting() {
        assertFalse(driver.findElement(By.id("avisEnAttente:datatable")).getText().contains("jbvskjbvljbl564bgdsr1b"));
    }

    @Test
    public void testValidation() {
        driver.findElement(By.id("avisEnAttente:datatable:0:valider")).click();
        assertFalse(driver.findElement(By.id("avisEnAttente:datatable")).getText().contains("Bonjour les amis"));
    }

    @Test
    public void testSuppression() {
        //driver.findElement(By.name("avisEnAttente:datatable:0:supprimer")).click();
        /*driver.get("http://localhost:8080/Oops-web/");
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=noupi");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertTrue(driver.findElement(By.id("opinions")).findElement(By.xpath("./form[last()]/fieldset/legend")).getText().contains("Bonjour satan"));*/
        //assertFalse(driver.findElement(By.id("avisEnAttente:datatable")).getText().contains("Bonjour satan"));
    }
}
