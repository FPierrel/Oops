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

    /**
     * Méthode permettant d'ajouter un album.
     *
     * @param a : album à ajouter.
     * @return l'album ajouté.
     */
    public Album add(Album a) {
        em.persist(a);
        return a;
    }

    /**
     * Méthode donnant un album à partir de son identifiant.
     *
     * @param id : identifiant de l'album voulu.
     * @return album voulu.
     */
    public Album get(Long id) {
        return em.find(Album.class, id);
    }

    /**
     * Méthode donnant tous les albums.
     *
     * @return liste de tous les albums.
     */
    public List<Album> getAll() {
        String query = "SELECT a FROM Soumissionnaire a";
        return em.createQuery(query).getResultList();
    }

    /**
     * Méthode permettant de mettre à jour un album.
     *
     * @param a : album à mettre à jour.
     * @return album mis à jour.
     */
    public Album update(Album a) {
        return em.merge(a);
    }

    /**
     * Méthode permettant de supprimmer un album.
     *
     * @param a : album à supprimer.
     */
    public void delete(Album a) {
        em.remove(a);
    }

    /**
     * Méthode donnant toutes les photos d'un album.
     *
     * @param id : identifiant de l'album.
     * @return la liste des photos de l'album.
     */
    public List<Photo> photosOfAlbum(Long id) {
        Album a = em.find(Album.class, id);
        return (List<Photo>) a.getPhotos();
    }
}
