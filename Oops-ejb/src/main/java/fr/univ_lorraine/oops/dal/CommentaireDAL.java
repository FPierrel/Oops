package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Commentaire;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class CommentaireDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public Commentaire add(Commentaire c){
        em.persist(c);
        return c;
    }
    
    public Commentaire get(Long id){
        return em.find(Commentaire.class, id);
    }
    
    public List<Commentaire> getAll(){
        String query = "SELECT c FROM Commentaire c";
        return em.createQuery(query).getResultList();
    }
    
    public Commentaire update(Commentaire c){
        return em.merge(c);
    }  
    
    public void delete(Commentaire c){
        em.remove(em.find(Commentaire.class,c.getId()));
        
    }
}
