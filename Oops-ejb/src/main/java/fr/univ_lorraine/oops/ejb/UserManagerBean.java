package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.UtilisateurDAL;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;

@Stateless
@LocalBean
public class UserManagerBean {

    @Inject
    LuceneBean luceneBean;

    @Inject
    UtilisateurDAL ud;

    /**
     * Add a new Utilisateur 
     * @param u the new Utilisateur
     * @return the new Utilisateur if the Utilisateur doesn't already exists, else null
     */
    public Utilisateur registerUser(Utilisateur u) {
        Utilisateur user = ud.get(u.getLogin());
        if (user == null) {
            ud.add(u);
            if (u instanceof Prestataire) {
                luceneBean.indexPrestataire(((Prestataire) u));
            }
            return u;
        }
        return null;
    }

    /**
     * Return the Utilisateur with the login "login"
     * @param login the login of the Utilisateur to return
     * @return the Utilisateur with the login in parameter
     */
    public Utilisateur searchByLogin(String login) {
        return ud.get(login);
    }

    /**
     * Update a Utilisateur
     * @param user the Utilisateur to update
     */
    public void updateUser(Utilisateur user) {
        ud.update(user);
    }
    
    /**
     * Return a List of Prestataire containing the four Prestataire with the highest grades
     * @return a List of Prestataire containing the four Prestataire with the highest grades
     */
    public List<Prestataire> getBestGrades() {
        return ud.getBestGrades(4);
    }

}
     
