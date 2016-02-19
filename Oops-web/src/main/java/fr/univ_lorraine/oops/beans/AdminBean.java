package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.AdminUtilsBean;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Report;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
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
    private List<ReportFichePrestataire> listUnverifiedReport;

    @Inject
    private AdminUtilsBean admin;

    public AdminBean() {
        this.listeAvisNonVerifies = new ArrayList<>();
        this.listUnverifiedReport = new ArrayList<>();
    }

    public String checkAvis(Avis avis) {
        avis.setModerated(true);
        this.admin.updateAvis(avis);
        return "admin/index.xhtml";
    }

    public String removeAvis(Avis avis) {
        this.admin.removeAvis(avis);
        return "admin/index.xhtml";
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

    public List<ReportFichePrestataire> getListUnverifiedReport() {
        this.listUnverifiedReport = this.admin.getUnverifiedReport();
        return listUnverifiedReport;
    }

    public void setListUnverifiedReport(List<ReportFichePrestataire> listUnverifiedReport) {
        this.listUnverifiedReport = listUnverifiedReport;
    }

    public String seeFiche(ReportFichePrestataire report) {
        return "/fiche.xhtml?page=" + report.getPrestataire().getLogin() + "&faces-redirect=true";
    }

}
