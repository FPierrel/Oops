package dal;

import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class PrestataireDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public Prestataire add(Prestataire p){
        em.persist(p);
        return p;
    }
    
    public Prestataire get(String login){
        return em.find(Prestataire.class, login);
    }
    
    public List<Prestataire> getAll(){
        String query = "SELECT p FROM Prestataire p";
        return em.createQuery(query).getResultList();
    }
    
    public Prestataire update(Prestataire p){
        return em.merge(p);
    }  
}
