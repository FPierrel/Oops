package fr.univ_lorraine.oops.ejb;

import dal.UtilisateurDAL;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserManagerBean {

    @Inject
    LuceneBean luceneBean;

    @Inject
    UtilisateurDAL ud;

    /*  @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
     private EntityManager em;



     public EntityManager getEntityManager() {
     return this.em;
     }
    
     public LuceneBean getLuceneBean(){
     return this.luceneBean;
     }*/
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

    public Utilisateur searchByLogin(String login) {
        return ud.get(login);
    }

    public void updateUser(Utilisateur user) {
        ud.update(user);
    }

}
     
