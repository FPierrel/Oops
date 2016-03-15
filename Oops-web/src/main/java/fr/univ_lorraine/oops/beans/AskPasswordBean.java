package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.PasswordManagerBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Named(value = "askPasswordBean")
@RequestScoped
public class AskPasswordBean {

    @Inject
    PasswordManagerBean pMB;

    private String feedback = "";
    private String mail;

    public AskPasswordBean() {
    }

    /**
     * Méthode permettant d'envoyer un nouveau mot de passe.
     * @return page vers laquelle on est redirigé.
     */
    public String requestNewPassword() {
        if (pMB.sendNewPassword(mail)) {
            feedback = "Un email vous à été envoyé à l'adresse " + mail;
        } else {
            feedback = "Adresse mail incorrecte";
        }
        return "/forgotPassword.xhtml";
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
