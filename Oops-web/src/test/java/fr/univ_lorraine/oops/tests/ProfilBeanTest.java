package fr.univ_lorraine.oops.tests;

import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

public class ProfilBeanTest {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private static EntityManager em;
    private static Utilisateur userTest;
    private static WebDriver driver;

    public ProfilBeanTest() {
    }

    public static void createUserTest() {
        ArrayList<Adresse> adressesTest = new ArrayList<>();
        Adresse adresseTest = new Adresse();
        adresseTest.setNumero("1664");
        adresseTest.setRue("rue du test");
        adresseTest.setVille("PARIS");
        adresseTest.setCodePostal("75000");
        adresseTest.setPays("FRANCE");
        adressesTest.add(adresseTest);
        userTest = new Soumissionnaire();
        userTest.setLogin("test1234567890");
        userTest.setAdresses(adressesTest);
        userTest.setMail("test@test.com");
        userTest.setMotDePasse("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
        userTest.setNom("Test");
        userTest.setPrenom("User");
        userTest.setNumeroTelephone("0123456789");
        em.persist(userTest);
    }

    @BeforeClass
    public static void setUpClass() {
        createUserTest();
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        driver = new PhantomJSDriver(capabilities);
        driver.manage().window().setSize(new Dimension(1440, 768));
        driver.get("http://localhost:8080/Oops-web/faces/profil.xhtml");
        driver.findElement(By.id("loginForm:username")).sendKeys("test1234567890");
        driver.findElement(By.id("loginForm:password")).sendKeys("123456");
        driver.findElement(By.id("loginForm:login")).click();
    }

    @AfterClass
    public static void tearDownClass() {
        em.remove(userTest);
        driver.quit();
    }

    @Before
    public void setUp() {
        driver.get("http://localhost:8080/Oops-web/faces/profil.xhtml");
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
                driver.findElement(By.id("profilForm:newMail")).clear();
        driver.findElement(By.id("profilForm:newMailConfirm")).clear();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEditProfilSuccess() {
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Modification(s) enregistrée(s) !";
        assertEquals(driver.findElement(By.className("ui-message-info-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongOldPassword() {
        driver.findElement(By.id("profilForm:oldPassword")).sendKeys("0000");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Mot de passe incorrect !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongNewPassword() {
        driver.findElement(By.id("profilForm:oldPassword")).sendKeys("123456");
        driver.findElement(By.id("profilForm:newPassword")).sendKeys("0000");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Mot de passe trop petit (inférieur à 6), veuillez recommencer !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongNewPasswordConfirm() {
        driver.findElement(By.id("profilForm:oldPassword")).sendKeys("123456");
        driver.findElement(By.id("profilForm:newPassword")).sendKeys("123456789");
        driver.findElement(By.id("profilForm:newPasswordConfirm")).sendKeys("0000");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Confirmation du nouveau mot de passe incorrecte !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongNewMail() {
        driver.findElement(By.id("profilForm:newMail")).sendKeys("a@a");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Adresse mail non valide, veuillez recommencer !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }

    @Test
    public void testEditProfilWrongNewMailConfirm() {
        driver.findElement(By.id("profilForm:newMail")).sendKeys("bonjour@hello.fr");
        driver.findElement(By.id("profilForm:newMailConfirm")).sendKeys("hello@hello.fr");
        driver.findElement(By.id("profilForm:save")).click();
        String message = "Confirmation de la nouvelle adresse mail incorrecte !";
        assertEquals(driver.findElement(By.className("ui-message-error-detail")).getText(), message);
    }
}
