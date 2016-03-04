/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;
 
import fr.univ_lorraine.oops.library.model.Album;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Photo;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
/**
 *
 * @author Administrateur
 */
@Stateless
@LocalBean
public class AlbumEntityManager {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
      
    @Inject
    private UserManagerBean userEM;
    
    public EntityManager getEntityManager() {
        return this.em;
    }
    
    public Album addAlbum(Album album,String login){       
        Prestataire user = (Prestataire)userEM.searchByLogin(login);
        user.addAlbum(album);
        this.getEntityManager().merge(user);
        return album;
    }
    
     public Photo addPhoto(Photo photo,Long idAlbum){ 
         Album album = this.getEntityManager().find(Album.class, idAlbum);
         album.addPhoto(photo);
         this.getEntityManager().merge(album); 
         return photo;
     }
     
     public List<Album> getAllAlbumsUser(String login){
         return ((Prestataire)userEM.searchByLogin(login)).getAlbums();
     }
     
     public  List<Photo> getAllPhotoAlbum(Long idAlbum){
         return this.getEntityManager().createNamedQuery("findPhotoByPKalbum").setParameter("idAlbum", idAlbum).getResultList();
     }
     
     public Album getAlbum(Long id){
          return this.getEntityManager().find(Album.class, id);
     }
     
     public void deleteAlbum(Long idAlbum){
  
          Album album =this.getEntityManager().find(Album.class, idAlbum);
        
         this.getEntityManager().remove(album);
         
     }

    public void deleteAlbum(Album album, String login) {
        Prestataire user = (Prestataire)userEM.searchByLogin(login);
        user.deleteAlbum(album);
        this.getEntityManager().merge(user);
        this.getEntityManager().remove(this.getEntityManager().find(Album.class, album.getId()));
    }

    public Album getDefault(String login) {
        Prestataire user = (Prestataire)userEM.searchByLogin(login);
        
        return user.getAlbums().size() == 0 ? new Album() : user.getAlbums().get(0);
    }

    public Album getAlbum(long idAlbum, String login) {
        Prestataire user = (Prestataire)userEM.searchByLogin(login);
        
        for(Album b : user.getAlbums()){
            if(b.getId() == idAlbum){
                return b;
            }
        }
        
        return new Album();
    }

    public void deletePhoto(Album album, long id) {
        Photo p = this.getEntityManager().find(Photo.class, id);
        Album al = this.getEntityManager().find(Album.class, album.getId());
        al.deletePhoto(p);
        this.getEntityManager().merge(al);
        this.getEntityManager().remove(p);
    }
}

