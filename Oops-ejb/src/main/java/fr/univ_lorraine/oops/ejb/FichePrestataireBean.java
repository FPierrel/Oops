package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
    
    public List<Avis> getPrestataireAvis(String login) {
        String queryString = "SELECT p.cAvis "
                + "FROM Prestataire p "
                + "WHERE  p.login='"+login+"'"
                ;
        TypedQuery<Avis> query = this.getEntityManager().createQuery(queryString, Avis.class);
        return query.getResultList();
    }
    
    public List<String> getPrestataireCategoriesNames(String login){
         String queryString = "SELECT c.nom "
                + "FROM Prestataire p, Categorie c "
                + "WHERE  p.login='"+login+"' AND c MEMBER OF p.categories"
                ;
        TypedQuery<String> query = this.getEntityManager().createQuery(queryString, String.class);
        return query.getResultList();
    }    
}
