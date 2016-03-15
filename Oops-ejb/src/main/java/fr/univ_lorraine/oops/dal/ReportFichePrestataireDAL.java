package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import fr.univ_lorraine.oops.library.model.ReportPhoto;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ReportFichePrestataireDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Add a ReportFichePrestataire in the database
     * @param r ReportFichePrestataire to add
     * @return the ReportFichePrestataire added
     */
    public ReportFichePrestataire add(ReportFichePrestataire r) {
        em.persist(r);
        return r;
    }

    /**
     * Get a ReportFichePrestataire from its id from the database
     * @param id id of the ReportFichePrestataire to return
     * @return a ReportFichePrestataire matching the id if it exists or null
     */
    public ReportFichePrestataire get(Long id) {
        return em.find(ReportFichePrestataire.class, id);
    }

    /**
     * Get all ReportFichePrestataire from the database
     * @return a List of ReportFichePrestataire
     */
    public List<ReportFichePrestataire> getAll() {
        String query = "SELECT r FROM ReportFichePrestataire r";
        return em.createQuery(query).getResultList();
    }

    /**
     * Get all ReportFichePrestataire not yet verified by an admin
     * @return a List of ReportFichePrestataire
     */
    public List<ReportFichePrestataire> getAllUnverifiedFiche() {
        String query = "SELECT r "
                + "FROM ReportFichePrestataire r "
                + "WHERE  r.moderated='" + false + "'";
        return em.createQuery(query, ReportFichePrestataire.class).getResultList();
    }

    /**
     * Get all ReportPhoto not yet verified by an admin
     * @return a List of ReportPhoto
     */
    public List<ReportPhoto> getAllUnverifiedPhoto() {
        String query = "SELECT r "
                + "FROM ReportPhoto r "
                + "WHERE  r.moderated='" + false + "'";
        return em.createQuery(query, ReportPhoto.class).getResultList();
    }

    /**
     * Update a ReportFichePrestataire in the database
     * @param r ReportFichePrestataire to update
     * @return the ReportFichePrestataire updated
     */
    public ReportFichePrestataire update(ReportFichePrestataire r) {
        return em.merge(r);
    }
}
