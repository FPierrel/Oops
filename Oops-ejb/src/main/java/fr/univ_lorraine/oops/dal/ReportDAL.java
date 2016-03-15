package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Report;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ReportDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Report add(Report r) {
        em.persist(r);
        return r;
    }

    public Report get(Long id) {
        return em.find(Report.class, id);
    }

    public List<Report> getAll() {
        String query = "SELECT r FROM Report r";
        return em.createQuery(query).getResultList();
    }

    public List<Report> getAllUnverified() {
        String query = "SELECT r "
                + "FROM Report r "
                + "WHERE  r.moderated='" + false + "'";
        return em.createQuery(query, Report.class).getResultList();
    }

    public Report update(Report r) {
        return em.merge(r);
    }
}
