package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.CategoriesBean;
import fr.univ_lorraine.oops.ejb.SearchResultsBean;
import fr.univ_lorraine.oops.library.model.Prestataire;
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
    private List<Prestataire> listPrestataires;

    private int communication, quality, price, delay, moyenne;

    public SearchBean() {

    }

    public String search() {
        String ville = "";
        String codePostal = "";
        
        if(this.choix != null && !this.choix.isEmpty()) {
            ville = this.choix.split(" ")[0];
            codePostal = this.choix.split(" ")[1].replaceAll("[()]", "");
        }

        if (this.advanced) {
            this.advanced = false;
            int emp = this.employeeSearch == null ? 0 : Integer.parseInt(this.employeeSearch);
            int turnover = this.chiffreAffaireSearch == null ? 0 : Integer.parseInt(this.chiffreAffaireSearch);
            this.prestataires = this.searchResults.search(this.quoi, ville, codePostal, this.lastnameSearch, this.firstnameSearch, emp, this.raisonSocialeSearch, this.formeJuridiqueSearch, turnover, communication, quality, price, delay, moyenne, categories);
        } else {
            this.prestataires = this.searchResults.simpleSearch(this.quoi, ville, codePostal);
        }
        return "results?faces-redirect=true";
    }

    public List<String> searchTown(String query) {
        return this.searchResults.searchTest(query);
    }

    public String toJavascriptArray(String query) {
        String[] arr = searchTown(query).toArray(new String[0]);
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

    public List<String> searchTownWithoutCode(String query) {
        return this.searchResults.searchTownWithoutCode(query);
    }

    public String getLastnameSearch() {
        return lastnameSearch;
    }

    public void setLastnameSearch(String lastnameSearch) {
        this.lastnameSearch = lastnameSearch;
    }

    public String getFirstnameSearch() {
        return firstnameSearch;
    }

    public void setFirstnameSearch(String firstnameSearch) {
        this.firstnameSearch = firstnameSearch;
    }

    public String getEmployeeSearch() {
        return employeeSearch;
    }

    public void setEmployeeSearch(String employeeSearch) {
        this.employeeSearch = employeeSearch;
    }

    public String getRaisonSocialeSearch() {
        return raisonSocialeSearch;
    }

    public void setRaisonSocialeSearch(String raisonSocialeSearch) {
        this.raisonSocialeSearch = raisonSocialeSearch;
    }

    public String getFormeJuridiqueSearch() {
        return formeJuridiqueSearch;
    }

    public void setFormeJuridiqueSearch(String formeJuridiqueSearch) {
        this.formeJuridiqueSearch = formeJuridiqueSearch;
    }

    public String getChiffreAffaireSearch() {
        return chiffreAffaireSearch;
    }

    public void setChiffreAffaireSearch(String chiffreAffaireSearch) {
        this.chiffreAffaireSearch = chiffreAffaireSearch;
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public void setAdvanced(boolean advanced) {
        this.advanced = advanced;
    }

    public void switchAdvanced() {
        this.advanced = !this.advanced;
    }

    public void cancelAdvanced() {
        this.advanced = false;
    }

    public List<Prestataire> getPrestataires() {
        return prestataires;
    }

    public void setPrestataires(List<Prestataire> prestataires) {
        this.prestataires = prestataires;
    }

    public String getQuoi() {
        return quoi;
    }

    public void setQuoi(String quoi) {
        this.quoi = quoi;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public int getCommunication() {
        return communication;
    }

    public void setCommunication(int communication) {
        this.communication = communication;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(int moyenne) {
        this.moyenne = moyenne;
    }

    public List<String> getVilles() {
        return villes;
    }

    public void setVilles(List<String> villes) {
        this.villes = villes;
    }

    public String getChoix() {
        return choix;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }

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

    public List<String> getAllCategories() {
        allCategories = this.categoriesB.getListCategories();
        return allCategories;
    }

    public void setAllCategories(List<String> categories) {
        this.allCategories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public void codesListener() {
        codes = this.searchResults.searchCodes(this.ou);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
}
