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
public class AlbumDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public Album add(Album a){
        em.persist(a);
        return a;
    }
    
    public Album get(Long id){
        return em.find(Album.class, id);
    }
    
    public List<Album> getAll(){
        String query = "SELECT a FROM Soumissionnaire a";
        return em.createQuery(query).getResultList();
    }
    
    public Album update(Album a){
        return em.merge(a);
    }  
    
    public void delete(Album a){
        em.remove(a);
    }
    
    public List<Photo> photosOfAlbum(Long id){
        Album a = em.find(Album.class, id);
        return (List<Photo>) a.getPhotos();
}
}
