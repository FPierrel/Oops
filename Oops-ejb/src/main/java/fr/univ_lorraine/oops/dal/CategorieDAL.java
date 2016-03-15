package fr.univ_lorraine.oops.dal;

import fr.univ_lorraine.oops.library.model.Categorie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class CategorieDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Méthode permettant d'ajouter une catégorie.
     *
     * @param c : catégorie à ajouter.
     * @return la catégorie ajoutée.
     */
    public Categorie add(Categorie c) {
        em.persist(c);
        return c;
    }

    /**
     * Méthode donnant une catégorie à partir de son identifiant.
     *
     * @param id : identifiant de la cétégorie voulue.
     * @return la catégorie voulue.
     */
    public Categorie get(Long id) {
        return em.find(Categorie.class, id);
    }

    /**
     * Méthode donnant toutes les catégories.
     *
     * @return la liste de toutes les catégories.
     */
    public List<Categorie> getAll() {
        String query = "SELECT c FROM Categorie c";
        return em.createQuery(query).getResultList();
    }

    /**
     * Méthode permettant de mettre à jour une catégorie.
     *
     * @param c : catégorie à mettre à jour.
     * @return la catégorie mise à jour.
     */
    public Categorie update(Categorie c) {
        return em.merge(c);
    }

    /**
     * Méthode donnant la liste des catégories à partir de leur nom.
     *
     * @param l : liste de noms de catégories voulues.
     * @return la liste des catégories voulues.
     */
    public Collection<Categorie> getFromList(List<String> l) {
        Collection<Categorie> c = new ArrayList<>();
        for (String s : l) {
            TypedQuery<Categorie> query = em.createNamedQuery("Categorie.findByName", Categorie.class);
            query.setParameter("name", s);
            Categorie cat = query.getSingleResult();
            c.add(cat);
        }
        return c;
    }

    /**
     * Méthode donnant la catégorie parente.
     *
     * @return la catégorie parente.
     */
    public Categorie getCategories() {
        Query query = em.createNamedQuery("Categorie.findByName");
        query.setParameter("name", "Toutes cat\u00e9gories");
        return (Categorie) query.getSingleResult();
    }

    /**
     * Méthode donnant la liste des catégories d'un prestataire.
     *
     * @param login : login du prestataire.
     * @return la liste des catégories du prestataire.
     */
    public List<Categorie> getCategoriesOfPrestataire(String login) {
        String queryString = "SELECT c "
                + "FROM Prestataire p, Categorie c "
                + "WHERE  p.login='" + login + "' AND c MEMBER OF p.categories";
        return em.createQuery(queryString, Categorie.class).getResultList();
    }
}
