package fr.univ_lorraine.oops.ejb;
 
import dal.AlbumDAL;
import dal.PhotoDAL;
import dal.PrestataireDAL;
import dal.UtilisateurDAL;
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
 
@Stateless
@LocalBean
public class AlbumEntityManager {

    @Inject
    PrestataireDAL presd;
      
    @Inject
    AlbumDAL ad;
    
    @Inject
    PhotoDAL pd;
    
    public Album addAlbum(Album album,String login){       
        Prestataire pres = presd.get(login);
        pres.addAlbum(album);
        presd.update(pres);
        return album;
    }
    
     public Photo addPhoto(Photo photo,Long idAlbum){ 
         Album album = ad.get(idAlbum);
         album.addPhoto(photo);
         ad.update(album);
         return photo;
     }
     
     public List<Album> getAllAlbumsUser(String login){
         return presd.get(login).getAlbums();
     }
     
     public  List<Photo> getAllPhotoAlbum(Long idAlbum){
         return ad.photosOfAlbum(idAlbum);
         
     }
     
     public Album getAlbum(Long id){
          return ad.get(id);
     }
     
     public void deleteAlbum(Long idAlbum){
         Album a = ad.get(idAlbum);
         ad.delete(a);
     }

    public void deleteAlbum(Album album, String login) {
        Prestataire pres = presd.get(login);        
        pres.deleteAlbum(album);
        presd.update(pres);
        ad.delete(album);
    }

    public Album getDefault(String login) {
        Prestataire pres = presd.get(login);        
        return pres.getAlbums().size() == 0 ? new Album() : pres.getAlbums().get(0);
    }

    public Album getAlbum(long idAlbum, String login) {
        Prestataire pres = presd.get(login);        
        for(Album b : pres.getAlbums()){
            if(b.getId() == idAlbum){
                return b;
            }
        }
        
        return new Album();
    }

    public void deletePhoto(Album album, long id) {
        Photo p = pd.get(id);
        album.deletePhoto(p);
        ad.update(album);
        pd.delete(p);
    }
}

