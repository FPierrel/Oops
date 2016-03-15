package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.CategoriesBean;
import fr.univ_lorraine.oops.ejb.UserManagerBean;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Prestataire.Type;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "editFicheBean")
@SessionScoped
public class EditFicheBean implements Serializable {

    @Inject
    private UserManagerBean userManager;

    @Inject
    private CategoriesBean categoriesB;

    private Prestataire prestataire;
    private String companyName, website, nbEmployee, turnover, description;
    private UIComponent companyNameComponent, websiteComponent, nbEmployeeComponent, turnoverNumberComponent, descriptionComponent;
    private List<String> allCategories, categories;
    private final Prestataire.Type[] formes = Prestataire.Type.values();

    public EditFicheBean() {
    }

    /**
     * Méthode permettant d'initialiser le formulaire.
     * @param login : login du prestataire.
     */
    public void init(String login) {
        this.prestataire = (Prestataire) this.userManager.searchByLogin(login);
        if (this.prestataire != null) {
            this.companyName = this.prestataire.getNomEntreprise();
            this.website = this.prestataire.getSiteWeb();
            this.nbEmployee = this.prestataire.getNbEmployes() + "";
            this.turnover = this.prestataire.getChiffreAffaire() + "";
            this.description = this.prestataire.getDescription();
            this.allCategories = this.categoriesB.getListCategories();
            this.categories = this.prestataire.getListCategories();
        }
    }

    /**
     * Méthode permettant de mettre à jour sa fiche.
     * @return page vers laquelle on est redirigé.
     */
    public String update() {
        boolean error = false;
        FacesContext context = FacesContext.getCurrentInstance();
        if (this.companyName.isEmpty()) {
            error = true;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nom du Prestaire non renseigné !", null);
            context.addMessage(this.companyNameComponent.getClientId(), message);
        }
        int nbEmployeeNumber = 0;
        try {
            nbEmployeeNumber = Integer.parseInt(this.nbEmployee);
            if (nbEmployeeNumber < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            error = true;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre d'employé(s) incorrect !", null);
            context.addMessage(this.nbEmployeeComponent.getClientId(), message);
        }
        int turnoverNumber = 0;
        try {
            turnoverNumber = Integer.parseInt(this.turnover);
            if (turnoverNumber < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            error = true;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Chiffre d'affaire incorrect !", null);
            context.addMessage(this.turnoverNumberComponent.getClientId(), message);
        }
        if (this.description.isEmpty()) {
            error = true;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Description du Prestataire non renseignée !", null);
            context.addMessage(this.descriptionComponent.getClientId(), message);
        }
        if (!error) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification(s) enregistrée(s) !", null);
            context.addMessage(null, message);
            this.prestataire.setNomEntreprise(this.companyName);
            this.prestataire.setSiteWeb(this.website);
            this.prestataire.setNbEmployes(nbEmployeeNumber);
            this.prestataire.setChiffreAffaire(turnoverNumber);
            this.prestataire.setDescription(this.description);
            this.prestataire.setCategories(this.categoriesB.getCategoriesFromListString(categories));
            this.userManager.updateUser(this.prestataire);
        }
        return "ficheedit.xhtml";
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNbEmployee() {
        return nbEmployee;
    }

    public void setNbEmployee(String nbEmployee) {
        this.nbEmployee = nbEmployee;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UIComponent getCompanyNameComponent() {
        return companyNameComponent;
    }

    public void setCompanyNameComponent(UIComponent companyNameComponent) {
        this.companyNameComponent = companyNameComponent;
    }

    public UIComponent getWebsiteComponent() {
        return websiteComponent;
    }

    public void setWebsiteComponent(UIComponent websiteComponent) {
        this.websiteComponent = websiteComponent;
    }

    public UIComponent getNbEmployeeComponent() {
        return nbEmployeeComponent;
    }

    public void setNbEmployeeComponent(UIComponent nbEmployeeComponent) {
        this.nbEmployeeComponent = nbEmployeeComponent;
    }

    public UIComponent getTurnoverNumberComponent() {
        return turnoverNumberComponent;
    }

    public void setTurnoverNumberComponent(UIComponent turnoverNumberComponent) {
        this.turnoverNumberComponent = turnoverNumberComponent;
    }

    public UIComponent getDescriptionComponent() {
        return descriptionComponent;
    }

    public void setDescriptionComponent(UIComponent descriptionComponent) {
        this.descriptionComponent = descriptionComponent;
    }

    public List<String> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<String> allCategories) {
        this.allCategories = allCategories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * Méthode mettant en forme la liste des catégories au format pour javascript.
     * @return la liste des catégories au format pour javascript.
     */
    public String toJavascriptArray() {
        String[] arr = this.categories.toArray(new String[0]);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append("\"").append(arr[i]).append("\"");
            if (i + 1 < arr.length) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public Type[] getFormes() {
        return this.formes;
    }
    
}
