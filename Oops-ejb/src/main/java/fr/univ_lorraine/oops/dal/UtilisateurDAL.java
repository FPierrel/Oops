package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UtilisateurDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Add a Utilisateur to the database
     * @param u the Utilisateur to add
     * @return the Utilisateur added
     */
    public Utilisateur add(Utilisateur u) {
        em.persist(u);
        return u;
    }

    /**
     * Get the Utilisateur with the login in parameter from the databse
     * @param login login of the Utilisateur
     * @return the Utilisateur with the login in parameter if it exists, else null
     */
    public Utilisateur get(String login) {
        return em.find(Utilisateur.class, login);
    }

    /**
     * Get all Utilisateur from the database
     * @return the List of Utilisateur
     */
    public List<Utilisateur> getAll() {
        String query = "SELECT u FROM Utilisateur u";
        return em.createQuery(query).getResultList();
    }

    /**
     * Update a Utilisateur in the database
     * @param u Utilisateur to update
     * @return the Utilisateur updated
     */
    public Utilisateur update(Utilisateur u) {
        return em.merge(u);
    }

    /**
     * Get all the Utilisateur banished from the database
     * @return the List of Utilisateur banished
     */
    public List<Utilisateur> getBanishedUser() {
        String queryString = "SELECT u "
                + "FROM Utilisateur u "
                + "WHERE  u.banished='" + true + "'";
        return em.createQuery(queryString, Utilisateur.class).getResultList();
    }
    
    /**
     * Get a Utilisateur from his email address
     * @param mail the email address of the Utilisateur
     * @return the Utilisateur matching the address if it exists or null
     */
    public Utilisateur getUserByEmail(String mail){
        String queryString = "SELECT u "
                + "FROM Utilisateur u "
                + "WHERE  u.mail='" + mail+ "'";
        return em.createQuery(queryString, Utilisateur.class).getSingleResult();
    }

    /**
     * Test if an email address exists in the database
     * @param mail the email address to test
     * @return true if the email address exists in the database, else false
     */
    public boolean mailExist(String mail) {
        String queryString = "SELECT u.mail "
                + "FROM Utilisateur u "
                + "WHERE u.mail = '" + mail + "'";
        return (em.createQuery(queryString, String.class).getResultList().size() > 0);
    }
    
    /**
     * Get the 'n' Prestataire with the highest average grades
     * @param number number of Prestataire to return
     * @return a List of Prestataire with the best average grades
     */
    public List<Prestataire> getBestGrades(int number) {
        String query = "SELECT p FROM Prestataire p ORDER BY p.average desc";
        return em.createQuery(query).setMaxResults(number).getResultList();
    }
    
}
