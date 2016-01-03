package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.SearchResultsBean;
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
    private int note = 3; /* A RETIRER QUAND LES NOTES DES AVIS SERONT FAITES ! */
    private List<Prestataire> prestataires;
    private List<String> villes = new ArrayList<>();
    private String choix;

    private int communication, quality, price, delay, moyenne;
    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {

    }

    public String search() {
        if (this.advanced) {
            int emp = this.employeeSearch == null? 0 : Integer.parseInt(this.employeeSearch);
            int turnover = this.chiffreAffaireSearch == null? 0 : Integer.parseInt(this.chiffreAffaireSearch);
            this.prestataires = this.searchResults.search(this.quoi, this.choix, this.lastnameSearch, this.firstnameSearch, emp, this.raisonSocialeSearch, this.formeJuridiqueSearch, turnover, communication, quality, price, delay, moyenne);
        } else {
            this.prestataires = this.searchResults.simpleSearch(this.quoi, this.choix);
        }
        return "results?faces-redirect=true";
    }

    public void searchTown(AjaxBehaviorEvent event) {
        this.villes = this.searchResults.searchTest(this.ou);
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
}