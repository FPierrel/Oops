package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.UserManagerBean;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Named(value = "registrationBean")
@RequestScoped
public class RegistrationBean {

    @Inject
    UserManagerBean userManager;

    private String login;
    private String password;
    private String confirmPassword;
    private String email;
    private String phone;
    private String companyName;
    private String lastname;
    private String firstname;

    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String registrationSoumissionnaire() {
        if (!this.password.equals(this.confirmPassword)) {
            //TODO : Message à afficher pour l'utilisateur.
            System.out.println("********************************** MDP !");
            System.out.println(this.password);
            System.out.println(this.confirmPassword);
            return "inscriptionS.xhtml";
        }
        Soumissionnaire s = new Soumissionnaire();
        s.setLogin(this.login);
        s.setMotDePasse(this.password);
        s.setMail(this.email);
        s.setNom(this.lastname);
        s.setPrenom(this.firstname);
        s.setNumeroTelephone(this.phone);
        Utilisateur user = this.userManager.registerUser(s);
        if (user == null) {
            System.out.println("********************************** EXISTE !");
            return "inscriptionS.xhtml";
            //TODO : Message à afficher pour l'utilisateur (login déjà pris).
        } else {
            return "index.xhtml";
            //TODO : Message à afficher à l'utilisateur (inscription réussie).
        }
    }

    public String registrationPrestataire() {
        if (!this.password.equals(this.confirmPassword)) {
            //TODO : Message à afficher pour l'utilisateur.
            return "inscriptionP.xhtml";
        }
        Prestataire p = new Prestataire();
        p.setLogin(this.login);
        p.setMotDePasse(this.password);
        p.setMail(this.email);
        p.setNom(this.lastname);
        p.setPrenom(this.firstname);
        p.setNumeroTelephone(this.phone);
        p.setNomEntreprise(this.companyName);
        Utilisateur user = this.userManager.registerUser(p);
        if (user == null) {
            return "inscriptionP.xhtml";
            //TODO : Message à afficher pour l'utilisateur (login déjà pris).
        } else {
            return "index.xhtml";
            //TODO : Message à afficher à l'utilisateur (inscription réussie).
        }
    }

}
