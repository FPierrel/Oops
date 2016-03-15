package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.AlbumDAL;
import fr.univ_lorraine.oops.dal.PhotoDAL;
import fr.univ_lorraine.oops.dal.PrestataireDAL;
import fr.univ_lorraine.oops.library.model.Album;
import fr.univ_lorraine.oops.library.model.Photo;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;

@Stateless
@LocalBean
public class AlbumEntityManager {

    @Inject
    PrestataireDAL presd;

    @Inject
    AlbumDAL ad;

    @Inject
    PhotoDAL pd;

    /**
     * Méthode permettant à un prestataire d'ajouter un album.
     *
     * @param album : album à ajouter.
     * @param login : login du prestataire.
     * @return l'album ajouté.
     */
    public Album addAlbum(Album album, String login) {
        Prestataire pres = presd.get(login);
        pres.addAlbum(album);
        presd.update(pres);
        return album;
    }

    /**
     * Méthode permettant d'ajouter une photo à un album.
     *
     * @param photo : photo à ajouter.
     * @param idAlbum : identifiant de l'album.
     * @return la photo ajoutée.
     */
    public Photo addPhoto(Photo photo, Long idAlbum) {
        Album album = ad.get(idAlbum);
        album.addPhoto(photo);
        ad.update(album);
        return photo;
    }

    /**
     * Méthode donnant tous les albums d'un prestataire.
     *
     * @param login : login du prestataire.
     * @return la liste des albums du prestataire.
     */
    public List<Album> getAllAlbumsUser(String login) {
        return presd.get(login).getAlbums();
    }

    /**
     * Méthode donnant toutes les photos d'un album.
     *
     * @param idAlbum : identifiant de l'album.
     * @return la liste des photos de l'album.
     */
    public List<Photo> getAllPhotoAlbum(Long idAlbum) {
        return ad.photosOfAlbum(idAlbum);
    }

    /**
     * Méthode donnat l'album grâce à son identifiant.
     *
     * @param id : identifiant de l'album voulu.
     * @return l'album voulu.
     */
    public Album getAlbum(Long id) {
        return ad.get(id);
    }

    /**
     * Méthode permettant de supprimer un album à partir de son identifiant.
     *
     * @param idAlbum : identifiant de l'album à supprimmer.
     */
    public void deleteAlbum(Long idAlbum) {
        Album a = ad.get(idAlbum);
        ad.delete(a);
    }

    /**
     * Méthode permettant de supprimmer un album.
     *
     * @param album : album à supprimmer.
     * @param login : login du prestataire qui possède l'album.
     */
    public void deleteAlbum(Album album, String login) {
        Prestataire pres = presd.get(login);

        Album a = ad.get(album.getId());

        if (pres.getProfilePicture() != null) {
            Photo p = pd.get(pres.getProfilePicture().getId());
            if (a.getPhotos().contains(p)) {
                pres.setProfilePicture(null);
            }
        }

        pres.deleteAlbum(a);
        presd.update(pres);
        ad.delete(a);
    }

    /**
     * Méthode donnant l'album défaut d'un prestataire.
     *
     * @param login : login du prestataire.
     * @return l'album defaut du prestataire.
     */
    public Album getDefault(String login) {
        Prestataire pres = presd.get(login);
        return pres.getAlbums().size() == 0 ? null : pres.getAlbums().get(0);
    }

    /**
     * Méthode donnant l'album à partir de son identifiant et le login du
     * prestataire.
     *
     * @param idAlbum : identifiant de l'album.
     * @param login : login du prestataire.
     * @return album demandé.
     */
    public Album getAlbum(long idAlbum, String login) {
        Prestataire pres = presd.get(login);
        for (Album b : pres.getAlbums()) {
            if (b.getId() == idAlbum) {
                return b;
            }
        }

        return new Album();
    }

    /**
     * Méthode permettant de supprimmer une photo à partir de son identifiant.
     *
     * @param album : album qui contient la photo à supprimmer.
     * @param id : identifiant de la photo à supprimmer.
     */
    public void deletePhoto(Album album, long id) {
        Photo p = pd.get(id);
        Prestataire pres = presd.get(album.getLogin());

        if (pres.getProfilePicture() != null) {
            if (pres.getProfilePicture().getId() == id) {
                pres.setProfilePicture(null);
            }
        }

        album.deletePhoto(p);
        ad.update(album);
        presd.update(pres);
        pd.delete(p);
    }

    /**
     * Méthode permettant de supprimmer une photo.
     *
     * @param album : album qui contient la photo à supprimmer.
     * @param photoToDelete : photo à supprimmer.
     */
    public void deletePhoto(Album album, Photo photoToDelete) {
        Album a = ad.get(album.getId());
        Photo p = pd.get(photoToDelete.getId());

        a.deletePhoto(p);
        ad.update(a);
        pd.delete(p);
    }

    /**
     * Méthode permettant de mettre à jour la description d'une photo.
     *
     * @param photoToDelete : photo dont il faut mettre à jour la description.
     * @param description : nouvelle descriptiopn de la photo.
     */
    public void updatePhotoDesc(Photo photoToDelete, String description) {
        Photo p = pd.get(photoToDelete.getId());
        p.setDescription(description);
        pd.update(p);
    }

    /**
     * Méthode permettant de désigner une photo comme photo de profil.
     *
     * @param remoteUser : prestataire à qui la photo appartient.
     * @param photoToDelete : photo à mettre en photo de profil.
     */
    public void setProfilePicture(String remoteUser, Photo photoToDelete) {
        Prestataire p = presd.get(remoteUser);
        p.setProfilePicture(photoToDelete);
        presd.update(p);
    }

    /**
     * Méthode permettant de vérifier si un album appartient à un prestataire.
     *
     * @param user : login du prestataire.
     * @param idAlbum : identifiant de l'album.
     * @return true si l'album appartient au prestataire, false sinon.
     */
    public boolean userHasAlbum(String user, long idAlbum) {
        Prestataire p = presd.get(user);

        if (p == null) {
            return false;
        }

        if (p.getAlbums().size() > 0) {
            for (Album a : p.getAlbums()) {
                if (a.getId() == idAlbum) {
                    return true;
                }
            }
        }

        return false;
    }
}
