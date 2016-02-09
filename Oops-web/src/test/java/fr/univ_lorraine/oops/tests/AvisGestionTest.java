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
        driver.quit();
    }

    public void initialisation(String s) {
        /* Création d'un avis propre à ce test */ 
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=Oui");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("formOpinion:opinion")).clear();
        int notes[] = {0, 0, 0, 0};
        driver.findElement(By.id("formOpinion:rate1")).findElements(By.xpath("div")).get(notes[0]).click();
        driver.findElement(By.id("formOpinion:rate2")).findElements(By.xpath("div")).get(notes[1]).click();
        driver.findElement(By.id("formOpinion:rate3")).findElements(By.xpath("div")).get(notes[2]).click();
        driver.findElement(By.id("formOpinion:rate4")).findElements(By.xpath("div")).get(notes[3]).click();
        driver.findElement(By.id("formOpinion:opinion")).sendKeys(s);
        driver.findElement(By.id("formOpinion:opinionButton")).click();
        driver.get("http://localhost:8080/Oops-web/");
        driver.get("http://localhost:8080/Oops-web/fiche.xhtml?page=Oui");
    }
    
    public void cloture(String s, int i) {
        /* Suppression de l'avis pour garantir l'isolation */
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("avisEnAttente:datatable:"+i+":supprimer")).click();
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
    }

    @Test
    public void testNumberOfAvisInWaiting() {
        /* Initialisation */ 
        this.initialisation("testNumberOfAvisInWaiting");
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test du nombre d'avis */
        int bonjour = driver.findElements(By.xpath(".//form//table//tr//td//span")).size()-1;
        assertTrue(bonjour>0);
        /* Cloture */
        this.cloture("testNumberOfAvisInWaiting",bonjour);
        //assertFalse(driver.findElement(By.xpath(".//form//table//tr//td")).getText().contains("testNumberOfAvisInWaiting")) ;         
    }

    @Test
    public void testAvisExisting() {
        /* Initialisation */ 
        this.initialisation("testAvisExisting");
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test */
        int bonjour = driver.findElements(By.xpath(".//form//table//tr//td//span")).size()-1;
        assertTrue(driver.findElement(By.id("avisEnAttente:datatable:"+bonjour+":contenu")).getText().contains("testAvisExisting")) ;
        /* Cloture */
        this.cloture("testAvisExisting",bonjour);
    }

    @Test
    public void testAvisNotExisting() {
        /* Initialisation */ 
        this.initialisation("testNotAvisExisting");
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test */
        int bonjour = driver.findElements(By.xpath(".//form//table//tr//td//span")).size()-1;
        assertFalse(driver.findElement(By.id("avisEnAttente:datatable:"+bonjour+":contenu")).getText().contains("hjfkhjuk5454k54d")) ;
        /* Cloture */
        this.cloture("testNotAvisExisting",bonjour);
    }

    @Test
    public void testValidation() {
        /* Initialisation */ 
        this.initialisation("testValidation");
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int bonjour = driver.findElements(By.xpath(".//form//table//tr//td//span")).size()-1;
        /* Validation */ 
        driver.findElement(By.id("avisEnAttente:datatable:"+bonjour+":valider")).click();
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test du nombre d'avis */
        bonjour -- ;
        assertFalse(driver.findElement(By.id("avisEnAttente:datatable:"+bonjour+":contenu")).getText().contains("testValidation")) ;
    }

    @Test
    public void testSuppression() {
        /* Initialisation */ 
        this.initialisation("testSuppression");
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int bonjour = driver.findElements(By.xpath(".//form//table//tr//td//span")).size()-1;
        /* Suppression */ 
        driver.findElement(By.id("avisEnAttente:datatable:"+bonjour+":supprimer")).click();
        driver.get("http://localhost:8080/Oops-web/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test du nombre d'avis */
        bonjour -- ;
        assertFalse(driver.findElement(By.id("avisEnAttente:datatable:"+bonjour+":contenu")).getText().contains("testSuppression")) ;
    }
}