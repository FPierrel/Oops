package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class SearchResultsBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    private String queryString;
    
    public EntityManager getEntityManager() { 
        return em; 
    }
    
    public List<Prestataire> search(String lastname) {
        queryString = "SELECT p "
                    + "FROM Prestataire p "
                    + "WHERE p.nom = '" + lastname + "'";
        Query query = em.createQuery(queryString, Prestataire.class);
        System.out.println("#############################"+query.getResultList().size()+"#############################");
        return query.getResultList();
    }
    
}
