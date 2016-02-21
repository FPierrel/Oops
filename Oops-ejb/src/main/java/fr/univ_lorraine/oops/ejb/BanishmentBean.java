/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Romain
 */
@Stateless
@LocalBean
public class BanishmentBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public List<Utilisateur> getBanishedUser() {
        String queryString = "SELECT u "
                + "FROM Utilisateur u "
                + "WHERE  u.banished='" + true + "'";
        TypedQuery<Utilisateur> query = this.getEntityManager().createQuery(queryString, Utilisateur.class);
        return query.getResultList();
    }

    public boolean isUserBanished(String login) {
        Utilisateur u = (Utilisateur) this.getEntityManager().find(Utilisateur.class, login);
        return u.isBanished();
    }

    public void redeemUser(String login) {
        Utilisateur u = (Utilisateur) this.getEntityManager().find(Utilisateur.class, login);
        u.setBanished(false);
        u.setAdminWarnings(0);
        this.getEntityManager().merge(u);
    }

    public void banishUser(String login) {
        Utilisateur u = (Utilisateur) this.getEntityManager().find(Utilisateur.class, login);
        u.setBanished(true);
        this.getEntityManager().merge(u);
    }
}
