package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.FichePrestataireBean;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "ficheBean")
@ConversationScoped
public class FicheBean implements Serializable {

    @Inject
    private FichePrestataireBean fiche;
    
    private String page;
    private Prestataire prestataire;

    /**
     * Creates a new instance of FicheBean
     */
    public FicheBean() {

    }

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if(this.page.isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mauvaise requête ! Utilisez un lien correct !", null);
            context.addMessage(null, message); 
        }        
        this.prestataire = this.fiche.getPrestataireLogin(this.page);        
        if(this.prestataire == null && !(this.page.isEmpty())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mauvaise requête ! Utilisez un lien correct !", null);
            context.addMessage(null, message);            
        }
    }
    
    public Prestataire getPrestataire() {
        return this.prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public String getPage() {
        return this.page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
}
