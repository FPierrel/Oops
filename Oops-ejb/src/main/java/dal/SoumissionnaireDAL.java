package dal;

import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class SoumissionnaireDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public Soumissionnaire add(Soumissionnaire s){
        em.persist(s);
        return s;
    }
    
    public Soumissionnaire get(String login){
        return em.find(Soumissionnaire.class, login);
    }
    
    public List<Soumissionnaire> getAll(){
        String query = "SELECT s FROM Soumissionnaire s";
        return em.createQuery(query).getResultList();
    }
    
    public Soumissionnaire update(Soumissionnaire s){
        return em.merge(s);
    }  
}
