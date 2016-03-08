package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Report;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class ReportManagerBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void reportFichePrestataire(String loginReporting, String loginFicheReported, String reason, String complement) {
        Prestataire pres = this.getEntityManager().find(Prestataire.class, loginFicheReported);
        ReportFichePrestataire report = new ReportFichePrestataire();
        report.setReason(reason);
        report.setUserReported(loginFicheReported);
        report.setUserReporting(loginReporting);
        report.setReportingDate(new Date());
        report.setComplement(complement);
        pres.addReport(report);
        this.getEntityManager().merge(pres);
    }

    public List<String> getReasons() {
        ArrayList<String> reasons = new ArrayList<>();
        reasons.add("Informations incorrectes");
        reasons.add("Usurpation d'indentité");
        reasons.add("Autre");
        return reasons;
    }
}
