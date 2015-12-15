package fr.univ_lorraine.oops.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fr.univ_lorraine.oops.beans.RegistrationBean;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.Random;
import org.junit.After;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author thibaut
 */
public class RegistrationBeanTest {
    
    RegistrationBean rb ; 
    static WebDriver driver ; 
    Time time  ; 
    
    public RegistrationBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        //driver = new FirefoxDriver() ; 
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
    }

    @Before
    public void setUp() {
        rb = new RegistrationBean();
        driver.get("http://localhost/oops/faces/inscription.xhtml");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        this.fillFields();
    }
    
    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }
    
    public void fillFields() {
        // Clear inputText to preserve isolation between tests
        driver.findElement(By.id("j_idt20:login")).clear();
        driver.findElement(By.id("j_idt20:password")).clear();
        driver.findElement(By.id("j_idt20:confirmPassword")).clear();
        driver.findElement(By.id("j_idt20:firstname")).clear();
        driver.findElement(By.id("j_idt20:lastname")).clear();
        driver.findElement(By.id("j_idt20:phone")).clear();
        driver.findElement(By.id("j_idt20:email")).clear();
        driver.findElement(By.id("j_idt20:number")).clear();
        driver.findElement(By.id("j_idt20:street")).clear();
        driver.findElement(By.id("j_idt20:complement")).clear();
        driver.findElement(By.id("j_idt20:code")).clear();
        driver.findElement(By.id("j_idt20:town")).clear();
        driver.findElement(By.id("j_idt20:country")).clear();
        // Complete fields with no errors
        driver.findElement(By.id("j_idt20:login")).sendKeys("Jose");
        driver.findElement(By.id("j_idt20:password")).sendKeys("JoseJose");
        driver.findElement(By.id("j_idt20:confirmPassword")).sendKeys("JoseJose");
        driver.findElement(By.id("j_idt20:firstname")).sendKeys("Jose");
        driver.findElement(By.id("j_idt20:lastname")).sendKeys("Jose");
        driver.findElement(By.id("j_idt20:phone")).sendKeys("06 66 66 66 66");
        driver.findElement(By.id("j_idt20:email")).sendKeys("jose@jose.jose");
        driver.findElement(By.id("j_idt20:number")).sendKeys("12");
        driver.findElement(By.id("j_idt20:street")).sendKeys("laRue");
        driver.findElement(By.id("j_idt20:complement")).sendKeys("3e étage");
        driver.findElement(By.id("j_idt20:code")).sendKeys("92000");
        driver.findElement(By.id("j_idt20:town")).sendKeys("laVille");
        driver.findElement(By.id("j_idt20:country")).sendKeys("lePays");
    }
    
    public int randomLogin() {
        Random r = new Random() ; 
        return r.nextInt(10000);  
    }
    
    @Test
    public void testRegistration() {
         assertEquals(driver.getTitle(),"Oops | Page d'inscription") ; 
    }
    
    @Test
    public void testRegistrationSuccess() {
        int log = this.randomLogin();
        driver.findElement(By.id("j_idt20:login")).sendKeys(log+"");
        driver.findElement(By.id("j_idt20:email")).clear();
        driver.findElement(By.id("j_idt20:email")).sendKeys(log+"@jose.jose");
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Inscription réussie !" ; 
        assertEquals(driver.findElement(By.className("ui-messages-info-detail")).getText(),str);
    }
    
    @Test
    public void testRegistrationLoginAlreadyExist() {
        int log = this.randomLogin();
        driver.findElement(By.id("j_idt20:login")).sendKeys(log+"");
        driver.findElement(By.id("j_idt20:email")).clear();
        driver.findElement(By.id("j_idt20:email")).sendKeys(log+"@jose.jose");
        driver.findElement(By.name("j_idt20:register")).click();
        driver.get("http://localhost:8080/Oops-web/faces/inscription.xhtml");
        this.fillFields();
        driver.findElement(By.id("j_idt20:login")).sendKeys(log+"");
        driver.findElement(By.id("j_idt20:email")).clear();
        driver.findElement(By.id("j_idt20:email")).sendKeys(log+"@jose.jose");
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Login déjà  utilisé, veuillez en choisir un nouveau !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str);
    }
    
    @Test
    public void testRegistrationShortPassword() throws InterruptedException {  
        driver.findElement(By.id("j_idt20:password")).clear();
        driver.findElement(By.id("j_idt20:confirmPassword")).clear();
        driver.findElement(By.id("j_idt20:password")).sendKeys("Jose");
        driver.findElement(By.id("j_idt20:confirmPassword")).sendKeys("Jose");
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Mot de passe trop petit (inférieur à 6), veuillez recommencer !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
    }
    
    @Test
    public void testRegistrationMismatchPassword() {    
        driver.findElement(By.id("j_idt20:password")).clear();
        driver.findElement(By.id("j_idt20:confirmPassword")).clear();
        driver.findElement(By.id("j_idt20:password")).sendKeys("Josejosee");
        driver.findElement(By.id("j_idt20:confirmPassword")).sendKeys("Josejose");
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Mot de passe différent, veuillez recommencer !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
    }
    
    @Test
    public void testRegistrationLoginMiss() {    
        driver.findElement(By.id("j_idt20:login")).clear();
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Veuillez renseigner un login !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
    }
    
    @Test
    public void testRegistrationInvalidEmail() {    
        driver.findElement(By.id("j_idt20:email")).clear();
        driver.findElement(By.id("j_idt20:email")).sendKeys("Jose@Jose");
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Adresse mail non valide, veuillez recommencer !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
    }
    
    @Test
    public void testRegistrationLastnameMiss() {    
        driver.findElement(By.id("j_idt20:lastname")).clear();
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Veuillez renseigner votre nom !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
    }
    
    @Test
    public void testRegistrationFirstnameMiss() {    
        driver.findElement(By.id("j_idt20:firstname")).clear();
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Veuillez renseigner votre prénom !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
    } 
    
    @Test
    public void testRegistrationPostalMiss() {    
        driver.findElement(By.id("j_idt20:code")).clear();
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Veuillez renseigner votre code postal !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
    } 
    
    @Test
    public void testRegistrationTownMiss() {    
        driver.findElement(By.id("j_idt20:town")).clear();
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Veuillez renseigner votre ville !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
    } 
    
    @Test
    public void testRegistrationCountryMiss() {    
        driver.findElement(By.id("j_idt20:country")).clear();
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Veuillez renseigner votre pays !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
    }
    
    /*@Test
    public void testRegistrationCompanyNameMiss() { 
        driver.findElement(By.xpath("//div[@class = 'ui-helper-hidden-accessible']")).click();
        driver.findElement(By.id("j_idt20:radios:1")).click();
        driver.findElement(By.id("j_idt20:companyName")).clear();
        driver.findElement(By.name("j_idt20:register")).click();
        String str = "Veuillez renseigner votre nom de prestataire !" ; 
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(),str); 
        driver.findElement(By.id("j_idt20:j_idt21:0")).click();
    }*/
}
