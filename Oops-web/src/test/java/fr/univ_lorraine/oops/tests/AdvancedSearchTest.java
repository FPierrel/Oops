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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class AdvancedSearchTest {

    private static WebDriver driver;

    public AdvancedSearchTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));

    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }

    @Before
    public void setUp() {
        driver.get("http://localhost:8080/Oops-web/index.xhtml");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //      driver.findElement(By.id("searchForm:advanced_checkbox")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AdvancedSearchTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        driver.findElement(By.id("searchForm:firstnameSearch")).clear();
        driver.findElement(By.id("searchForm:lastnameSearch")).clear();
        driver.findElement(By.id("searchForm:employeeSearch")).clear();
        driver.findElement(By.id("searchForm:chiffreAffaireSearch")).clear();
        new Select(driver.findElement(By.id("searchForm:categorie"))).selectByVisibleText("Toutes cat\u00e9gories");
    }

    @After
    public void tearDown() {
        //    driver.findElement(By.id("searchForm:advanced_checkbox")).click();
    }

    @Test
    public void testAdvancedSearchWithLastName() {
        driver.findElement(By.id("searchForm:lastnameSearch")).sendKeys("bov\u00e9");
        driver.findElement(By.id("searchForm:go")).click();
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testAdvancedSearchWithWrongLastName() {
        driver.findElement(By.id("searchForm:lastnameSearch")).sendKeys("sgertgyeyeyeysb");
        driver.findElement(By.id("searchForm:go")).click();
        Assert.assertTrue(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testAdvancedSearchWithFirstName() {
        driver.findElement(By.id("searchForm:firstnameSearch")).sendKeys("jos\u00e9");
        driver.findElement(By.id("searchForm:go")).click();
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testAdvancedSearchWithWrongFirstName() {
        driver.findElement(By.id("searchForm:firstnameSearch")).sendKeys("sdglsdgljsqgd");
        driver.findElement(By.id("searchForm:go")).click();
        Assert.assertTrue(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testAdvancedSearchWithNumberEmployees() {
        driver.findElement(By.id("searchForm:employeeSearch")).sendKeys("300");
        driver.findElement(By.id("searchForm:go")).click();
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testAdvancedSearchWithChiffreAffaire() {
        driver.findElement(By.id("searchForm:chiffreAffaireSearch")).sendKeys("300000");
        driver.findElement(By.id("searchForm:go")).click();
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testAdvancedSearchWithCategorieRestauration() {
        Select sel = new Select(driver.findElement(By.id("searchForm:categorie")));
        sel.selectByVisibleText("--Restauration");
        driver.findElement(By.id("searchForm:go")).click();
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testAdvancedSearchWithCategorieAudit() {
        Select sel = new Select(driver.findElement(By.id("searchForm:categorie")));
        sel.selectByVisibleText("----Audit");
        driver.findElement(By.id("searchForm:go")).click();
        Assert.assertTrue(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }

    @Test
    public void testAdvancedSearchEmpty() {
        driver.findElement(By.id("searchForm:go")).click();
        Assert.assertFalse(driver.findElement(By.id("nbResult")).getText().contains("Aucun"));
    }
}
