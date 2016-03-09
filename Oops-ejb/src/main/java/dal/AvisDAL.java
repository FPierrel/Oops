package dal;

import fr.univ_lorraine.oops.library.model.Avis;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class AvisDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Avis add(Avis a) {
        em.persist(a);
        return a;
    }

    public Avis get(Long id) {
        return em.find(Avis.class, id);
    }

    public List<Avis> getAll() {
        String query = "SELECT a FROM Avis a";
        return em.createQuery(query).getResultList();
    }

    public List<Avis> getAllUnverified() {
        String queryString = "SELECT a "
                + "FROM Avis a "
                + "WHERE  a.moderated='" + false + "'";
        return em.createQuery(queryString, Avis.class).getResultList();
    }

    public Avis update(Avis a) {
        return em.merge(a);
    }

    public void delete(Avis a) {
        em.remove(a);
    }

    public List<Avis> getAvisPrestatire(String loginPrestataire) {
        String queryString = "SELECT p.cAvis "
                + "FROM Prestataire p "
                + "WHERE  p.login='" + loginPrestataire + "'";
        return em.createQuery(queryString, Avis.class).getResultList();
    }

    public List<Avis> getLastAvis(int number) {
        String query = "SELECT a FROM Avis a ORDER BY a.id desc";
        return em.createQuery(query).setMaxResults(number).getResultList();
    }
}
