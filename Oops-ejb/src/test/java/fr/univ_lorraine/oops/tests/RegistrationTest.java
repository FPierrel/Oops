package fr.univ_lorraine.oops.tests;

import fr.univ_lorraine.oops.ejb.LuceneBean;
import fr.univ_lorraine.oops.ejb.UserManagerBean;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RegistrationTest {
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public RegistrationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestUserNotInBase() {
        Utilisateur p1 = new Prestataire();
        p1.setLogin("José");

        EntityManager em = Mockito.mock(EntityManager.class);
        LuceneBean lB = Mockito.mock(LuceneBean.class);
        
        Mockito.when(em.find(Utilisateur.class, p1.getLogin())).thenReturn(null);

        UserManagerBean umb = Mockito.mock(UserManagerBean.class);
        
        Mockito.when(umb.registerUser(p1)).thenCallRealMethod();
        Mockito.when(umb.getEntityManager()).thenReturn(em);
        Mockito.when(umb.getLuceneBean()).thenReturn(lB);
        

        Assert.assertSame(umb.registerUser(p1), p1);
    }
    
    @Test
    public void TestUserInBase() {
        Utilisateur p1 = new Prestataire();
        p1.setLogin("José");

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.find(Utilisateur.class, p1.getLogin())).thenReturn(p1);

        UserManagerBean umb = Mockito.mock(UserManagerBean.class);
        Mockito.when(umb.registerUser(p1)).thenCallRealMethod();
        Mockito.when(umb.getEntityManager()).thenReturn(em);

        Assert.assertNull(umb.registerUser(p1));
    }
}
