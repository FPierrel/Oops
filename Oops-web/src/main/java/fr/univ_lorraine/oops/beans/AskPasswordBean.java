/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.PasswordManagerBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author romain
 */
@Named(value = "askPasswordBean")
@RequestScoped
public class AskPasswordBean {

    @Inject
    PasswordManagerBean pMB;

    private String feedback = "";
    private String mail;

    /**
     * Creates a new instance of PasswordManagerBean
     */
    public AskPasswordBean() {
    }

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
