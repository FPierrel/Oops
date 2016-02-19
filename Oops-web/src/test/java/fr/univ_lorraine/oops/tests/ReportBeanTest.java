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
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Romain
 */
public class ReportBeanTest {

    private static WebDriver driver;
    private static String host;

    public ReportBeanTest() {
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
        driver.findElement(By.name("j_username")).sendKeys("jose");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        driver.get(host + "/fiche.xhtml?page=noupi");

    }

    @After
    public void tearDown() {

    }

    @Test
    public void reportFichePrestataire() {
        driver.findElement(By.id("reportFiche:reportFiche")).click();
        Select sel = new Select(driver.findElement(By.id("reportFiche:reasons")));
        sel.selectByVisibleText("Autre");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReportBeanTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        driver.findElement(By.id("reportFiche:reportFicheReason")).sendKeys("Test_report");
        driver.findElement(By.id("reportFiche:reportFicheButton")).click();
        /*  driver.get(host + "/login.xhtml");
         driver.findElement(By.name("j_username")).sendKeys("jose");
         driver.findElement(By.name("j_password")).sendKeys("123456");
         driver.findElement(By.name("submit")).click();*/
        driver.get(host + "/admin/index.xhtml");
        assertTrue((driver.findElement(By.id("signalementFicheEnAttente:datatableReport")).getText()).contains("Test_report"));
        driver.findElement(By.id("signalementFicheEnAttente:datatableReport")).findElement(By.xpath("tbody/tr[last()]/td[last()]/input")).click();

    }

}   
