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
import org.openqa.selenium.support.ui.Select;

public class AvisGestionTest {

    private static WebDriver driver;
    private static String host;

    public AvisGestionTest() {
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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

    public void cloture(String s, int i) {
        /* Suppression de l'avis pour garantir l'isolation */
        driver.get(host + "/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("form:datatable:"+i+":supprimer")).click();
        driver.get(host + "/admin/index.xhtml");
    }

    @Test
    public void testNumberOfAvisInWaiting() {
        /* Initialisation */
        this.initialisation("testNumberOfAvisInWaiting");
        driver.get(host + "/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test du nombre d'avis */
        int nb_avis = driver.findElement(By.id("form:datatable")).findElements(By.xpath("./tbody/tr")).size() - 1;
        assertTrue(nb_avis > 0);
        /* Cloture */
        this.cloture("testNumberOfAvisInWaiting", nb_avis);
        //assertFalse(driver.findElement(By.xpath(".//form//table//tr//td")).getText().contains("testNumberOfAvisInWaiting")) ;         
    }

    @Test
    public void testAvisExisting() {
        /* Initialisation */
        this.initialisation("testAvisExisting");
        driver.get(host + "/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test */
        int nb_avis = driver.findElement(By.id("form:datatable")).findElements(By.xpath("./tbody/tr")).size() - 1;
        assertTrue(driver.findElement(By.id("form:datatable:"+nb_avis+":contenu")).getText().contains("testAvisExisting"));
        /* Cloture */
        this.cloture("testAvisExisting", nb_avis);
    }

    @Test
    public void testAvisNotExisting() {
        /* Initialisation */
        this.initialisation("testNotAvisExisting");
        driver.get(host + "/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test */
        int nb_avis = driver.findElement(By.id("form:datatable")).findElements(By.xpath("./tbody/tr")).size() - 1;
        assertFalse(driver.findElement(By.id("form:datatable:" + nb_avis + ":contenu")).getText().contains("hjfkhjuk5454k54d"));
        /* Cloture */
        this.cloture("testNotAvisExisting", nb_avis);
    }

    @Test
    public void testValidation() {
        /* Initialisation */
        this.initialisation("testValidation");
        driver.get(host + "/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int nb_avis = driver.findElement(By.id("form:datatable")).findElements(By.xpath("./tbody/tr")).size() - 1;
        /* Validation */
        driver.findElement(By.id("form:datatable:" + nb_avis + ":valider")).click();
        driver.get(host + "/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test du nombre d'avis */
        nb_avis--;
        assertFalse(driver.findElement(By.id("form:datatable:" + nb_avis + ":contenu")).getText().contains("testValidation"));
    }

    @Test
    public void testSuppression() {
        /* Initialisation */
        this.initialisation("testSuppression");
        driver.get(host + "/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int nb_avis = driver.findElement(By.id("form:datatable")).findElements(By.xpath("./tbody/tr")).size() - 1;
        /* Suppression */
        driver.findElement(By.id("form:datatable:" + nb_avis + ":supprimer")).click();
        driver.get(host + "/admin/index.xhtml");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /* Test du nombre d'avis */
        nb_avis--;
        assertFalse(driver.findElement(By.id("form:datatable:" + nb_avis + ":contenu")).getText().contains("testSuppression"));
    }
}
