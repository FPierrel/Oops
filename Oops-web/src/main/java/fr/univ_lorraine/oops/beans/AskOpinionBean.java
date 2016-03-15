package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.MailManagerBean;
import java.io.Serializable;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Named(value = "askOpinionBean")
@RequestScoped
public class AskOpinionBean implements Serializable {

    @Inject
    MailManagerBean mMB;

    private String mail;
    private UIComponent emailComponent;
    private String page;

    public AskOpinionBean() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UIComponent getEmailComponent() {
        return emailComponent;
    }

    public void setEmailComponent(UIComponent emailComponent) {
        this.emailComponent = emailComponent;
    }

    /**
     * Méthode permettant d'envoyer un e-mail d'invitation.
     */
    public void send() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!Pattern.matches("^[_A-Za-z0-9-\\+\\.]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z-]{2,})$", this.mail)) {
            FacesMessage fM = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Format adresse mail non valide, veuillez recommencer !", null);
            context.addMessage(this.emailComponent.getClientId(), fM);
        } else {
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            if (mMB.sendAskOpinion(request.getRemoteUser(), mail, "http://pi-r-l.ovh:8080/Oops-web/" + "?page=" + page)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "L'invitation a bien été envoyé !", null);
                context.addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cette adresse n'est associée à aucun utilisateur du site !", null);
                context.addMessage(null, message);
            }
        }
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
