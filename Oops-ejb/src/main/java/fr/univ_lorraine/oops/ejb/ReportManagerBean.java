package fr.univ_lorraine.oops.ejb;

import dal.PrestataireDAL;
import fr.univ_lorraine.oops.library.model.Photo;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import fr.univ_lorraine.oops.library.model.ReportPhoto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;

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
    
    public void reportPhoto(String loginReporting, Photo photo, String reason, String complement) {
        String loginFicheReported = "noupi";
        Prestataire pres = pd.get(loginFicheReported);
        ReportPhoto report = new ReportPhoto();
        report.setReason(reason);
        report.setUserReported(loginFicheReported);
        report.setUserReporting(loginReporting);
        report.setReportingDate(new Date());
        report.setComplement(complement);
        report.setPhoto(photo);
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
