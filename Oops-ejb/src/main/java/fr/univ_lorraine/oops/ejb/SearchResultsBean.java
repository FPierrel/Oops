/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
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

    private String q;

    public List<Prestataire> search(String lastnameSearch, String firstnameSearch, String townSearch, int employeeSearch, float ratingSearch) {
        q = "SELECT p FROM Prestataire p WHERE 1 = 1";
        if (!firstnameSearch.isEmpty()) {
            searchPrestataireByFirstname(firstnameSearch);
        }
        if (!lastnameSearch.isEmpty()) {
            searchPrestataireByLastname(lastnameSearch);
        }
        if (!townSearch.isEmpty()) {
            searchByTown(townSearch);
        }
        searchByEmployee(employeeSearch);
        searchByRating(ratingSearch);

        return computeResults();
    }

    public List<Prestataire> computeResults() {
        Query query = em.createQuery(q, Prestataire.class);
        return query.getResultList();
    }

    public void searchPrestataireByLastname(String lastname) {
        q += " AND p.nom = '" + lastname + "'";
    }

    public void searchPrestataireByFirstname(String firstname) {
        q += " AND p.prenom = '" + firstname + "'";
    }

    public void searchByTown(String town) {
        //q += " AND p.adresse.ville = '"+town+"'";
    }

    public void searchByRating(float rating) {
        q += " AND p.rating >= '" + rating + "'";
    }

    public void searchByEmployee(int employee) {
        q += " AND p.nbEmployes >= " + employee;
    }
}
