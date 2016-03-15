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

    /**
     * Add a Report to the database
     * @param r Report to add
     * @return the Report added
     */
    public Report add(Report r) {
        em.persist(r);
        return r;
    }

    /**
     * Get the Report with the id in parameter
     * @param id id of the Report to return
     * @return the Report with the id in parameter if it exists or null
     */
    public Report get(Long id) {
        return em.find(Report.class, id);
    }

    /**
     * Get all Report from the database
     * @return a List of Report
     */
    public List<Report> getAll() {
        String query = "SELECT r FROM Report r";
        return em.createQuery(query).getResultList();
    }

    /**
     * Get all Report not yet verified by an admin
     * @return a List of Report
     */
    public List<Report> getAllUnverified() {
        String query = "SELECT r "
                + "FROM Report r "
                + "WHERE  r.moderated='" + false + "'";
        return em.createQuery(query, Report.class).getResultList();
    }

    /**
     * Update a Report in the database
     * @param r the Report to update
     * @return the updated Report
     */
    public Report update(Report r) {
        return em.merge(r);
    }
}
