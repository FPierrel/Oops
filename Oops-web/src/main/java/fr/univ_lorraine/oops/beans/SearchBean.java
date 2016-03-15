package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.CategoriesBean;
import fr.univ_lorraine.oops.ejb.SearchResultsBean;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Prestataire.Type;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@Named(value = "searchBean")
@SessionScoped
public class SearchBean implements Serializable {

    @Inject
    SearchResultsBean searchResults;

    @Inject
    CategoriesBean categoriesB;

    private String lastnameSearch, firstnameSearch, raisonSocialeSearch, formeJuridiqueSearch, chiffreAffaireSearch;
    private String quoi, ou;
    private String employeeSearch;
    private boolean advanced;
    private List<Prestataire> prestataires = new ArrayList<>();
    private List<String> villes = new ArrayList<>();
    private String choix;
    private List<String> allCategories = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private List<String> codes = new ArrayList<>();
    private String code;
    private int communication, quality, price, delay, moyenne;
    private final Prestataire.Type[] allFormes = Prestataire.Type.values();
    private List<String> formesJuridiques = new ArrayList<>();

    /**
     * Constructor
     */
    public SearchBean() {

    }

    /**
     * Call the method simpleSearch or search with the correct arguments
     * @return the redirection link
     */
    public String search() {
        String ville = "";
        String codePostal = "";

        if (this.choix != null && !this.choix.isEmpty()) {
            ville = this.choix.split(" ")[0];
            if (this.choix.split(" ").length > 1) {
                codePostal = this.choix.split(" ")[1].replaceAll("[()]", "");
            }
        }

        if (this.advanced) {
            this.advanced = false;
            int emp = this.employeeSearch == null ? 0 : Integer.parseInt(this.employeeSearch);
            int turnover = this.chiffreAffaireSearch == null ? 0 : Integer.parseInt(this.chiffreAffaireSearch);
            this.prestataires = this.searchResults.search(this.quoi, ville, codePostal, this.lastnameSearch, this.firstnameSearch, emp, this.raisonSocialeSearch, this.formesJuridiques, turnover, communication, quality, price, delay, moyenne, categories);
        } else {
            this.prestataires = this.searchResults.simpleSearch(this.quoi, ville, codePostal);
        }
        return "results?faces-redirect=true";
    }

    /**
     * Call searchTest from SearchResultsBean
     * @param query
     * @return the result of searchTest
     */
    public List<String> searchTown(String query) {
        return this.searchResults.searchTest(query);
    }

    /**
     * Get the names of cities matching the parameter
     * @param query name of the city
     * @return a List of String, all the names of the cities in France matching the parameter
     */
    public List<String> searchTownWithoutCode(String query) {
        return this.searchResults.searchTownWithoutCode(query);
    }

    /**
     * Getter lastnameSearch
     * @return
     */
    public String getLastnameSearch() {
        return lastnameSearch;
    }

    /**
     * Setter lastnameSearch
     * @param lastnameSearch
     */
    public void setLastnameSearch(String lastnameSearch) {
        this.lastnameSearch = lastnameSearch;
    }

    /**
     * Getter firstnameSearch
     * @return
     */
    public String getFirstnameSearch() {
        return firstnameSearch;
    }

    /**
     * Setter firstnameSearch
     * @param firstnameSearch
     */
    public void setFirstnameSearch(String firstnameSearch) {
        this.firstnameSearch = firstnameSearch;
    }

    /**
     * Getter employeeSearch
     * @return
     */
    public String getEmployeeSearch() {
        return employeeSearch;
    }

    /**
     * Setter employeeSearch
     * @param employeeSearch
     */
    public void setEmployeeSearch(String employeeSearch) {
        this.employeeSearch = employeeSearch;
    }

    /**
     * Getter raisonSocialeSearch
     * @return
     */
    public String getRaisonSocialeSearch() {
        return raisonSocialeSearch;
    }

    /**
     * Setter raisonSocialeSearch
     * @param raisonSocialeSearch
     */
    public void setRaisonSocialeSearch(String raisonSocialeSearch) {
        this.raisonSocialeSearch = raisonSocialeSearch;
    }

    /**
     * Getter formeJuridique
     * @return
     */
    public String getFormeJuridiqueSearch() {
        return formeJuridiqueSearch;
    }

    /**
     * Setter formeJuridique
     * @param formeJuridiqueSearch
     */
    public void setFormeJuridiqueSearch(String formeJuridiqueSearch) {
        this.formeJuridiqueSearch = formeJuridiqueSearch;
    }

    /**
     * Getter chiffreAffaire
     * @return
     */
    public String getChiffreAffaireSearch() {
        return chiffreAffaireSearch;
    }

