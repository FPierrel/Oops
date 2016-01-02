package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.UserManagerBean;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import javax.inject.Named;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.regex.Pattern;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "profilBean")
@SessionScoped
public class ProfilBean implements Serializable {

    @Inject
    private UserManagerBean userManager;

    private Utilisateur user;
    private String oldPassword, newPassword, newPasswordConfirm, newMail, newMailConfirm;

    public ProfilBean() {

    }

    public void init(String login) {
        this.user = this.userManager.searchByLogin(login);
    }

    public String update() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (!sha256(this.oldPassword).equals(this.user.getMotDePasse())) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mot de passe incorrect !", null);
        } else if (this.newPassword.length() < 6) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mot de passe trop petit (inférieur à 6), veuillez recommencer !", null);
        } else if (!this.newPassword.equals(this.newPasswordConfirm)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Confirmation du nouveau mot de passe incorrecte !", null);
        } else if (!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", this.newMail)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adresse mail non valide, veuillez recommencer !", null);
        } else if (!this.newMail.equals(this.newMailConfirm)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Confirmation de la nouvelle adresse mail incorrecte !", null);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification(s) enregistrée(s) !", null);
            if (!this.newMail.isEmpty()) {
                this.user.setMail(this.newMail);
            }
            if (!this.newPassword.isEmpty()) {
                this.user.setMotDePasse(sha256(this.newPassword));
            }
            this.userManager.updateUser(this.user);
        }
        context.addMessage(null, message);
        return "profil.xhtml";
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public String getNewMail() {
        return newMail;
    }

    public void setNewMail(String newMail) {
        this.newMail = newMail;
    }

    public String getNewMailConfirm() {
        return newMailConfirm;
    }

    public void setNewMailConfirm(String newMailConfirm) {
        this.newMailConfirm = newMailConfirm;
    }

}
