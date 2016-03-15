package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.CategorieDAL;
import fr.univ_lorraine.oops.library.model.Categorie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class CategoriesBean {

    @Inject
    CategorieDAL cd;

    public Categorie getCategories() { //TODO nom non explicite
        return cd.getCategories();
    }
    
      public List<String> getListCategories() {
        Categorie c = getCategories();
        return c.getListCategories(new ArrayList<String>(), 0);
    }
      
      public Collection<Categorie> getCategoriesFromListString(List<String> l){
          return cd.getFromList(l);
      }
}
