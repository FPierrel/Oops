package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Commentaire;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class CommentaireDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Méthode permettant d'ajouter un commentaire.
     *
     * @param c : commentaire à ajouter.
     * @return le commentaire ajouté.
     */
    public Commentaire add(Commentaire c) {
        em.persist(c);
        return c;
    }

    /**
     * Méthode donnant un commentaire à partie de son identifiant.
     *
     * @param id : identifiant du commentaire voulu.
     * @return le commentaire voulu.
     */
    public Commentaire get(Long id) {
        return em.find(Commentaire.class, id);
    }

    /**
     * Méthode donnant la liste de tous les commentaires.
     *
     * @return la liste de tous les commentaires.
     */
    public List<Commentaire> getAll() {
        String query = "SELECT c FROM Commentaire c";
        return em.createQuery(query).getResultList();
    }

    /**
     * Méthode permettant de mettre à jour un commentaire.
     *
     * @param c : commentaire à mettre à jour.
     * @return le commentaire mis à jour.
     */
    public Commentaire update(Commentaire c) {
        return em.merge(c);
    }

    /**
     * Méthode permettant de supprimmer un commentaire.
     *
     * @param c : commentaire à supprimmer.
     */
    public void delete(Commentaire c) {
        em.remove(em.find(Commentaire.class, c.getId()));

    }
}
