/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.SearchResultsBean;
import fr.univ_lorraine.oops.library.model.Prestataire;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 *
 * @author Romain
 */
@Named(value = "searchBean")
@SessionScoped
public class SearchBean implements Serializable {

    @Inject
    SearchResultsBean s;

    private ArrayList<Prestataire> prestataires;
    private String whatSearch, whereSearch, lastnameSearch, firstnameSearch, townSearch;
    private int employeeSearch;
    private float ratingSearch;
    private boolean advanced;

    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
        prestataires = new ArrayList<>();
    }

    public String search() {
        prestataires = new ArrayList(s.search(lastnameSearch, firstnameSearch, townSearch, employeeSearch, ratingSearch));

        return "results.xhtml";
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public void setAdvanced(boolean advanced) {
        this.advanced = advanced;
    }

    public ArrayList<Prestataire> getPrestataires() {
        return prestataires;
    }

    public void setPrestataires(ArrayList<Prestataire> prestataires) {
        this.prestataires = prestataires;
    }

    public String getWhatSearch() {
        return whatSearch;
    }

    public void setWhatSearch(String whatSearch) {
        this.whatSearch = whatSearch;
    }

    public String getWhereSearch() {
        return whereSearch;
    }

    public void setWhereSearch(String whereSearch) {
        this.whereSearch = whereSearch;
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

}
