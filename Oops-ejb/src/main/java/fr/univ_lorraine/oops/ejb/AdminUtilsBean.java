package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Report;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class AdminUtilsBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public List<Avis> getAvisNotVerified() {
        String queryString = "SELECT a "
                + "FROM Avis a "
                + "WHERE  a.moderated='" + false + "'";
        TypedQuery<Avis> query = this.getEntityManager().createQuery(queryString, Avis.class);
        return query.getResultList();
    }

    public void updateAvis(Avis avis) {
        this.getEntityManager().merge(avis);
    }

    public void removeAvis(Avis avis) {
        Prestataire p = (Prestataire) this.getEntityManager().find(Utilisateur.class, avis.getLoginPrestaire());
        p.removeAvis(avis);
        this.getEntityManager().merge(p);
        this.getEntityManager().remove(this.getEntityManager().find(Avis.class, avis.getId()));
        p.recalculateMarks();
    }

    public void updateReport(Report report) {
       this.getEntityManager().merge(report);
    }

    public List<ReportFichePrestataire> getUnverifiedReport() {
         String queryString = "SELECT r "
                + "FROM Report r "
                + "WHERE  r.moderated='" + false + "'";
        TypedQuery<ReportFichePrestataire> query = this.getEntityManager().createQuery(queryString, ReportFichePrestataire.class);
        return query.getResultList();
    }
    
    

}
