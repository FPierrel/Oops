/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.tests;

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

/**
 *
 * @author Romain
 */
public class FicheAvisTest {

    private static WebDriver driver;

    public FicheAvisTest() {
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
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=satan");
        driver.findElement(By.id("formOpinion:opinion")).clear();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFicheAvisComplete() {
        int notes[] = {4, 3, 2, 1};
        driver.findElement(By.id("formOpinion:rate1")).findElements(By.xpath("div")).get(notes[0] - 1).click();
        driver.findElement(By.id("formOpinion:rate2")).findElements(By.xpath("div")).get(notes[1] - 1).click();
        driver.findElement(By.id("formOpinion:rate3")).findElements(By.xpath("div")).get(notes[2] - 1).click();
        driver.findElement(By.id("formOpinion:rate4")).findElements(By.xpath("div")).get(notes[3] - 1).click();
        String comment = "Ceci est un avis de test";
        driver.findElement(By.id("formOpinion:opinion")).sendKeys(comment);
        driver.findElement(By.id("formOpinion:opinionButton")).click();
        driver.get("http://localhost:8080/Oops-web/");
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=satan");
        assertTrue(driver.findElement(By.id("opinions")).findElement(By.xpath("./form[last()]/fieldset/legend")).getText().contains("noupi"));
        
        List<WebElement> l = driver.findElement(By.id("opinions")).findElement(By.xpath("./form[last()]/fieldset/div/table")).findElements(By.xpath(".//input"));
        for (int i = 0; i <= 3; i++) {
            assertEquals(l.get(i).getAttribute("value"), notes[i] + "");
        }
        assertEquals(driver.findElement(By.id("opinions")).findElement(By.xpath("form[last()]/fieldset/div/span")).getText(), comment);
    }
    
    @Test
    public void testFicheAvisCommentMissing(){
         driver.findElement(By.id("formOpinion:opinionButton")).click();
         assertEquals(driver.findElement(By.className("ui-messages-error-summary")).getText(), "Veuillez renseigner un avis !");
            }
    @Test
    public void testFicheAvisResponse(){
        String msg = "Ceci est un commentaire en réponse à un avis";
        driver.findElement(By.id("opinions")).findElement(By.xpath("./form[last()]/fieldset/div/table[last()]")).findElement(By.xpath(".//input")).sendKeys(msg);
        driver.findElement(By.id("opinions")).findElement(By.xpath("./form[last()]/fieldset/div/table[last()]")).findElement(By.xpath(".//button")).click();
        driver.get("http://localhost:8080/Oops-web/");
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=satan");
        assertTrue(driver.findElement(By.id("opinions")).findElement(By.xpath("./form[last()]/fieldset/div/fieldset/legend")).getText().contains("noupi"));
        assertTrue(driver.findElement(By.id("opinions")).findElement(By.xpath("./form[last()]/fieldset/div/fieldset/div")).getText().contains(msg));
    }
}