    /**
     * Setter chiffreAffaire
     * @param chiffreAffaireSearch
     */
    public void setChiffreAffaireSearch(String chiffreAffaireSearch) {
        this.chiffreAffaireSearch = chiffreAffaireSearch;
    }

    /**
     * Getter advanced
     * @return
     */
    public boolean isAdvanced() {
        return advanced;
    }

    /**
     * Setter advanced
     * @param advanced
     */
    public void setAdvanced(boolean advanced) {
        this.advanced = advanced;
    }

    /**
     * Change advanced from false to true and vice-versa
     */
    public void switchAdvanced() {
        this.advanced = !this.advanced;
    }

    /**
     * Set advanced to false
     */
    public void cancelAdvanced() {
        this.advanced = false;
    }

    /**
     * Getter prestataires
     * @return 
     */
    public List<Prestataire> getPrestataires() {
        return prestataires;
    }

    /**
     * Setter prestataires
     * @param prestataires
     */
    public void setPrestataires(List<Prestataire> prestataires) {
        this.prestataires = prestataires;
    }

    /**
     * Getter quoi
     * @return
     */
    public String getQuoi() {
        return quoi;
    }

    /**
     * Setter quoi
     * @param quoi
     */
    public void setQuoi(String quoi) {
        this.quoi = quoi;
    }

    /**
     * Getter ou
     * @return
     */
    public String getOu() {
        return ou;
    }

    /**
     * Setter ou
     * @param ou
     */
    public void setOu(String ou) {
        this.ou = ou;
    }

    /**
     * Getter communication
     * @return
     */
    public int getCommunication() {
        return communication;
    }

    /**
     * Setter communication
     * @param communication
     */
    public void setCommunication(int communication) {
        this.communication = communication;
    }

    /**
     * Getter quality
     * @return
     */
    public int getQuality() {
        return quality;
    }

    /**
     * Setter quality
     * @param quality
     */
    public void setQuality(int quality) {
        this.quality = quality;
    }

    /**
     * Getter price
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     * Setter price
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Getter delay
     * @return
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Setter delay
     * @param delay
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * Getter moyenne
     * @return
     */
    public int getMoyenne() {
        return moyenne;
    }

    /**
     * Setter moyenne
     * @param moyenne
     */
    public void setMoyenne(int moyenne) {
        this.moyenne = moyenne;
    }

    /**
     * Getter villes
     * @return
     */
    public List<String> getVilles() {
        return villes;
    }

    /**
     * Setter villes
     * @param villes
     */
    public void setVilles(List<String> villes) {
        this.villes = villes;
    }

    /**
     * Getter choix
     * @return
     */
    public String getChoix() {
        return choix;
    }

    /**
     * Setter choix
     * @param choix
     */
    public void setChoix(String choix) {
        this.choix = choix;
    }

    /**
     * Set a different message depending of the number of results
     * @return the message
     */
    public String getNbResult() {
        if (this.prestataires.isEmpty()) {
            return "Aucun résultat";
        } else if (this.prestataires.size() == 1) {
            return "1 Résultat :";
        } else if (this.prestataires.size() > 0) {
            return "" + this.prestataires.size() + " Résultats :";
        } else {
            return "";
        }
    }

    /**
     * Getter allCategories. Call getListCategories of CategoriesBean
     * @return
     */
    public List<String> getAllCategories() {
        allCategories = this.categoriesB.getListCategories();
        return allCategories;
    }

    /**
     * Setter allCategories
     * @param categories
     */
    public void setAllCategories(List<String> categories) {
        this.allCategories = categories;
    }

    /**
     * Getter categories
     * @return
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Setter categories
     * @param categories
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * Getter codes
     * @return
     */
    public List<String> getCodes() {
        return codes;
    }

    /**
     * Setter codes
     * @param codes
     */
    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    /**
     * Call searchCodes from SearchResultsBean
     */
    public void codesListener() {
        codes = this.searchResults.searchCodes(this.ou);
    }

    /**
     * Getter code
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter code
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Convert the list of categories to a usable format
     * @param list the list of categories
     * @return a formatted String
     */
    public String toJavascriptArray(List<String> list) {
        String[] arr = list.toArray(new String[0]);
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
    
    /**
     * Getter allFormes
     * @return
     */
    public Type[] getAllFormes() {
        return this.allFormes;
    }

    /**
     * Getter formesJuridiques
     * @return
     */
    public List<String> getFormesJuridiques() {
        return formesJuridiques;
    }

    /**
     * Setter formesJuridiques
     * @param formesJuridiques
     */
    public void setFormesJuridiques(List<String> formesJuridiques) {
        this.formesJuridiques = formesJuridiques;
    }
    
}
