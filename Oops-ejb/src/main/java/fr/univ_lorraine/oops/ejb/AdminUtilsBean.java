package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.AvisDAL;
import fr.univ_lorraine.oops.dal.ReportDAL;
import fr.univ_lorraine.oops.dal.ReportFichePrestataireDAL;
import fr.univ_lorraine.oops.dal.UtilisateurDAL;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Report;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import fr.univ_lorraine.oops.library.model.ReportPhoto;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class AdminUtilsBean {

    @Inject
    UtilisateurDAL ud;

    @Inject
    AvisDAL ad;

    @Inject
    ReportDAL rd;

    @Inject
    ReportFichePrestataireDAL rfd;

    public void removeAvis(Avis avis) {
        Prestataire p = (Prestataire) ud.get(avis.getLoginPrestaire());
        p.removeAvis(avis);

        ud.update(p);
        ad.delete(avis);
        p.recalculateMarks();
    }

    public void dismissReport(Report report) {
        report.setModerated(true);
        rd.update(report);
    }

    public void acceptReport(Report report) {
        report.setModerated(true);
        report.setJustified(true);
        Utilisateur u = ud.get(report.getUserReported());
        int nbWarnings = u.getAdminWarnings();
        nbWarnings++;
        u.setAdminWarnings(nbWarnings);
        if (nbWarnings >= 3) {
            u.setBanished(true);
        }
        rd.update(report);
        ud.update(u);
    }

    public List<ReportFichePrestataire> getUnverifiedReportFiche() {
        return rfd.getAllUnverifiedFiche();
    }
    
    public List<ReportPhoto> getUnverifiedReportPhoto() {
        return rfd.getAllUnverifiedPhoto();
    }
    
    public List<Avis> getAvisNotVerified(){
        return ad.getAllUnverified();
}
    
    public Avis updateAvis(Avis a){
        return ad.update(a);
    }
}
