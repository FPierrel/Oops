package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class FichePrestataireBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public EntityManager getEntityManager() { 
        return this.em; 
    }

    public List<Prestataire> getPrestatairePrenomNom(String prenom, String nom) {
        Query query = this.getEntityManager().createNamedQuery("Prestataire.findNomPrenom");
        query.setParameter("nom", nom);
        query.setParameter("prenom", prenom);
        List<Prestataire> p = query.getResultList();
        return p;
    }

    public List<Prestataire> getPrestataireLogin(String login) {
        Query query = this.getEntityManager().createNamedQuery("Prestataire.findLogin");
        query.setParameter("login", login);
        List<Prestataire> p = query.getResultList();
        return p;
    }
}
