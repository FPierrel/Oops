package fr.univ_lorraine.oops.ejb;

import dal.UtilisateurDAL;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class BanishmentBean {

    @Inject
    UtilisateurDAL ud;
    
    public List<Utilisateur> getBanishedUser() {
        return ud.getBanishedUser();
    }

    public boolean isUserBanished(String login) {
        return ud.get(login).isBanished();
    }

    public void redeemUser(String login) {
        Utilisateur u = ud.get(login);        
        u.setBanished(false);
        u.setAdminWarnings(0);
        ud.update(u);
    }

    public void banishUser(String login) {
        Utilisateur u = ud.get(login);
        u.setBanished(true);
        ud.update(u);
    }
}
