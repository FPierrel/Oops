package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.PasswordManagerBean;
import fr.univ_lorraine.oops.ejb.SearchResultsBean;
import fr.univ_lorraine.oops.ejb.UserManagerBean;
import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Prestataire.Type;
import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "registrationBean")
@SessionScoped
public class RegistrationBean implements Serializable {

    @Inject
    UserManagerBean userManager;

    @Inject
    SearchResultsBean searchBean;

    @Inject
    PasswordManagerBean pMB;

    private String login, password, confirmPassword, email, phone, companyName,
            lastname, firstname, user, number, street, complement, town,
            country, turnover, employee, website;
    private boolean prestataire, soumissionnaire;
    private UIComponent loginComponent, passwordComponent, confirmPasswordComponent,
            emailComponent, phoneComponent, companyNameComponent, lastnameComponent,
            firstnameComponent, numberComponent, streetComponent, complementComponent,
            townComponent, countryComponent, turnoverComponent, employeeComponent;
    private Type forme;
    private final Type[] formes = Prestataire.Type.values();

    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {
        this.user = "Soumissionnaire";
        this.prestataire = false;
        this.soumissionnaire = true;
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
        return this.email;
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

    public boolean isPrestataire() {
        return prestataire;
    }

    public void setPrestataire(boolean prestataire) {
        this.prestataire = prestataire;
    }

    public boolean isSoumissionnaire() {
        return soumissionnaire;
    }

    public void setSoumissionnaire(boolean soumissionnaire) {
        this.soumissionnaire = soumissionnaire;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        if (user.equals("Soumissionnaire")) {
            this.soumissionnaire = true;
            this.prestataire = false;
        } else {
            this.soumissionnaire = false;
            this.prestataire = true;
        }
    }

    public UIComponent getConfirmPasswordComponent() {
        return confirmPasswordComponent;
    }

    public void setConfirmPasswordComponent(UIComponent confirmPasswordComponent) {
        this.confirmPasswordComponent = confirmPasswordComponent;
    }

    public UIComponent getLoginComponent() {
        return loginComponent;
    }

    public void setLoginComponent(UIComponent loginComponent) {
        this.loginComponent = loginComponent;
    }

    public UIComponent getPasswordComponent() {
        return passwordComponent;
    }

    public void setPasswordComponent(UIComponent passwordComponent) {
        this.passwordComponent = passwordComponent;
    }

    public UIComponent getEmailComponent() {
        return emailComponent;
    }

    public void setEmailComponent(UIComponent emailComponent) {
        this.emailComponent = emailComponent;
    }

    public UIComponent getPhoneComponent() {
        return phoneComponent;
    }

    public void setPhoneComponent(UIComponent phoneComponent) {
        this.phoneComponent = phoneComponent;
    }

    public UIComponent getCompanyNameComponent() {
        return companyNameComponent;
    }

    public void setCompanyNameComponent(UIComponent companyNameComponent) {
        this.companyNameComponent = companyNameComponent;
    }

    public UIComponent getLastnameComponent() {
        return lastnameComponent;
    }

    public void setLastnameComponent(UIComponent lastnameComponent) {
        this.lastnameComponent = lastnameComponent;
    }

    public UIComponent getFirstnameComponent() {
        return firstnameComponent;
    }

    public void setFirstnameComponent(UIComponent firstnameComponent) {
        this.firstnameComponent = firstnameComponent;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UIComponent getNumberComponent() {
        return numberComponent;
    }

    public void setNumberComponent(UIComponent numberComponent) {
        this.numberComponent = numberComponent;
    }

    public UIComponent getStreetComponent() {
        return streetComponent;
    }

    public void setStreetComponent(UIComponent streetComponent) {
        this.streetComponent = streetComponent;
    }

    public UIComponent getComplementComponent() {
        return complementComponent;
    }

    public void setComplementComponent(UIComponent complementComponent) {
        this.complementComponent = complementComponent;
    }

    public UIComponent getTownComponent() {
        return townComponent;
    }

    public void setTownComponent(UIComponent townComponent) {
        this.townComponent = townComponent;
    }

    public UIComponent getCountryComponent() {
        return countryComponent;
    }

    public void setCountryComponent(UIComponent countryComponent) {
        this.countryComponent = countryComponent;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public UIComponent getTurnoverComponent() {
        return turnoverComponent;
    }

    public void setTurnoverComponent(UIComponent turnoverComponent) {
        this.turnoverComponent = turnoverComponent;
    }

    public UIComponent getEmployeeComponent() {
        return employeeComponent;
    }

    public void setEmployeeComponent(UIComponent employeeComponent) {
        this.employeeComponent = employeeComponent;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Type getForme() {
        return forme;
    }

    public void setForme(Type forme) {
        this.forme = forme;
    }

    public Type[] getFormes() {
        return this.formes;
    }
    
    public String registration() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (this.password.length() < 6) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mot de passe trop petit (inférieur à 6), veuillez recommencer !", null);
            context.addMessage(this.passwordComponent.getClientId(), message);
            return "inscription.xhtml";
        }
        if (!this.password.equals(this.confirmPassword)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mot de passe différent, veuillez recommencer !", null);
            context.addMessage(this.confirmPasswordComponent.getClientId(), message);
            return "inscription.xhtml";
        }
        if (!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", this.email)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adresse mail non valide, veuillez recommencer !", null);
            context.addMessage(this.emailComponent.getClientId(), message);
            return "inscription.xhtml";
        }
        if (!Pattern.matches("^[A-Z]+([_A-Z-]+)*[ ]*([_A-Z-]+)*[ ]\\(([0-9]{5})+(-[0-9]{5})*\\)$", this.town)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ville non valide, veuillez recommencer !", null);
            context.addMessage(this.townComponent.getClientId(), message);
            return "inscription.xhtml";
        }
        Utilisateur user;
        Adresse address = new Adresse();
        address.setNumero(this.number);
        address.setRue(this.street);
        address.setComplement(this.complement);
        address.setVille(this.town);
        if (this.soumissionnaire) {
            Soumissionnaire s = new Soumissionnaire();
            s.setInscription(new Date());
            s.setLogin(this.login);
            s.setMotDePasse(pMB.sha256(this.password));
            s.setMail(this.email);
            s.setNom(this.lastname);
            s.setPrenom(this.firstname);
            s.setNumeroTelephone(this.phone);
            s.addAdresse(address);
            user = this.userManager.registerUser(s);
        } else {
            Prestataire p = new Prestataire();
            int nbEmployeeNumber = 0;
            try {
                nbEmployeeNumber = Integer.parseInt(this.employee);
                if (!this.employee.isEmpty() && nbEmployeeNumber < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre d'employé(s) incorrect !", null);
                context.addMessage(this.employeeComponent.getClientId(), message);
                return "inscription.xhtml";
            }
            int turnoverNumber = 0;
            try {
                turnoverNumber = Integer.parseInt(this.turnover);
                if (!this.turnover.isEmpty() && turnoverNumber < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Chiffre d'affaire incorrect !", null);
                context.addMessage(this.turnoverComponent.getClientId(), message);
                return "inscription.xhtml";
            }
            p.setLogin(this.login);
            p.setMotDePasse(pMB.sha256(this.password));
            p.setMail(this.email);
            p.setNom(this.lastname);
            p.setPrenom(this.firstname);
            p.setNumeroTelephone(this.phone);
            p.setNomEntreprise(this.companyName);
            p.setChiffreAffaire(turnoverNumber);
            p.setNbEmployes(nbEmployeeNumber);
            p.setInscription(new Date());
            p.addAdresse(address);
            if (this.website != null && !this.website.isEmpty()) {
                p.setSiteWeb(this.website);
            }
            p.setFormeJuridique(this.forme.toString());
            user = this.userManager.registerUser(p);
        }
        if (user == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login déjà utilisé, veuillez en choisir un nouveau !", null);
            context.addMessage(this.loginComponent.getClientId(), message);
            return "inscription.xhtml";
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Inscription réussie !");
            context.addMessage(null, message);
            return "index.xhtml";
        }
    }

}
