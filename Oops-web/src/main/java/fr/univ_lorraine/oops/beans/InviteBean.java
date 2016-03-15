package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.MailManagerBean;
import java.io.Serializable;
import java.util.regex.Pattern;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Named(value = "inviteBean")
@RequestScoped
public class InviteBean implements Serializable {

    @Inject
    MailManagerBean mMB;

    private String mail, message;

    public InviteBean() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Méthode permettant d'envoyer un mail d'inviation.
     * @return page vers laquelle on est redirigé.
     */
    public String send() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", this.mail)) {
            FacesMessage fM = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adresse mail non valide, veuillez recommencer !", null);
            context.addMessage(null, fM);
            return "invite.xhtml";
        }
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (mMB.sendInviteMail(request.getRemoteUser(), mail, message)) {
            FacesMessage fM = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message envoyé !", null);
            context.addMessage(null, fM);
        } else {
            FacesMessage fM = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un utilisateur ayant cette adresse e-mail est déjà inscrit sur le site !", null);
            context.addMessage(null, fM);
        }
        return "invite.xhtml";
    }

}
