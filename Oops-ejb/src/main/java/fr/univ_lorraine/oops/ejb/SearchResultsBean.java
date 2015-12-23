package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.ArrayList;
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
                + searchPrestataireWithLastname(lastname, "AND")
                + searchPrestataireWithFirstname(firstname, "AND")
                + searchPrestataireWithTown(town, "AND")
                + searchPrestataireWithEmployee(employee, "AND");
        Query query = this.getEntityManager().createQuery(queryString, Prestataire.class);
        return query.getResultList();
    }

    public String searchPrestataireWithLastname(String lastname, String operateur) {
        if (lastname.isEmpty()) {
            return "";
        }
        return " " + operateur + " p.nom = '" + lastname + "'";
    }

    public String searchPrestataireWithFirstname(String firstname, String operateur) {
        if (firstname.isEmpty()) {
            return "";
        }
        return " " + operateur + " p.prenom = '" + firstname + "'";
    }

    public String searchPrestataireWithTown(String town, String operateur) {
        //if(town.isEmpty())
        return "";
        //return " " + operateur + " p.adresse = '" + town + "'";
    }

    public String searchPrestataireWithEmployee(int employee, String operateur) {
        return " " + operateur + " p.nbEmployes >= " + employee + "";
    }

    public List<Prestataire> simpleSearch(String quoi, String ou) {
        /* Recherche Rapide
        recherche sur :
        nom de la boite
        nom
        prenom    
         */
        String queryString = "SELECT p "
                + "FROM Prestataire p "
                + "WHERE 1 = 1"
                + searchPrestataireWithEnterprisename(quoi, "AND");
        /* + searchPrestataireWithLastname(quoi, "OR")
                    + searchPrestataireWithFirstname(quoi, "OR")
                    + searchPrestataireWithTown(ou, "AND");*/

        Query query = this.getEntityManager().createQuery(queryString, Prestataire.class);
        return query.getResultList();
    }

    private String searchPrestataireWithEnterprisename(String quoi, String operateur) {
        if (quoi.isEmpty()) {
            return "";
        }

        String[] st = quoi.split(" ");
        StringBuilder sb = new StringBuilder(" " + operateur + "(");
        for (int i = 0; i < st.length; i++) {
            if (i == 0) {
                sb.append(" UPPER(p.nomEntreprise) LIKE '%" + st[i].toUpperCase() + "%' ");
            } else {
                sb.append(" OR UPPER(p.nomEntreprise) LIKE '%" + st[i].toUpperCase() + "%'");
            }
        }
        sb.append(" )");
        return sb.toString();

    }

}
