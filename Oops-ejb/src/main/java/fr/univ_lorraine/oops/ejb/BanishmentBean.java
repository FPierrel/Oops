package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.UtilisateurDAL;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;

@Stateless
@LocalBean
public class BanishmentBean {

    @Inject
    UtilisateurDAL ud;

    /**
     * Méthode donnant la liste des utilisateurs bannis.
     *
     * @return la liste des utilisateurs bannis.
     */
    public List<Utilisateur> getBanishedUser() {
        return ud.getBanishedUser();
    }

    /**
     * Méthode vérifiant si un utilisateur est banni ou non.
     *
     * @param login : login de l'utilisateur.
     * @return true si l'utilisateur est banni, false sinon.
     */
    public boolean isUserBanished(String login) {
        return ud.get(login).isBanished();
    }

    /**
     * Méthode permettant de gracier un utilisateur.
     *
     * @param login : login de l'utilisateur à gracier.
     */
    public void redeemUser(String login) {
        Utilisateur u = ud.get(login);
        u.setBanished(false);
        u.setAdminWarnings(0);
        ud.update(u);
    }

    /**
     * Méthode permettant de bannir un utilisateur.
     *
     * @param login : login de l'utilisateur à bannir.
     */
    public void banishUser(String login) {
        Utilisateur u = ud.get(login);
        u.setBanished(true);
        ud.update(u);
    }
}
