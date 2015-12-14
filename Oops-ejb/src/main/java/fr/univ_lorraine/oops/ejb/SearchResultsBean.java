package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class SearchResultsBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public EntityManager getEntityManager() { 
        return this.em; 
    }
    
    public List<Prestataire> search(String lastname, String firstname, String town, int employee) {
        String queryString = "SELECT p "
                    + "FROM Prestataire p "
                    + "WHERE 1 = 1"
                    + searchPrestataireWithLastname(lastname)
                    + searchPrestataireWithFirstname(firstname)
                    + searchPrestataireWithTown(town)
                    + searchPrestataireWithEmployee(employee);
        Query query = this.getEntityManager().createQuery(queryString, Prestataire.class);
        return query.getResultList();
    }
    
    public String searchPrestataireWithLastname(String lastname) {
        if(lastname.isEmpty())
            return "";
        return " AND p.nom = '" + lastname + "'";
    }
    
    public String searchPrestataireWithFirstname(String firstname) {
        if(firstname.isEmpty())
            return "";
        return " AND p.prenom = '" + firstname + "'";
    }
    
    public String searchPrestataireWithTown(String town) {
        //if(town.isEmpty())
            return "";
        //return " AND p.adresse = '" + town + "'";
    }
    
    public String searchPrestataireWithEmployee(int employee) {
        return " AND p.nbEmployes >= " + employee + "";
    }
    
}
