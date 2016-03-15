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

    public Categorie add(Categorie c) {
        em.persist(c);
        return c;
    }

    public Categorie get(Long id) {
        return em.find(Categorie.class, id);
    }

    public List<Categorie> getAll() {
        String query = "SELECT c FROM Categorie c";
        return em.createQuery(query).getResultList();
    }

    public Categorie update(Categorie c) {
        return em.merge(c);
    }

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

    public Categorie getCategories() {
        Query query = em.createNamedQuery("Categorie.findByName");
        query.setParameter("name", "Toutes cat\u00e9gories");
        return (Categorie) query.getSingleResult();
    }

    public List<Categorie> getCategoriesOfPrestataire(String login) {
        String queryString = "SELECT c "
                + "FROM Prestataire p, Categorie c "
                + "WHERE  p.login='" + login + "' AND c MEMBER OF p.categories";
        return em.createQuery(queryString, Categorie.class).getResultList();
    }
}
