package dal;

import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
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

    public ReportFichePrestataire add(ReportFichePrestataire r) {
        em.persist(r);
        return r;
    }

    public ReportFichePrestataire get(Long id) {
        return em.find(ReportFichePrestataire.class, id);
    }

    public List<ReportFichePrestataire> getAll() {
        String query = "SELECT r FROM ReportFichePrestataire r";
        return em.createQuery(query).getResultList();
    }

    public List<ReportFichePrestataire> getAllUnverified() {
        String query = "SELECT r "
                + "FROM ReportFichePrestataire r "
                + "WHERE  r.moderated='" + false + "'";
        return em.createQuery(query, ReportFichePrestataire.class).getResultList();
    }

    public ReportFichePrestataire update(ReportFichePrestataire r) {
        return em.merge(r);
    }
}
