package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Categorie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class CategoriesBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public Categorie getCategories() {
        Query query = em.createNamedQuery("Categorie.findByName");
        query.setParameter("name", "Toutes cat\u00e9gories");
        return (Categorie) query.getSingleResult();
    }
    
      public List<String> getListCategories() {
        Categorie c = getCategories();
        return c.getListCategories(new ArrayList<String>(), 0);
    }
      
      public Collection<Categorie> getCategoriesFromListString(List<String> l){
          Collection<Categorie> c = new ArrayList<>();
          for (String s : l){
              TypedQuery<Categorie> query = this.getEntityManager().createNamedQuery("Categorie.findByName", Categorie.class);
              query.setParameter("name", s);
              Categorie cat = query.getSingleResult();
              c.add(cat);
          }
          return c;
      }
}
