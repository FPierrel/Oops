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

    public EntityManager getEntityManager() {
        return this.em;
    }

    public List<Prestataire> search(String what, String where, String postalCode, String lastname, String firstname, int employee, String raisonSociale, String formeJuridique, int chiffreAffaire, int communication, int quality, int price, int delay, int moyenne, String categorie) {
        String queryString = "SELECT DISTINCT p "
                + "FROM Prestataire p"
                + ((!where.isEmpty()) ? ", Adresse a" : "")
                + ", Categorie c "
                + "WHERE 1 = 1"
                + searchPrestataireWithLastname(lastname, "AND")
                + searchPrestataireWithFirstname(firstname, "AND")
                + searchPrestataireWithEmployee(employee, "AND")
                + searchPrestataireWithRaisonSociale(raisonSociale, "AND")
                + searchPrestataireWithFormeJuridique(formeJuridique, "AND")
                + searchPrestataireWithChiffreAffaire(chiffreAffaire, "AND")
                + searchPrestataireWithCommunication(communication, "AND")
                + searchPrestataireWithQuality(quality, "AND")
                + searchPrestataireWithPrice(price, "AND")
                + searchPrestataireWithValue(delay, "AND")
                + searchPrestataireWithAverage(moyenne, "AND")
                + searchPrestataireWithCategorie(categorie, "AND");

        if (!what.isEmpty()) {
            queryString += searchPrestataireWithEnterprisename(what, "AND");
        }

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

    public String searchPrestataireWithLastname(String lastname, String operateur) {
        if (lastname.isEmpty()) {
            return "";
        }
        return " " + operateur + " UPPER(p.nom) = '" + lastname.toUpperCase() + "'";
    }

    public String searchPrestataireWithFirstname(String firstname, String operateur) {
        if (firstname.isEmpty()) {
            return "";
        }
        return " " + operateur + " UPPER(p.prenom) = '" + firstname.toUpperCase() + "'";
    }

    public String searchPrestataireWithEmployee(int employee, String operateur) {
        return " " + operateur + " p.nbEmployes >= " + employee;
    }

    public String searchPrestataireWithRaisonSociale(String raisonSociale, String operateur) {
        if (raisonSociale.isEmpty()) {
            return "";
        }
        return "";
        //return " " + operateur + " UPPER(p.raisonSociale) = '" + raisonSociale.toUpperCase() + "'";
    }

    public String searchPrestataireWithFormeJuridique(String formeJuridique, String operateur) {
        if (formeJuridique.isEmpty()) {
            return "";
        }
        return "";
        //return " " + operateur + " UPPER(p.formeJuridique) = '" + formeJuridique.toUpperCase() + "'";
    }

    public String searchPrestataireWithChiffreAffaire(int chiffreAffaire, String operateur) {
        return " " + operateur + " p.chiffreAffaire >= " + chiffreAffaire;
    }

    public String searchPrestataireWithCommunication(int communication, String operateur) {
        return " " + operateur + " p.communication >= " + communication;
    }

    public String searchPrestataireWithQuality(int quality, String operateur) {
        return " " + operateur + " p.quality >= " + quality;
    }

    public String searchPrestataireWithPrice(int price, String operateur) {
        return " " + operateur + " p.price >= " + price;
    }

    public String searchPrestataireWithValue(int delay, String operateur) {
        return " " + operateur + " p.delay >= " + delay;
    }

    public String searchPrestataireWithAverage(int average, String operateur) {
        return " " + operateur + " p.average >= " + average;
    }

    private String searchPrestataireWithCategorie(String categorie, String operateur) {
        return " " + operateur + " c IN (p.categories) AND '" + categorie + "' MEMBER OF c.motsCles";
    }

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

    public List<String> searchTownsByRadius(String ville, String codePostal, int radius) {
        List<String> li = new ArrayList<>();

        try {
            String query = "CALL geodist('" + ville + "','" + codePostal + "'," + radius + ")";
            ResultSet resultat = MySQL.getInstance().search(query);

            while (resultat.next()) {
                String nom = resultat.getString("ville_nom");
                li.add(nom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchResultsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

    private String searchPrestataireWithTownName(String ou, String codePostal, String operateur) {
        this.villes = this.searchTownsByRadius(ou, codePostal, 20);

        if (this.villes.isEmpty()) {
            return "";
        }

        return " " + operateur + " UPPER(a.ville) IN :villes AND a MEMBER OF p.adresses";
    }

    public List<String> searchTest(String search) {
        try {
            List<String> li = new ArrayList<>();
            String query = ""
                    + "SELECT ville_nom, ville_code_postal "
                    + "FROM villes_france_free "
                    + "WHERE ville_nom LIKE '" + search + "%' "
                    + "OR ville_code_postal LIKE '" + search + "%' "
                    + "ORDER BY ville_nom ";

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

    public Categorie getCategories() {
        Query query = em.createNamedQuery("Categorie.findByName");
        query.setParameter("name", "Toutes cat\u00e9gories");
        return (Categorie) query.getSingleResult();
    }

    public List<String> searchTownWithoutCode(String search) {
        List<String> li = new ArrayList<>();

        try {
            /* Récupérer latitude longitude de la ville de référence */
            String query = ""
                    + "SELECT ville_nom "
                    + "FROM villes_france_free "
                    + "WHERE ville_nom LIKE '%" + search + "%' ";

            ResultSet resultat = MySQL.getInstance().search(query);

            while (resultat.next()) {
                li.add(resultat.getString("ville_nom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchResultsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

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

    public void setCoordinates(List<Prestataire> pres) {
        Geocoding geo = new Geocoding();

        for (Prestataire p : pres) {
            for (Adresse a : p.getAdresses()) {
                String[] coords = geo.callGetCoordinates(a.getNumero() + " " + a.getRue() + " " + a.getVille());
                a.setLatitude(coords[0]);
                a.setLongitude(coords[1]);
            }
        }
    }
}
