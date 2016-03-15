package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.CategorieDAL;
import fr.univ_lorraine.oops.library.model.Categorie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;

@Stateless
@LocalBean
public class CategoriesBean {

    @Inject
    CategorieDAL cd;

    /**
     * Méthode donnant les catégories à travers la catégorie parente.
     *
     * @return les catégories.
     */
    public Categorie getCategories() {
        return cd.getCategories();
    }

    /**
     * Méthode donnant les noms de toutes les catégories et sous-catégories.
     *
     * @return la liste des noms de toutes les catégories et sous-catégories.
     */
    public List<String> getListCategories() {
        Categorie c = getCategories();
        return c.getListCategories(new ArrayList<String>(), 0);
    }

    /**
     * Méthode donnant les catégories voulues à partir de leur nom.
     *
     * @param l : liste des noms de catégories voulues.
     * @return liste des catégories voulues.
     */
    public Collection<Categorie> getCategoriesFromListString(List<String> l) {
        return cd.getFromList(l);
    }
}
