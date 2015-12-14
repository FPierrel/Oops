package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.SearchResultsBean;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@Named(value = "searchBean")
@SessionScoped
public class SearchBean implements Serializable{
    
    @Inject
    SearchResultsBean searchResults;
    
    private String lastnameSearch, firstnameSearch, townSearch;
    private int employeeSearch;
    private float ratingSearch;
    private boolean advanced;
    
    private List<Prestataire> prestataires;

    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
    }
    
    public String search() {
        this.prestataires = this.searchResults.search(this.lastnameSearch);
        return "results?faces-redirect=true";
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

    public float getRatingSearch() {
        return ratingSearch;
    }

    public void setRatingSearch(float ratingSearch) {
        this.ratingSearch = ratingSearch;
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
    
}
