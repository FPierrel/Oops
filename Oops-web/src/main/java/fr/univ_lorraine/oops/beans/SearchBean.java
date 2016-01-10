package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.SearchResultsBean;
import fr.univ_lorraine.oops.library.model.Categorie;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

@Named(value = "searchBean")
@SessionScoped
public class SearchBean implements Serializable {

    @Inject
    SearchResultsBean searchResults;

    private String lastnameSearch, firstnameSearch, raisonSocialeSearch, formeJuridiqueSearch, chiffreAffaireSearch;
    private String quoi, ou;
    private String employeeSearch;
    private boolean advanced;
    private int note = 3;
    /* A RETIRER QUAND LES NOTES DES AVIS SERONT FAITES ! */
    private List<Prestataire> prestataires;
    private List<String> villes = new ArrayList<>();
    private String choix;
    private List<String> categories = new ArrayList<>();
    private String categorie;

    private int communication, quality, price, delay, moyenne;
    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {

    }

    public String search() {
        String ville = "";
        String codePostal = "";
        
        try {
            ville = this.choix.split(" ")[0];
            codePostal = this.choix.split(" ")[1].replaceAll("[()]", "");
        } catch (NullPointerException e) {

        }

        if (this.advanced) {
            int emp = this.employeeSearch == null ? 0 : Integer.parseInt(this.employeeSearch);
            int turnover = this.chiffreAffaireSearch == null ? 0 : Integer.parseInt(this.chiffreAffaireSearch);
             this.prestataires = this.searchResults.search(this.quoi, ville, codePostal, this.lastnameSearch, this.firstnameSearch, emp, this.raisonSocialeSearch, this.formeJuridiqueSearch, turnover, communication, quality, price, delay, moyenne, categorie.replace("-", ""));
        } else {
            this.prestataires = this.searchResults.simpleSearch(this.quoi, ville, codePostal);
        }
        return "results?faces-redirect=true";
    }

    /*public void searchTown(AjaxBehaviorEvent event) {
        this.villes = this.searchResults.searchTest(this.ou);
    }*/
    public List<String> searchTown(String query) {
        return this.searchResults.searchTest(query);
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

    public List<Prestataire> getPrestataires() {
        return prestataires;
    }

    public void setPrestataires(List<Prestataire> prestataires) {
        this.prestataires = prestataires;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
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
    
    public String getNbResult(){
        if (this.prestataires.size() == 0)
            return "Aucun résultat";
        else if (this.prestataires.size() == 1)
            return "1 Résultat :";
        else if (this.prestataires.size() > 0)
            return "" + this.prestataires.size() + " Résultats :";
        else
            return "";
    }
    public List<String> getCategories() {
        Categorie c = searchResults.getCategories();
        return c.getListCategories(new ArrayList<String>(), 0);
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
