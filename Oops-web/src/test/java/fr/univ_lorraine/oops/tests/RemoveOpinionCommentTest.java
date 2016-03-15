/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.tests;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Thibaut
 */
public class RemoveOpinionCommentTest {

    private static WebDriver driver;
    private static String host;

    public RemoveOpinionCommentTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));
        host = System.getProperty(("glassfishHost"));
        driver.get(host + "/login.xhtml");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.name("j_username")).sendKeys("jose");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }

    public void initialisation(String s) {
        /* Création d'un avis propre à ce test */
        driver.get(host + "/fiche.xhtml?page=Oui");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.id("formOpinion:opinion")).clear();
        int notes[] = {0, 0, 0, 0};
        driver.findElement(By.id("s_new_a_delai")).findElements(By.xpath("i")).get(notes[0]).click();
        driver.findElement(By.id("s_new_a_prix")).findElements(By.xpath("i")).get(notes[1]).click();
        driver.findElement(By.id("s_new_a_comm")).findElements(By.xpath("i")).get(notes[2]).click();
        driver.findElement(By.id("s_new_a_qual")).findElements(By.xpath("i")).get(notes[3]).click();
        driver.findElement(By.id("formOpinion:opinion")).sendKeys(s);
        driver.findElement(By.id("formOpinion:opinionButton")).click();
        driver.get(host);
        driver.get(host + "/fiche.xhtml?page=Oui");
    }

    @Test
    public void testSuppressionAvisSuccess() {
        /* Initialisation */
        this.initialisation("testSuppressionAvisSuccess");
        /* Suppression de l'avis */
        driver.findElement(By.xpath(".//form[last()]//a[@class='pull-right']")).click();
        /* Test */
        driver.get(host);
        driver.get(host + "/fiche.xhtml?page=Oui");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        assertFalse(driver.findElement(By.id("comments-list")).getText().contains("testSuppressionAvisSuccess"));
    }

    @Test
    public void testSuppressionCommentSuccess() {
        /* Initialisation */
        this.initialisation("Aviszzz");
        /* Ajout commentaire */ 
        driver.get(host + "/fiche.xhtml?page=Oui");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);   
        String msg = "testSuppressionCommentSuccess";
        driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]//div[@class='media-heading']/a[last()]")).click();
        driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]//input[@placeholder='Réponse']")).sendKeys(msg);
        driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]//div[@class='media-body']/div[last()]/div/a")).click();
        driver.get(host);
        driver.get(host + "/fiche.xhtml?page=Oui");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);   
        /* Verif presence commentaire */ 
        assertTrue(driver.findElement(By.id("comments-list")).getText().contains(msg));
        driver.get(host + "/fiche.xhtml?page=Oui");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        /* Suppression du commentaire */ 
        driver.findElement(By.id("comments-list")).findElement(By.xpath("./form[last()]//div[@class='media']//div[@class='media']//div[@class='media-heading']/a")).click();
        driver.get(host);
        driver.get(host + "/fiche.xhtml?page=Oui");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        /* Verif absence commentaire */
        assertFalse(driver.findElement(By.id("comments-list")).getText().contains(msg));
        /* Suppression avis */
        driver.findElement(By.xpath(".//form[last()]//a[@class='pull-right']")).click();
    }

}
