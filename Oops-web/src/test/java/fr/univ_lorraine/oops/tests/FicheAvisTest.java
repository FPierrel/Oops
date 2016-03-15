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
    private static String host;

    public FicheAvisTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));
        host = System.getProperty(("glassfishHost"));
        driver.get(host +"/login.xhtml");
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
        driver.get(host + "/fiche.xhtml?page=satan");
        driver.findElement(By.id("formOpinion:opinion")).clear();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFicheAvisComplete() {
        int notes[] = {4, 3, 2, 1};
        driver.findElement(By.id("s_new_a_comm")).findElements(By.xpath("i")).get(notes[0]-1).click();
        driver.findElement(By.id("s_new_a_delai")).findElements(By.xpath("i")).get(notes[1]-1).click();
        driver.findElement(By.id("s_new_a_prix")).findElements(By.xpath("i")).get(notes[2]-1).click();
        driver.findElement(By.id("s_new_a_qual")).findElements(By.xpath("i")).get(notes[3]-1).click();
        String comment = "Ceci est un avis de test";
        driver.findElement(By.id("formOpinion:opinion")).sendKeys(comment);
        driver.findElement(By.id("formOpinion:opinionButton")).click();
        driver.get(host);
        driver.get(host + "/fiche.xhtml?page=satan");
        assertTrue(driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]//strong")).getText().contains("noupi"));

        List<WebElement> l = driver.findElement(By.id("comments-list")).findElements(By.xpath("./form[last()]//div[@class='starrr']"));
        for (int i = 0; i <= 3; i++) {
            assertEquals(l.get(i).findElements(By.xpath("./i[@class='fa fa-star']")).size(), notes[i]);
        }
        assertEquals(driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]/div/div[2]/div/p")).getText(), comment);
    }

    @Test
    public void testFicheAvisCommentMissing() {
        driver.findElement(By.id("formOpinion:opinionButton")).click();
        assertEquals(driver.findElement(By.className("ui-messages-error-summary")).getText(), "Veuillez renseigner un avis !");
    }

    @Test
    public void testFicheAvisResponse() {
        String msg = "Ceci est un commentaire en réponse à un avis";
        driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]//div[@class='media-heading']/a[last()]")).click();
        driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]//input[@placeholder='Réponse']")).sendKeys(msg);
        driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]//div[@class='media-body']/div[last()]/div/a")).click();
        driver.get(host);
        driver.get(host + "/fiche.xhtml?page=satan");
        assertTrue(driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]/div/div[2]/div[2]//strong")).getText().contains("noupi"));
        assertTrue(driver.findElement(By.id("comments-list")).getText().contains(msg));
    }
}
