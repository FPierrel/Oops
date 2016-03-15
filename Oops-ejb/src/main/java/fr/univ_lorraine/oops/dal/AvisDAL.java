package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Avis;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class AvisDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Méthode permettant d'ajouter un avis.
     *
     * @param a : avis à ajouter.
     * @return l'avis ajouté.
     */
    public Avis add(Avis a) {
        em.persist(a);
        return a;
    }

    /**
     * Méthode donnat un avis à partie de son identifiant.
     *
     * @param id : identifiant de l'avis voulu.
     * @return l'avis voulu.
     */
    public Avis get(Long id) {
        return em.find(Avis.class, id);
    }

    /**
     * Méthode donnant tous les avis.
     *
     * @return la liste de tous les avis.
     */
    public List<Avis> getAll() {
        String query = "SELECT a FROM Avis a";
        return em.createQuery(query).getResultList();
    }

    /**
     * Méthode donnant la liste des avis non vérifiés.
     *
     * @return la liste des avis non vérifiés.
     */
    public List<Avis> getAllUnverified() {
        String queryString = "SELECT a "
                + "FROM Avis a "
                + "WHERE  a.moderated='" + false + "'";
        return em.createQuery(queryString, Avis.class).getResultList();
    }

    /**
     * Méthode permettant de mettre à jour un avis.
     *
     * @param a : avis à mettre à jour.
     * @return l'avis mis à jour.
     */
    public Avis update(Avis a) {
        return em.merge(a);
    }

    /**
     * Méthode permettant de supprimmer un avis.
     *
     * @param a : avis à supprimmé.
     */
    public void delete(Avis a) {
        em.remove(em.find(Avis.class, a.getId()));
    }

    /**
     * Méthode donnant tous les avis sur un prestataire.
     *
     * @param loginPrestataire : login du prestataire.
     * @return la liste des avis concernant le prestataire.
     */
    public List<Avis> getAvisPrestatire(String loginPrestataire) {
        String queryString = "SELECT p.cAvis "
                + "FROM Prestataire p "
                + "WHERE  p.login='" + loginPrestataire + "'";
        return em.createQuery(queryString, Avis.class).getResultList();
    }

    /**
     * Méthode donnant les derniers avis pour la page d'accueil.
     *
     * @param number : nombre voulu d'avis parmi les derniers.
     * @return la liste des derniers avis.
     */
    public List<Avis> getLastAvis(int number) {
        String query = "SELECT a FROM Avis a ORDER BY a.id desc";
        return em.createQuery(query).setMaxResults(number).getResultList();
    }
}
