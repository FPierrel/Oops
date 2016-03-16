package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.PrestataireDAL;
import fr.univ_lorraine.oops.library.model.Album;
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
            
    /**
     * Create a new ReportFichePrestataire and add it to the reported Prestataire
     * @param loginReporting the login of the user creating the report
     * @param loginFicheReported the login of the user being reported
     * @param reason the reason of the report
     * @param complement the complementary reason of the report 
     */
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
    
    /**
     * Create a new ReportPhoto and add it to the reported Prestataire
     * @param loginReporting the login of the user creating the report
     * @param photo the photo being reported
     * @param album the album containing the Photo reported
     * @param reason the reason of the report
     * @param complement the complementary reason of the report 
     */
    public void reportPhoto(String loginReporting, Photo photo, Album album, String reason, String complement) {
        String loginFicheReported = loginReporting;
        Prestataire pres = pd.get(loginFicheReported);
        ReportPhoto report = new ReportPhoto();
        report.setReason(reason);
        report.setUserReported(loginFicheReported);
        report.setUserReporting(loginReporting);
        report.setReportingDate(new Date());
        report.setComplement(complement);
        report.setPhoto(photo);
        report.setAlbum(album);
        pres.addReport(report);
        pd.update(pres);
    }

    /**
     * Getter of the List of String reasons
     * @return the List of String reasons
     */
    public List<String> getReasons() {
        ArrayList<String> reasons = new ArrayList<>();
        reasons.add("Informations incorrectes");
        reasons.add("Usurpation d'indentité");
        reasons.add("Autre");
        return reasons;
    }
    
    /**
     * Getter of the List of String photosReasons
     * @return the List of String photosReasons
     */
    public List<String> getPhotosReasons() {
        ArrayList<String> reasons = new ArrayList<>();
        reasons.add("Image choquante");
        reasons.add("Usurpation d'indentité");
        reasons.add("Autre");
        return reasons;
    }
}
