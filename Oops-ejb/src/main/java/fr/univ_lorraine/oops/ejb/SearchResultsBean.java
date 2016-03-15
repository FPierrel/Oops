package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Categorie;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Resultat;
import fr.univ_lorraine.oops.rest.Geocoding;
import fr.univ_lorraine.oops.rest.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class SearchResultsBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    private List<String> villes = new ArrayList<>();
    @Inject
    private LuceneBean luceneBean;

    /**
     * Getter of the EntityManager
     * @return the EntityManager
     */
    public EntityManager getEntityManager() {
        return this.em;
    }

    /**
     * Return a List of Prestataire matching the parameters
     * @param what a keyword for the Prestataire
     * @param where the city of the Prestataire
     * @param postalCode the zipcode of the Prestataire
     * @param lastname the lastname of the Prestataire
     * @param firstname the firstname of the Prestataire
     * @param employee the number of employee of the Prestataire
     * @param raisonSociale the corporate name of the Prestataire
     * @param formesJuridiques the legal form of the Prestataire
     * @param chiffreAffaire the turnover og the Prestataire
     * @param communication the minimum grade in communication of the Prestataire
     * @param quality the minimum grade in quality of the Prestataire
     * @param price the minimum grade in price of the Prestataire
     * @param delay the minimum grade in delay of the Prestataire
     * @param moyenne the minimum grade in average of the Prestataire
     * @param categories a List of String of job types the Prestataire does
     * @return a List of Prestataire matching the parameters
     */
    public List<Prestataire> search(String what, String where, String postalCode, String lastname, String firstname, int employee, String raisonSociale, List<String> formesJuridiques, int chiffreAffaire, int communication, int quality, int price, int delay, int moyenne, List<String> categories) {
        String queryString = "SELECT DISTINCT p "
                + "FROM Prestataire p"
                + ((!where.isEmpty()) ? ", Adresse a" : "")
                + ", Categorie c "
                + "WHERE 1 = 1"
                + searchPrestataireWithLastname(lastname, "AND")
                + searchPrestataireWithFirstname(firstname, "AND")
                + searchPrestataireWithEmployee(employee, "AND")
                + searchPrestataireWithFormesJuridiques(formesJuridiques, "AND")
                + searchPrestataireWithChiffreAffaire(chiffreAffaire, "AND")
                + searchPrestataireWithCommunication(communication, "AND")
                + searchPrestataireWithQuality(quality, "AND")
                + searchPrestataireWithPrice(price, "AND")
                + searchPrestataireWithValue(delay, "AND")
                + searchPrestataireWithAverage(moyenne, "AND");
        if (!categories.isEmpty()) {
            queryString += " AND (";
        }
        boolean b = true;
        for (String cat : categories) {
            queryString += searchPrestataireWithCategorie(cat, "OR", b);
            b = false;
        }

        if (!categories.isEmpty()) {
            queryString += " )";
        }

        if (!what.isEmpty()) {
            queryString += searchPrestataireWithEnterprisename(what, "AND");
        }

        villes.clear();
        if (!where.isEmpty()) {
            queryString += searchPrestataireWithTownName(where, postalCode, "AND");
        }

        queryString += " ORDER BY p.average DESC";
        Query query = this.getEntityManager().createQuery(queryString, Prestataire.class);
        
        if (!this.villes.isEmpty()) {
            query.setParameter("villes", this.villes);
        }

        List<Prestataire> l = query.getResultList();
        this.setCoordinates(l);

        return l;
    }

    /**
     * Create part of the SQL query for the lastname of the Prestataire
     * @param lastname the lastname of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the lastname of a Prestataire
     */
    public String searchPrestataireWithLastname(String lastname, String operateur) {
        if (lastname.isEmpty()) {
            return "";
        }
        return " " + operateur + " UPPER(p.nom) = '" + lastname.toUpperCase() + "'";
    }

    /**
     * Create part of the SQL query for the firstname of the Prestataire
     * @param firstname the firstname of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the firstname of a Prestataire
     */
    public String searchPrestataireWithFirstname(String firstname, String operateur) {
        if (firstname.isEmpty()) {
            return "";
        }
        return " " + operateur + " UPPER(p.prenom) = '" + firstname.toUpperCase() + "'";
    }

    /**
     * Create part of the SQL query for the number of employees of the Prestataire
     * @param employee the number of employee of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the number of employees of a Prestataire
     */
    public String searchPrestataireWithEmployee(int employee, String operateur) {
        return " " + operateur + " p.nbEmployes >= " + employee;
    }

    /**
     * Create part of the SQL query for the number of employees of the Prestataire
     * @param raisonSociale the corporate name of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the corporate name of a Prestataire
     */
    public String searchPrestataireWithRaisonSociale(String raisonSociale, String operateur) {
        if (raisonSociale.isEmpty()) {
            return "";
        }
        return "";
        //return " " + operateur + " UPPER(p.raisonSociale) = '" + raisonSociale.toUpperCase() + "'";
    }

    /**
     * Create part of the SQL query for the legal form of the Prestataire
     * @param formesJuridiques the list of String legal form of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the List of String legal form of a Prestataire
     */
    public String searchPrestataireWithFormesJuridiques(List<String> formesJuridiques, String operateur) {
        if (formesJuridiques.isEmpty()) {
            return "";
        }
        String queryString = " " + operateur + " ( 1!=1";
        for (int i = 0 ; i < formesJuridiques.size() ; i++ ) {
            queryString += searchPrestataireWithFormeJuridique(formesJuridiques.get(i), "OR");
        }        
        queryString += " )";
        return queryString;
    }
    
    /**
     * Create part of the SQL query for the legal form of the Prestataire
     * @param type the legal form of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the legal form of a Prestataire
     */
    public String searchPrestataireWithFormeJuridique(String type, String operateur) {
        return " " + operateur + " p.formeJuridique =  '" + type + "'";
    }

    /**
     * Create part of the SQL query for the turnover of the Prestataire
     * @param chiffreAffaire the turnover of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for turnover of a Prestataire
     */
    public String searchPrestataireWithChiffreAffaire(int chiffreAffaire, String operateur) {
        return " " + operateur + " p.chiffreAffaire >= " + chiffreAffaire;
    }

    /**
     * Create part of the SQL query for the minimum grade in communication of the Prestataire
     * @param communication the minimum grade in communication of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the minimum grade in communication of a Prestataire
     */
    public String searchPrestataireWithCommunication(int communication, String operateur) {
        return " " + operateur + " p.communication >= " + communication;
    }

     /**
     * Create part of the SQL query for the minimum grade in quality of the Prestataire
     * @param quality the minimum grade in quality of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the minimum grade in quality of a Prestataire
     */
    public String searchPrestataireWithQuality(int quality, String operateur) {
        return " " + operateur + " p.quality >= " + quality;
    }

    /**
     * Create part of the SQL query for the minimum grade in price of the Prestataire
     * @param price the minimum grade in price of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the minimum grade in price of a Prestataire
     */
    public String searchPrestataireWithPrice(int price, String operateur) {
        return " " + operateur + " p.price >= " + price;
    }

    /**
     * Create part of the SQL query for the minimum grade in delay of the Prestataire
     * @param delay the minimum grade in delay of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the minimum grade in delay of a Prestataire
     */
    public String searchPrestataireWithValue(int delay, String operateur) {
        return " " + operateur + " p.delay >= " + delay;
    }

    /**
     * Create part of the SQL query for the minimum grade in average of the Prestataire
     * @param average the minimum grade in average of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the minimum grade in average of a Prestataire
     */
    public String searchPrestataireWithAverage(int average, String operateur) {
        return " " + operateur + " p.average >= " + average;
    }

    /**
     * Create part of the SQL query for type of jobs a Prestataire offers
     * @param categorie the type of job
     * @param operateur the operator for the query, OR or AND
     * @param firstCategory a boolean used to format the query
     * @return a String, part of a sql query for the legal form of a Prestataire
     */
    private String searchPrestataireWithCategorie(String categorie, String operateur, boolean firstCategory) {
        return " " + ((firstCategory) ? "" : operateur) + " ( c IN (p.categories) AND '" + categorie + "' MEMBER OF c.motsCles )";
    }

    /**
     * Return a List of Prestataire matching the parameters
     * @param quoi a keyword for the Prestataire
     * @param ou the city of the Prestataire
     * @param codePostal the zipcode of the Prestataire
     * @return a List of Prestataire matching the parameters
     */
    public List<Prestataire> simpleSearch(String quoi, String ou, String codePostal) {
        ArrayList<Prestataire> results = new ArrayList<>();

        //Si le champs quoi est pas renseigné on s'embete pas
        if (quoi.isEmpty() && !ou.isEmpty()) {
            String queryString = "Select p "
                    + "FROM Prestataire p, Adresse a "
                    + "WHERE 1=1 "
                    + searchPrestataireWithTownName(ou, codePostal, "AND");
            Query query = this.getEntityManager().createQuery(queryString, Prestataire.class);

            if (!this.villes.isEmpty()) {
                query.setParameter("villes", this.villes);
            }

            List<Prestataire> l = query.getResultList();
            this.setCoordinates(l);

            return l;
        }

        float scoreMin = 0.9f;
        //Liste des prestataires correspondant un minimum
        HashMap<String, Float> relevanceList = luceneBean.search(quoi);
        ArrayList<Resultat> relevanceList2 = new ArrayList<>();

        //Si le champs où est renseigné on cherche les presta. qui peuvent correspondre
        List<String> whereList;
        String queryString = "SELECT DISTINCT p.login "
                + "FROM Prestataire p, Adresse a "
                + "WHERE 1=1 "
                + searchPrestataireWithTownName(ou, codePostal, "AND");
        Query query = this.getEntityManager().createQuery(queryString, Prestataire.class);
        if (!this.villes.isEmpty()) {
            query.setParameter("villes", this.villes);
        }
        whereList = query.getResultList();

        // On fait l'union des deux en gardant les prestataires avec un score honorable
        for (String s : whereList) {
            if (relevanceList.containsKey(s)) {
                if (relevanceList.get(s) > scoreMin) {
                    relevanceList2.add(new Resultat(s, relevanceList.get(s)));
                }
            }
        }

        // Tri par ordre de pertinance
        Collections.sort(relevanceList2);
        Collections.reverse(relevanceList2);

        for (Resultat r : relevanceList2) {
            results.add(em.find(Prestataire.class, r.getId()));
        }

        List<Prestataire> l = results;
        this.setCoordinates(l);
        return l;
    }

     /**
     * Create part of the SQL query for the company name of the Prestataire
     * @param quoi the company name of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the company name of a Prestataire
     */
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

    /**
     * Create part of the SQL query for the radius of the search of the List of Prestataire
     * @param ville the town used as a center for the search by radius
     * @param codePostal the zipcode used as a center for the search by radius
     * @param radius radius of the search in kilometers
     * @return a String, part of a sql query for the minimum grade in average of a Prestataire
     */
    public List<String> searchTownsByRadius(String ville, String codePostal, int radius) {
        List<String> li = new ArrayList<>();

        try {
            String query = "CALL geodist('" + ville + "','" + codePostal + "'," + radius + ")";
            ResultSet resultat = MySQL.getInstance().search(query);

            while (resultat.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(resultat.getString("ville_nom")).append(" ");
                sb.append("(").append(resultat.getString("ville_code_postal")).append(")");
                li.add(sb.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchResultsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

     /**
     * Create part of the SQL query for the location of the Prestataire
     * @param ou the city name of the Prestataire
     * @param codePostal the zipcode of the Prestataire
     * @param operateur the operator for the query, OR or AND
     * @return a String, part of a sql query for the legal form of a Prestataire
     */
    private String searchPrestataireWithTownName(String ou, String codePostal, String operateur) {
        this.villes = this.searchTownsByRadius(ou, codePostal, 20);
        
        if(ou.isEmpty()){
            return "";
        }

        if (this.villes.isEmpty()) {
            return " " + operateur + " UPPER(a.ville) = \"" + ou + "\"";
        }

        return " " + operateur + " UPPER(a.ville) IN :villes AND a MEMBER OF p.adresses";
    }

     /**
     * A test function
     * @param search
     * @return
     */
    public List<String> searchTest(String search) {
        try {
            List<String> li = new ArrayList<>();
            String query = ""
                    + "SELECT ville_nom, ville_code_postal "
                    + "FROM villes_france_free "
                    + "WHERE ville_nom LIKE '" + search + "%' "
                    + "OR ville_code_postal LIKE '" + search + "%' "
                    + "ORDER BY ville_nom LIMIT 5 ";

            ResultSet resultat = MySQL.getInstance().search(query);

            while (resultat.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(resultat.getString("ville_nom")).append(" ");
                sb.append("(").append(resultat.getString("ville_code_postal")).append(")");
                li.add(sb.toString());
            }

            return li;
        } catch (SQLException ex) {
            Logger.getLogger(SearchResultsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get the Categorie "Toutes catégories"
     * @return the Categorie "Toutes catégories"
     */
    public Categorie getCategories() {
        Query query = em.createNamedQuery("Categorie.findByName");
        query.setParameter("name", "Toutes cat\u00e9gories");
        return (Categorie) query.getSingleResult();
    }

     /**
     * Get the name of all cities in France matching the parameter
     * @param search the city name to match
     * @return a List of String, all the names of the cities in France matching the parameter
     */
    public List<String> searchTownWithoutCode(String search) {
        List<String> li = new ArrayList<>();

        try {
            /* Récupérer latitude longitude de la ville de référence */
            String query = ""
                    + "SELECT ville_nom "
                    + "FROM villes_france_free "
                    + "WHERE ville_nom LIKE '" + search + "%' ";

            ResultSet resultat = MySQL.getInstance().search(query);

            while (resultat.next()) {
                li.add(resultat.getString("ville_nom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchResultsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

     /**
     * Get the name of zipcodes of the French city in parameter
     * @param town the city name to match
     * @return a List of String, all the zipcodes in France matching the city in parameter
     */
    public List<String> searchCodes(String town) {
        List<String> li = new ArrayList<>();

        try {
            String query = ""
                    + "SELECT ville_code_postal "
                    + "FROM villes_france_free "
                    + "WHERE ville_nom = '" + town + "' ";

            ResultSet resultat = MySQL.getInstance().search(query);

            while (resultat.next()) {
                for (String code : resultat.getString("ville_code_postal").split("-")) {
                    li.add(code);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchResultsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

    /**
     * Add coordinates to all of the addresses of a Prestataire using geo location
     * @param pres the Prestataire to geo locate
     */
    public void setCoordinates(List<Prestataire> pres) {
        Geocoding geo = new Geocoding();

        for (Prestataire p : pres) {
            for (Adresse a : p.getAdresses()) {
                String ville = a.getVille().substring(0, a.getVille().indexOf("(") - 1);
                String[] coords = geo.callGetCoordinates(a.getNumero() + " " + a.getRue() + " " + ville);
                a.setLatitude(coords[0]);
                a.setLongitude(coords[1]);
            }
        }
    }
}
