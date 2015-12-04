package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserManagerBean {
    
    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public Utilisateur registerUser(Utilisateur u) {
        //Utilisateur user;
        //if(u instanceof Prestataire) {
           Utilisateur user = this.em.find(Prestataire.class, u.getLogin());
        /*} else {
            user = this.em.find(Soumissionnaire.class, u.getLogin());
        }*/
        if(user == null) {
            this.em.persist(u);
            return u;
        }
        return null;
    }
}
