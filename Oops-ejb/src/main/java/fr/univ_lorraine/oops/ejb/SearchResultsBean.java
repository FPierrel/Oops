/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Romain
 */
@Stateless
@LocalBean
public class SearchResultsBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    List<Prestataire> lastnameRes;
    List<Prestataire> firstnameRes;
    List<Prestataire> townRes;
    List<Prestataire> ratingRes;
    List<Prestataire> employeeRes;

    private int numberOfSearches;

    public ArrayList<Prestataire> search(String lastnameSearch, String firstnameSearch, String townSearch, int employeeSearch, float ratingSearch) {
        numberOfSearches = 0;
        lastnameRes = new ArrayList<>();
        firstnameRes = new ArrayList<>();
        townRes = new ArrayList<>();
        ratingRes = new ArrayList<>();
        employeeRes = new ArrayList<>();

        if (!firstnameSearch.isEmpty()) {
            searchPrestataireByFirstname(firstnameSearch);
        }
        if (!lastnameSearch.isEmpty()) {
            searchPrestataireByLastname(lastnameSearch);
        }
        searchByEmployee(employeeSearch);
        searchByRating(ratingSearch);

        return computeResults();
    }

    public ArrayList<Prestataire> computeResults() {
        ArrayList<Prestataire> l = new ArrayList<>();
        for (Prestataire p : ratingRes) {
            int nb = 0;
            if (lastnameRes.contains(p)) {
                nb++;
            }
            if (firstnameRes.contains(p)) {
                nb++;
            }
            if (townRes.contains(p)) {
                nb++;
            }
            if (employeeRes.contains(p)) {
                nb++;
            }

            if (numberOfSearches == nb) {
                l.add(p);
            }
        }
        return l;
    }

    public void searchPrestataireByLastname(String lastname) {

        Query query = em.createNamedQuery("Prestataire.findByLastname");
        query.setParameter("lastname", lastname);
        lastnameRes = query.getResultList();
        numberOfSearches++;
    }

    public void searchPrestataireByFirstname(String firstname) {
        Query query = em.createNamedQuery("Prestataire.findByFirstname");
        query.setParameter("firstname", firstname);
        firstnameRes = query.getResultList();
        numberOfSearches++;
    }

    public void searchByTown(String town) {
        Query query = em.createNamedQuery("Prestataire.findByTown");
        query.setParameter("town", town);
        townRes = query.getResultList();
        numberOfSearches++;
    }

    public void searchByRating(float rating) {
        Query query = em.createNamedQuery("Prestataire.findByRating");
        query.setParameter("rating", rating);
        ratingRes = query.getResultList();
    }

    public void searchByEmployee(int employee) {
        Query query = em.createNamedQuery("Prestataire.findByEmployee");
        query.setParameter("employee", employee);
        employeeRes = query.getResultList();
        numberOfSearches++;
    }
}
