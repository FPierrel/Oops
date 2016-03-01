/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;
 
import fr.univ_lorraine.oops.library.model.Album;
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
         Album album =this.getEntityManager().find(Album.class, idAlbum);
         photo.setAlbum(album);
         album.getPhotos().add(photo);
         this.getEntityManager().persist(photo); 
         return photo;
     }
     
     public List<Album> getAllAlbumsUser(String login){
         return ((Prestataire)userEM.searchByLogin(login)).getAlbums();
     }
     
     public  List<Photo> getAllPhotoAlbum(Long idAlbum){
         return this.getEntityManager().createNamedQuery("findPhotoByPKalbum").setParameter("idAlbum", idAlbum).getResultList();
     }
     
     public void deleteAlbum(Long idAlbum){
  
          Album album =this.getEntityManager().find(Album.class, idAlbum);
        
         this.getEntityManager().remove(album);
         
     }
}

