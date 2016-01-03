package fr.univ_lorraine.oops.ejb;

import com.mysql.jdbc.Connection;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Resultat;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        ArrayList<Prestataire> results = new ArrayList<>();
        float scoreMin = 0.5f;
        //Liste des prestataires correspondant un minimum
        HashMap<String, Float> relevanceList = luceneBean.search(quoi);
        ArrayList<Resultat> relevanceList2 = new ArrayList<>();

        //Si le champs où est renseigné on cherche les presta. qui peuvent correspondre
        ArrayList<String> whereList;
        String queryString = "Select p "
                + "FROM Prestataire p, Adresse a "
                + "WHERE 1=1 "
                + searchPrestataireWithTownName(ou, "AND");
        Query query = this.getEntityManager().createQuery(queryString, Prestataire.class);
        if (!this.villes.isEmpty()) {
                query.setParameter("villes", this.villes);
        }
        whereList = (ArrayList<String>) query.getResultList();
        
        // On fait l'union des deux en gardant les prestataires avec un score honorable
        for (String s : whereList)
            if (relevanceList.containsKey(s))
                if (relevanceList.get(s) > scoreMin)
                    relevanceList2.add(new Resultat(s, relevanceList.get(s)));
        
        // Tri par ordre de pertinance
        Collections.sort(relevanceList2);
        Collections.reverse(relevanceList2);
        
        for (Resultat r : relevanceList2)
            results.add(em.find(Prestataire.class, r.getId()));
        
        return results;        
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

    public List<String> searchTownsByRadius(String ville, int radius) {
        List<String> li = new ArrayList<>();
        /* Connexion à la base de données */
        String url = "jdbc:mysql://test.pi-r-l.ovh:3306/oops";
        String utilisateur = "root";
        String motDePasse = "fakepwd88";
        Connection connexion = null;
        ResultSet resultat = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connexion = (Connection) DriverManager.getConnection(url, utilisateur, motDePasse);
            statement = connexion.createStatement();

            /* Récupérer latitude longitude de la ville de référence */
            resultat = statement.executeQuery(""
                    + "SELECT ville_latitude_deg, ville_longitude_deg "
                    + "FROM villes_france_free "
                    + "WHERE ville_nom_reel = '" + ville + "'");

            double latitude = 0;
            double longitude = 0;

            while (resultat.next()) {
                latitude = resultat.getDouble("ville_latitude_deg");
                longitude = resultat.getDouble("ville_longitude_deg");
            }

            /* Récupérer liste des villes à radius m à la ronde */
            resultat = statement.executeQuery(""
                    + "SELECT *, "
                    + "get_distance_metres('" + latitude + "', '" + longitude + "', ville_latitude_deg, ville_longitude_deg) AS distance "
                    + "FROM villes_france_free "
                    + "HAVING distance < " + radius);

            while (resultat.next()) {
                String nom = resultat.getString("ville_nom");
                li.add(nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchResultsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ignore) {
                }
            }
            if (resultat != null) {
                try {
                    resultat.close();
                } catch (SQLException ignore) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            }
        }

        return li;
    }

    private String searchPrestataireWithTownName(String ou, String operateur) {
        this.villes = this.searchTownsByRadius(ou, 10000);

        if (this.villes.isEmpty()) {
            return "";
        }

        return " " + operateur + " UPPER(a.ville) IN :villes AND a MEMBER OF p.adresses";
    }

    public List<String> searchTest(String search) {
        List<String> li = new ArrayList<>();
        /* Connexion à la base de données */
        String url = "jdbc:mysql://test.pi-r-l.ovh:3306/oops";
        String utilisateur = "root";
        String motDePasse = "fakepwd88";
        Connection connexion = null;
        ResultSet resultat = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connexion = (Connection) DriverManager.getConnection(url, utilisateur, motDePasse);
            statement = connexion.createStatement();

            /* Récupérer latitude longitude de la ville de référence */
            resultat = statement.executeQuery(""
                    + "SELECT ville_nom "
                    + "FROM villes_france_free "
                    + "WHERE ville_nom LIKE '" + search + "%' "
                    + "OR ville_code_postal LIKE '" + search + "%' "
                    + "ORDER BY ville_nom ");

            while (resultat.next()) {
                li.add(resultat.getString("ville_nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchResultsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ignore) {
                }
            }
            if (resultat != null) {
                try {
                    resultat.close();
                } catch (SQLException ignore) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            }
        }

        return li;
    }
}
