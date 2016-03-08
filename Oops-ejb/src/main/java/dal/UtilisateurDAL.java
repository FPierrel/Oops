package dal;

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
    
    public Utilisateur add(Utilisateur u){
        em.persist(u);
        return u;
    }
    
    public Utilisateur get(String login){
        return em.find(Utilisateur.class, login);
    }
    
    public List<Utilisateur> getAll(){
        String query = "SELECT u FROM Utilisateur u";
        return em.createQuery(query).getResultList();
    }
    
    public Utilisateur update(Utilisateur u){
        return em.merge(u);
    }  
}
