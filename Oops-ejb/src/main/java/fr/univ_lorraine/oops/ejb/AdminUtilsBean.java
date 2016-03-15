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

    /**
     * Méthode permettant de supprimer un avis.
     *
     * @param avis : avis à supprimer.
     */
    public void removeAvis(Avis avis) {
        Prestataire p = (Prestataire) ud.get(avis.getLoginPrestaire());
        p.removeAvis(avis);

        ud.update(p);
        ad.delete(avis);
        p.recalculateMarks();
    }

    /**
     * Méthode permettant de gérer un signalement injustifié.
     *
     * @param report : le signalement géré.
     */
    public void dismissReport(Report report) {
        report.setModerated(true);
        rd.update(report);
    }

    /**
     * Méthode permettant de gérer un signalement justifié et donne un
     * avertissement. Au bout de 3 avertissements, le prestataire est banni.
     *
     * @param report : le signalement géré.
     */
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

    /**
     * Méthode donnant les signalements de fiche non vérifiés.
     *
     * @return la liste des signalements de fiche non vérifiés.
     */
    public List<ReportFichePrestataire> getUnverifiedReportFiche() {
        return rfd.getAllUnverifiedFiche();
    }

    /**
     * Méthode donnant les signalements de photo non vérifiés.
     *
     * @return la liste des signalements de photo non vérifiés.
     */
    public List<ReportPhoto> getUnverifiedReportPhoto() {
        return rfd.getAllUnverifiedPhoto();
    }

    /**
     * Méthode donnant les avis non vérifiés.
     *
     * @return la liste des avis non vérifiés.
     */
    public List<Avis> getAvisNotVerified() {
        return ad.getAllUnverified();
    }

    /**
     * Méthode permettant de mettre à jour un avis.
     *
     * @param a : avis à mettre à jour.
     * @return l'avis mis à jour.
     */
    public Avis updateAvis(Avis a) {
        return ad.update(a);
    }
}
