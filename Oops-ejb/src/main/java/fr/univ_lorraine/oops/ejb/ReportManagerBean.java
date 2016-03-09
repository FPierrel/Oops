package fr.univ_lorraine.oops.ejb;

import dal.PrestataireDAL;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Report;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class ReportManagerBean {

    @Inject
    PrestataireDAL pd;
            
    public void reportFichePrestataire(String loginReporting, String loginFicheReported, String reason, String complement) {
        Prestataire pres = pd.get(loginFicheReported);
        ReportFichePrestataire report = new ReportFichePrestataire();
        report.setReason(reason);
        report.setUserReported(loginFicheReported);
        report.setUserReporting(loginReporting);
        report.setReportingDate(new Date());
        report.setComplement(complement);
        pres.addReport(report);
        pd.update(pres);
    }

    public List<String> getReasons() {
        ArrayList<String> reasons = new ArrayList<>();
        reasons.add("Informations incorrectes");
        reasons.add("Usurpation d'indentité");
        reasons.add("Autre");
        return reasons;
    }
}
