package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class PrestataireDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    /**
     * Add a Prestataire to the database
     * @param p the Prestataire to add
     * @return the added Prestataire
     */
    public Prestataire add(Prestataire p){
        em.persist(p);
        return p;
    }
    
    /**
     * Get the Prestataire with the login in parameter
     * @param login the login of the Prestataire to return
     * @return the Prestataire with the login in parameter if it exists or null
     */
    public Prestataire get(String login){
        return em.find(Prestataire.class, login);
    }
    
    /**
     * Get all Prestataire from the database
     * @return a List of Prestataire
     */
    public List<Prestataire> getAll(){
        String query = "SELECT p FROM Prestataire p";
        return em.createQuery(query).getResultList();
    }
    
    /**
     * Update a Prestataire in the database
     * @param p the Prestataire to update
     * @return the Prestataire updated
     */
    public Prestataire update(Prestataire p){
        return em.merge(p);
    }  
}
