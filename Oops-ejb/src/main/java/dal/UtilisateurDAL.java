package dal;

import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class UtilisateurDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Utilisateur add(Utilisateur u) {
        em.persist(u);
        return u;
    }

    public Utilisateur get(String login) {
        return em.find(Utilisateur.class, login);
    }

    public List<Utilisateur> getAll() {
        String query = "SELECT u FROM Utilisateur u";
        return em.createQuery(query).getResultList();
    }

    public Utilisateur update(Utilisateur u) {
        return em.merge(u);
    }

    public List<Utilisateur> getBanishedUser() {
        String queryString = "SELECT u "
                + "FROM Utilisateur u "
                + "WHERE  u.banished='" + true + "'";
        return em.createQuery(queryString, Utilisateur.class).getResultList();
    }
    
    public Utilisateur getUserByEmail(String mail){
        String queryString = "SELECT u "
                + "FROM Utilisateur u "
                + "WHERE  u.mail='" + mail+ "'";
        return em.createQuery(queryString, Utilisateur.class).getSingleResult();
    }

    public boolean mailExist(String mail) {
        String queryString = "SELECT u.mail "
                + "FROM Utilisateur u "
                + "WHERE u.mail = '" + mail + "'";
        return (em.createQuery(queryString, String.class).getResultList().size() > 0);
    }
    
    public List<Prestataire> getBestGrades(int number) {
        String query = "SELECT p FROM Prestataire p ORDER BY p.average desc";
        return em.createQuery(query).setMaxResults(number).getResultList();
    }
    
}
