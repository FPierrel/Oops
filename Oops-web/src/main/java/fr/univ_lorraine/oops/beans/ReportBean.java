package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.BanishmentBean;
import fr.univ_lorraine.oops.ejb.ReportManagerBean;
import fr.univ_lorraine.oops.library.model.Photo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Named(value = "reportBean")
@ViewScoped
public class ReportBean implements Serializable {

    @Inject
    ReportManagerBean report;

    @Inject
    BanishmentBean ban;

    private String page;
    private List<String> reasons = new ArrayList<>();
    private List<String> photosReasons = new ArrayList<>();
    private String reason;
    private String complement;
    private Photo photo;
    private boolean options;

    public ReportBean() {
    }

    public void reportFiche() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        report.reportFichePrestataire(request.getRemoteUser(), page, reason, complement);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Votre signalement sera bientôt traité par un administrateur !", null);
        context.addMessage(null, message);
    }
    
    public void reportPhoto() {
        options = false;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        report.reportPhoto(request.getRemoteUser(), photo, reason, complement);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Votre signalement sera bientôt traité par un administrateur !", null);
        context.addMessage(null, message);
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<String> getReasons() {
        return report.getReasons();
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reasonChosen) {
        this.reason = reasonChosen;
    }

    public List<String> getPhotosReasons() {
        return report.getPhotosReasons();
    }

    public void setPhotosReasons(List<String> photosReasons) {
        this.photosReasons = photosReasons;
    }    

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        options = true;
        this.photo = photo;
    }
        
    public void banishPrestataire() {
        ban.banishUser(page);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Le bannissement de ce prestataire a été effectué !", null);
        context.addMessage(null, message);
    }
   
    public void redeemPrestataire() {
        ban.redeemUser(page);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Le bannissement de ce prestataire a été annulé !", null);
        context.addMessage(null, message);
    }
    
    public boolean isBanished() {
        return ban.isUserBanished(page);
    }

    public boolean isOptions() {
        return options;
    }

    public void setOptions(boolean options) {
        this.options = options;
    }
    
    
}
