package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.BanishmentBean;
import fr.univ_lorraine.oops.ejb.ReportManagerBean;
import fr.univ_lorraine.oops.library.model.Album;
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
    private Album album;
    private boolean options;

    /**
     * Constructor
     */
    public ReportBean() {
    }

    /**
     * Call the function reportFichePrestataire from the Session Bean ReportManagerBean and set a feedback message 
     */
    public void reportFiche() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        report.reportFichePrestataire(request.getRemoteUser(), page, reason, complement);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Votre signalement sera bientôt traité par un administrateur !", null);
        context.addMessage(null, message);
    }
    
    /**
     * Call the function reportPhoto from the Session Bean ReportManagerBean and set a feedback message
     */
    public void reportPhoto() {
        options = false;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        report.reportPhoto(request.getRemoteUser(), photo, album, reason, complement);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Votre signalement sera bientôt traité par un administrateur !", null);
        context.addMessage(null, message);
    }

    /**
     * Getter of complement
     * @return the String complement
     */
    public String getComplement() {
        return complement;
    }

    /**
     * Setter of complement
     * @param complement
     */
    public void setComplement(String complement) {
        this.complement = complement;
    }

    /**
     * Getter of the String page
     * @return the String page
     */
    public String getPage() {
        return page;
    }

    /**
     * Setter of the String page
     * @param page the new page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Getter of the List of String reasons
     * Call the method getReasons from the Session Bean ReportManagerBean
     * @return the List of String reasons
     */
    public List<String> getReasons() {
        return report.getReasons();
    }

    /**
     * Setter of the List of String reasons
     * @param reasons
     */
    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    /**
     * Getter of the String reason
     * @return the String reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Setter of the String reasonChosen
     * @param reasonChosen the new String
     */
    public void setReason(String reasonChosen) {
        this.reason = reasonChosen;
    }

    /**
     * Getter of the List of String photosReasons
     * Call the method getPhotosReasons from the Session Bean ReportManagerBean
     * @return the List of String photosReasons
     */
    public List<String> getPhotosReasons() {
        return report.getPhotosReasons();
    }

    /**
     * Setter of the List of String photosReasons
     * @param photosReasons the new List of String photoReasons
     */
    public void setPhotosReasons(List<String> photosReasons) {
        this.photosReasons = photosReasons;
    }    

    /**
     * Getter of the Photo photo
     * @return the Photo photo
     */
    public Photo getPhoto() {
        return photo;
    }

    /**
     * Setter of the Photo photo
     * @param photo the new Photo
     * @param al the Album containing the Photo
     */
    public void setPhoto(Photo photo, Album al) {
        options = !options;
        this.photo = photo;
        this.album = al;
    }
        
    /**
     * Call the function banishUser from the Session Bean BanishmentBean with the "page" attribute and set a feedback message 
     */
    public void banishPrestataire() {
        ban.banishUser(page);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Le bannissement de ce prestataire a été effectué !", null);
        context.addMessage(null, message);
    }
   
    /**
     * Call the function redeemUser from the Session Bean BanishmentBean with the "page" attribute and set a feedback message 
     */
    public void redeemPrestataire() {
        ban.redeemUser(page);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Le bannissement de ce prestataire a été annulé !", null);
        context.addMessage(null, message);
    }
    
    /**
     * Call the function isUerBanished from the Session Bean BanishmentBean with the "page"
     * @return the boolean true if the Utilisateur with login "page" is banished, else false
     */
    public boolean isBanished() {
        return ban.isUserBanished(page);
    }

    /**
     * Getter of the boolean options
     * @return the boolean options
     */
    public boolean isOptions() {
        return options;
    }

    /**
     * Setter of the boolean options
     * @param options the new boolean options
     */
    public void setOptions(boolean options) {
        this.options = options;
    }
    
    
}
