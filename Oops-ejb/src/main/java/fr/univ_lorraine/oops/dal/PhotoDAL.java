package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Photo;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class PhotoDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    /**
     * Add a Photo to the database
     * @param p the Photo to add
     * @return the Photo added
     */
    public Photo add(Photo p){
        em.persist(p);
        return p;
    }
    
    /**
     * Get the Photo with id in parameter
     * @param id the id of the Photo to return
     * @return the Photo with id in parameter if it exists or null
     */
    public Photo get(Long id){
        return em.find(Photo.class, id);
    }
    
    /**
     * Get all Photos in the database
     * @return a List of Photo
     */
    public List<Photo> getAll(){
        String query = "SELECT p FROM Photo p";
        return em.createQuery(query).getResultList();
    }
    
    /**
     * Update a Photo in the database
     * @param p the Photo to update
     * @return the updated Photo
     */
    public Photo update(Photo p){
        return em.merge(p);
    }  
    
    /**
     * Delete a Photo from the database
     * @param p the Photo to delete
     */
    public void delete(Photo p){
        em.remove(p);
    }
    
}
