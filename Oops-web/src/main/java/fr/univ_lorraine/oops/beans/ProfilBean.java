package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.PasswordManagerBean;
import fr.univ_lorraine.oops.ejb.SearchResultsBean;
import fr.univ_lorraine.oops.ejb.UserManagerBean;
import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Inject
    private SearchResultsBean searchBean;
    
    @Inject
    PasswordManagerBean pMB;

    private Utilisateur user;
    private String oldPassword, newPassword, newPasswordConfirm, newMail, newMailConfirm, town;
    private List<String> codes = new ArrayList<>();

    public ProfilBean() {

    }

    public void init(String login) {
        this.user = this.userManager.searchByLogin(login);
        Collection<Adresse> address = this.user.getAdresses();
        Adresse a = (address.toArray(new Adresse[0]))[0];
        this.town = a.getVille();
    }

    public String update() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (!this.oldPassword.isEmpty() && !pMB.sha256(this.oldPassword).equals(this.user.getMotDePasse())) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mot de passe incorrect !", null);
        } else if (!this.oldPassword.isEmpty() && this.newPassword.length() < 6) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mot de passe trop petit (inférieur à 6), veuillez recommencer !", null);
        } else if (!this.oldPassword.isEmpty() && !this.newPassword.isEmpty() && !this.newPassword.equals(this.newPasswordConfirm)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Confirmation du nouveau mot de passe incorrecte !", null);
        } else if (!this.newMail.isEmpty() && !Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", this.newMail)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adresse mail non valide, veuillez recommencer !", null);
        } else if (!this.newMail.isEmpty() && !this.newMail.equals(this.newMailConfirm)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Confirmation de la nouvelle adresse mail incorrecte !", null);
        } else if (!Pattern.matches("^[A-Z]+([_A-Z-]+)*[ ]*([_A-Z-]+)*[ ]\\(([0-9]{5})+(-[0-9]{5})*\\)$", this.town)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ville non valide, veuillez recommencer !", null);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification(s) enregistrée(s) !", null);
            if (!this.newMail.isEmpty()) {
                this.user.setMail(this.newMail);
            }
            if (!this.newPassword.isEmpty()) {
                this.user.setMotDePasse(pMB.sha256(this.newPassword));
            }
            Collection<Adresse> address = this.user.getAdresses();
            Adresse a = (address.toArray(new Adresse[0]))[0];
            a.setVille(this.town);
            this.user.setAdresses(address);
            this.userManager.updateUser(this.user);
        }
        context.addMessage(null, message);
        return "profil.xhtml";
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

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public void codesListener(Adresse a) {
        codes = searchBean.searchCodes(a.getVille());
    }

   public String getTown() {
        return town;
    }
 
    public void setTown(String town) {
        this.town = town;
    }
     
}
