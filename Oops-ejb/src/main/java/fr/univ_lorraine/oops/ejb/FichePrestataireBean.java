package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class FichePrestataireBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public EntityManager getEntityManager() { 
        return this.em; 
    }

    public Prestataire getPrestataireLogin(String login) {
        Prestataire prestataire = this.getEntityManager().find(Prestataire.class, login);
        return prestataire;
    }
    
    public List<Avis> getPrestataireAvis(String login) {
        String queryString = "SELECT DISTINCT p.cAvis "
                + "FROM Prestataire p "
                + "WHERE  p.login='"+login+"'"
                ;
        TypedQuery<Avis> query = this.getEntityManager().createQuery(queryString, Avis.class);
        return query.getResultList();
    }

    /*public double getNoteGlobCom(String login) {
        String queryString = "SELECT AVG(CAST(a.noteCom as double)) FROM Avis a WHERE a.pLogin='"+login+"'" ; 
        Query query = this.getEntityManager().createQuery(queryString, double.class);
        Double test = (Double) query.getSingleResult();
        if (test != null) {
            System.out.println(test.doubleValue());
            return test.doubleValue() ;
        } else {
            return 0 ; 
        }
    }
    
    public double getNoteGlobDelai(String login) {
        String queryString = "SELECT AVG(CAST(a.noteDelai as double)) FROM Avis a WHERE a.pLogin='"+login+"'" ; 
        Query query = this.getEntityManager().createQuery(queryString, double.class);
        Double test = (Double) query.getSingleResult();
        if (test != null) {
            return test.doubleValue() ;
        } else {
            return 0 ; 
        }
    }
    
    public double getNoteGlobQualite(String login) {
        String queryString = "SELECT AVG(CAST(a.noteQualite as double)) FROM Avis a WHERE a.pLogin='"+login+"'" ; 
        Query query = this.getEntityManager().createQuery(queryString, double.class);
        Double test = (Double) query.getSingleResult();
        if (test != null) {
            System.out.println(test.doubleValue());
            return test.doubleValue() ;
        } else {
            return 0 ; 
        }
    }
    
    public double getNoteGlobPrix(String login) {
        String queryString = "SELECT AVG(CAST(a.notePrix as double)) FROM Avis a WHERE a.pLogin='"+login+"'" ; 
        Query query = this.getEntityManager().createQuery(queryString, double.class);
        Double test = (Double) query.getSingleResult();
        if (test != null) {
            System.out.println(test.doubleValue());
            return test.doubleValue() ;
        } else {
            return 0 ; 
        }
    }*/

    
}
