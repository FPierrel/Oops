/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.tests;

import fr.univ_lorraine.oops.ejb.UserManagerBean;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.xml.registry.infomodel.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author thomas
 */
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
        Mockito.when(em.find(Utilisateur.class, p1.getLogin())).thenReturn(null);

        UserManagerBean umb = Mockito.mock(UserManagerBean.class);
        Mockito.when(umb.registerUser(p1)).thenCallRealMethod();
        Mockito.when(umb.getEntityManager()).thenReturn(em);

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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
