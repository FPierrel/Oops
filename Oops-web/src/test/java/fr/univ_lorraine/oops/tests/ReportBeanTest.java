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
        driver.findElement(By.name("j_username")).sendKeys("noupi");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        driver.get(host + "/fiche.xhtml?page=noupi");

    }

    @After
    public void tearDown() {

    }

    @Test
    public void reportFichePrestataireTest() {
        driver.findElement(By.id("formNotes:reportFicheLink")).click();
        Select sel = new Select(driver.findElement(By.id("formNotes:reportFicheForm:reasons")));
        sel.selectByVisibleText("Autre");
       
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheReason")).sendKeys("Test_report");
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheButton")).click();
        
        driver.get(host + "/login.xhtml");
        driver.findElement(By.name("j_username")).sendKeys("jose");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        driver.get(host + "/admin/index.xhtml");
        driver.findElement(By.id("form:tabs")).findElement(By.xpath(".//li[2]/a")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReportBeanTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertTrue((driver.findElement(By.id("form:datatableReport2")).findElement(By.xpath("./tbody/tr[last()]/td[5]")).getText()).contains("Test_report"));
        driver.findElement(By.id("form:datatableReport2")).findElement(By.xpath(".//tr[last()]/td[last()]/input")).click();

    }

    @Test
    public void blockedViaReportsTest() {
        driver.get(host + "/fiche.xhtml?page=satan");
        driver.findElement(By.id("formNotes:reportFicheLink")).click();
        Select sel = new Select(driver.findElement(By.id("formNotes:reportFicheForm:reasons")));
        sel.selectByVisibleText("Autre");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReportBeanTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheReason")).sendKeys("Test_report1");
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheButton")).click();

        driver.findElement(By.id("formNotes:reportFicheLink")).click();
        sel = new Select(driver.findElement(By.id("formNotes:reportFicheForm:reasons")));
        sel.selectByVisibleText("Autre");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReportBeanTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheReason")).clear();
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheReason")).sendKeys("Test_report2");
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheButton")).click();

        driver.findElement(By.id("formNotes:reportFicheLink")).click();
        sel = new Select(driver.findElement(By.id("formNotes:reportFicheForm:reasons")));
        sel.selectByVisibleText("Autre");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReportBeanTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheReason")).clear();
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheReason")).sendKeys("Test_report3");
        driver.findElement(By.id("formNotes:reportFicheForm:reportFicheButton")).click();

        driver.get(host + "/login.xhtml");
        driver.findElement(By.name("j_username")).sendKeys("jose");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        driver.get(host + "/admin/index.xhtml");

        driver.findElement(By.id("form:tabs")).findElement(By.xpath(".//li[2]/a")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReportBeanTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        driver.findElement(By.id("form:datatableReport2")).findElement(By.xpath(".//tr[last()]/td[last()-1]/input")).click();
        driver.findElement(By.id("form:datatableReport2")).findElement(By.xpath(".//tr[last()]/td[last()-1]/input")).click();
        driver.findElement(By.id("form:datatableReport2")).findElement(By.xpath(".//tr[last()]/td[last()-1]/input")).click();
        
        driver.get(host + "/login.xhtml");
        driver.findElement(By.name("j_username")).sendKeys("satan");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        String message = "Votre compte a été bloqué par un administrateur !";
        assertEquals(driver.findElement(By.className("ui-messages-error-summary")).getText(), message);

        driver.get(host + "/login.xhtml");
        driver.findElement(By.name("j_username")).sendKeys("jose");
        driver.findElement(By.name("j_password")).sendKeys("123456");
        driver.findElement(By.name("submit")).click();
        driver.get(host + "/fiche.xhtml?page=satan");
        driver.findElement(By.id("formNotes:banish:pardon")).click();
    }
}
