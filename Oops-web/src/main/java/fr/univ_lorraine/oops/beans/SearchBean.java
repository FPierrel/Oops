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
public class SearchBean implements Serializable{
    
    @Inject
    SearchResultsBean searchResults;
    
    private String lastnameSearch, firstnameSearch, townSearch;
    private String quoi, ou;
    private int employeeSearch;
    private boolean advanced;
    private int note = 3; /* A RETIRER QUAND LES NOTES DES AVIS SERONT FAITES ! */
    private List<Prestataire> prestataires;
    private List<String> villes = new ArrayList<>(); 
    private String choix;

    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
       
    }
    
    public String search() {
        if(this.advanced) {
            this.prestataires = this.searchResults.search(this.lastnameSearch, this.firstnameSearch, this.townSearch, this.employeeSearch);
            return "results?faces-redirect=true";
        }else{
            this.prestataires = this.searchResults.simpleSearch(this.quoi, this.choix);
            return "results?faces-redirect=true";
        }
    }
    
    public void searchTown(AjaxBehaviorEvent event){
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

    public String getTownSearch() {
        return townSearch;
    }

    public void setTownSearch(String townSearch) {
        this.townSearch = townSearch;
    }

    public int getEmployeeSearch() {
        return employeeSearch;
    }

    public void setEmployeeSearch(int employeeSearch) {
        this.employeeSearch = employeeSearch;
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
