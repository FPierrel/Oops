package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Album;
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
    
    public Photo add(Photo p){
        em.persist(p);
        return p;
    }
    
    public Photo get(Long id){
        return em.find(Photo.class, id);
    }
    
    public List<Photo> getAll(){
        String query = "SELECT p FROM Photo p";
        return em.createQuery(query).getResultList();
    }
    
    public Photo update(Photo p){
        return em.merge(p);
    }  
    
    public void delete(Photo p){
        em.remove(p);
    }
    
}
