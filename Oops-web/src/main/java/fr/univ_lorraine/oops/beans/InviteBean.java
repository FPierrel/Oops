/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.MailManagerBean;
import java.io.Serializable;
import java.util.regex.Pattern;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author romain
 */
@Named(value = "inviteBean")
@RequestScoped
public class InviteBean implements Serializable {

    @Inject
    MailManagerBean mMB;

    private String mail, message;
    private String feedback = "";

    private UIComponent emailComponent;

    /**
     * Creates a new instance of InviteBean
     */
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public UIComponent getEmailComponent() {
        return emailComponent;
    }

    public void setEmailComponent(UIComponent emailComponent) {
        this.emailComponent = emailComponent;
    }

    public String send() {
        feedback = "";
        FacesContext context = FacesContext.getCurrentInstance();
        if (!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", this.mail)) {
            FacesMessage fM = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adresse mail non valide, veuillez recommencer !", null);
            context.addMessage(this.emailComponent.getClientId(), fM);
            return "invite.xhtml";
        }
        System.out.println("mail = " + mail);
        System.out.println("message = " + message);
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (mMB.sendInviteMail(request.getRemoteUser(), mail, message)) {
            feedback = "Message envoyé";
        } else {
            feedback = "Un utilisateur ayant cette adresse mail est déjà inscrit sur le site";
        }
        return "invite.xhtml";
    }

}
