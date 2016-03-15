package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class SoumissionnaireDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    /**
     * Add a Soumissionnaire to the database
     * @param s Soumissionnaire to add
     * @return the Soumissionnaire added
     */
    public Soumissionnaire add(Soumissionnaire s){
        em.persist(s);
        return s;
    }
    
    /**
     * Get the Soumissionnaire with the login in parameter
     * @param login login of the Soumissionnaire to return
     * @return the Soumissionnaire matching the login if it exists or null
     */
    public Soumissionnaire get(String login){
        return em.find(Soumissionnaire.class, login);
    }
    
    /**
     * Get all Soumissionnaire in the database
     * @return a List of Soumissionnaire
     */
    public List<Soumissionnaire> getAll(){
        String query = "SELECT s FROM Soumissionnaire s";
        return em.createQuery(query).getResultList();
    }
    
    /**
     * Update a Soumissionnaire in the database
     * @param s the Soumissionnaire to update
     * @return tue Soumissionnaire updated
     */
    public Soumissionnaire update(Soumissionnaire s){
        return em.merge(s);
    }  
}
