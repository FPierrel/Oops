package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.AdminUtilsBean;
import fr.univ_lorraine.oops.ejb.AlbumEntityManager;
import fr.univ_lorraine.oops.ejb.BanishmentBean;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Photo;
import fr.univ_lorraine.oops.library.model.Report;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import fr.univ_lorraine.oops.library.model.ReportPhoto;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "adminBean")
@ViewScoped
public class AdminBean implements Serializable {

    private List<Avis> listeAvisNonVerifies;
    private List<ReportFichePrestataire> listUnverifiedReportFiche;
    private List<ReportPhoto> listUnverifiedReportPhoto;
    private List<Utilisateur> listBanishedUser;

    @Inject
    private AdminUtilsBean admin;

    @Inject
    BanishmentBean ban;

    @Inject
    AlbumEntityManager aEM;

    public AdminBean() {
        this.listeAvisNonVerifies = new ArrayList<>();
        this.listUnverifiedReportFiche = new ArrayList<>();
        this.listUnverifiedReportPhoto = new ArrayList<>();
        this.listBanishedUser = new ArrayList<>();
    }

    public String checkAvis(Avis avis) {
        avis.setModerated(true);
        this.admin.updateAvis(avis);
        return "/admin/index.xhtml";
    }

    public String removeAvis(Avis avis) {
        this.admin.removeAvis(avis);
        return "/admin/index.xhtml";
    }

    public List<Avis> getListeAvisNonVerifies() {
        this.listeAvisNonVerifies = this.admin.getAvisNotVerified();
        return listeAvisNonVerifies;
    }

    public void setListeAvisNonVerifies(List<Avis> listeAvisNonVerifies) {
        this.listeAvisNonVerifies = listeAvisNonVerifies;
    }

    public void dismissReport(Report report) {
        this.admin.dismissReport(report);
    }

    public void acceptReport(Report report) {
        this.admin.acceptReport(report);
    }

    public void acceptReportPhoto(ReportPhoto report) {
        Photo p = report.getPhoto();
        report.setPhoto(null);
        this.admin.acceptReport(report);
        this.aEM.deletePhoto(report.getAlbum(), p);
    }

    public List<ReportFichePrestataire> getListUnverifiedReportFiche() {
        this.listUnverifiedReportFiche = this.admin.getUnverifiedReportFiche();
        return listUnverifiedReportFiche;
    }

    public void setListUnverifiedReportFiche(List<ReportFichePrestataire> listUnverifiedReportFiche) {
        this.listUnverifiedReportFiche = listUnverifiedReportFiche;
    }

    public List<ReportPhoto> getListUnverifiedReportPhoto() {
        this.listUnverifiedReportPhoto = this.admin.getUnverifiedReportPhoto();
        return listUnverifiedReportPhoto;
    }

    public void setListUnverifiedReportPhoto(List<ReportPhoto> listUnverifiedReportPhoto) {
        this.listUnverifiedReportPhoto = listUnverifiedReportPhoto;
    }

    public String seeFiche(String login) {
        return "/fiche.xhtml?page=" + login + "&faces-redirect=true";
    }

    public void redeemPrestataire(String login) {
        ban.redeemUser(login);
    }

    public List<Utilisateur> getListBanishedUser() {
        return ban.getBanishedUser();
    }

    public void setListBanishedUser(List<Utilisateur> listBanishedUser) {
        this.listBanishedUser = listBanishedUser;
    }

}
