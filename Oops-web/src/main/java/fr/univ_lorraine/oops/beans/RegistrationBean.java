package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.SearchResultsBean;
import fr.univ_lorraine.oops.ejb.UserManagerBean;
import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    
    private List<String> codes = new ArrayList<>();
    private String login, password, confirmPassword, email, phone, companyName, 
            lastname, firstname, user, number, street, code, complement, town, country;
    private int turnover, employee;
    private boolean prestataire, soumissionnaire;    
    private UIComponent loginComponent, passwordComponent, confirmPasswordComponent, 
            emailComponent, phoneComponent, companyNameComponent, lastnameComponent, 
            firstnameComponent, numberComponent, streetComponent, complementComponent, 
            codeComponent, townComponent, countryComponent, turnoverComponent, employeeComponent;
    
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
        if(user.equals("Soumissionnaire")) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public UIComponent getCodeComponent() {
        return codeComponent;
    }

    public void setCodeComponent(UIComponent codeComponent) {
        this.codeComponent = codeComponent;
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

    public int getTurnover() {
        return turnover;
    }

    public void setTurnover(int turnover) {
        this.turnover = turnover;
    }

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
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
    
    public String registration() {
        FacesContext context = FacesContext.getCurrentInstance();
        if(this.password.length() < 6) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mot de passe trop petit (inférieur à 6), veuillez recommencer !", null);
            context.addMessage(this.passwordComponent.getClientId(), message); 
            return "inscription.xhtml";
        }
        if(!this.password.equals(this.confirmPassword)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mot de passe différent, veuillez recommencer !", null);
            context.addMessage(this.confirmPasswordComponent.getClientId(), message); 
            return "inscription.xhtml";
        }
        if(! Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", this.email)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adresse mail non valide, veuillez recommencer !", null);
            context.addMessage(this.emailComponent.getClientId(), message); 
            return "inscription.xhtml";
        }
        Utilisateur user;
        Adresse address = new Adresse();
        address.setNumero(this.number);
        address.setRue(this.street);
        address.setComplement(this.complement);
        address.setCodePostal(this.code);
        address.setVille(this.town);
        if(this.soumissionnaire) {
            Soumissionnaire s = new Soumissionnaire();
            s.setLogin(this.login);
            s.setMotDePasse(ProfilBean.sha256(this.password));
            s.setMail(this.email);
            s.setNom(this.lastname);
            s.setPrenom(this.firstname);
            s.setNumeroTelephone(this.phone);
            s.addAdresse(address);
            user = this.userManager.registerUser(s);
        } else {
            Prestataire p = new Prestataire();
            p.setLogin(this.login);
            p.setMotDePasse(ProfilBean.sha256(this.password));
            p.setMail(this.email);
            p.setNom(this.lastname);
            p.setPrenom(this.firstname);
            p.setNumeroTelephone(this.phone);
            p.setNomEntreprise(this.companyName);
            p.setChiffreAffaire(this.turnover);
            p.setNbEmployes(this.employee);
            p.addAdresse(address);
            user = this.userManager.registerUser(p);
        }
        if(user == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login déjà  utilisé, veuillez en choisir un nouveau !", null);
            context.addMessage(this.loginComponent.getClientId(), message); 
            return "inscription.xhtml";
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Inscription réussie !");
            context.addMessage(null, message); 
            return "index.xhtml";
        }
    }
    
    public List<String> searchCode(String town){
        return this.searchBean.searchCodes(town);
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
    
    public void codesListener() {
        codes = searchBean.searchCodes(this.town);
    }
    
}
