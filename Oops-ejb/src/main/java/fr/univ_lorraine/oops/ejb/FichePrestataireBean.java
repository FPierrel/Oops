package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class FichePrestataireBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public EntityManager getEntityManager() { 
        return this.em; 
    }

    public Prestataire getPrestataireLogin(String login) {
        Prestataire prestataire = this.getEntityManager().find(Prestataire.class, login);
        return prestataire;
    }
}
