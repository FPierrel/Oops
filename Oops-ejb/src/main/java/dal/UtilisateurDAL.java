package dal;

import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class UtilisateurDAL {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Utilisateur add(Utilisateur u) {
        em.persist(u);
        return u;
    }

    public Utilisateur get(String login) {
        return em.find(Utilisateur.class, login);
    }

    public List<Utilisateur> getAll() {
        String query = "SELECT u FROM Utilisateur u";
        return em.createQuery(query).getResultList();
    }

    public Utilisateur update(Utilisateur u) {
        return em.merge(u);
    }

    public List<Utilisateur> getBanishedUser() {
        String queryString = "SELECT u "
                + "FROM Utilisateur u "
                + "WHERE  u.banished='" + true + "'";
        return em.createQuery(queryString, Utilisateur.class).getResultList();
    }
    
    public Utilisateur getUserByEmail(String mail){
        String queryString = "SELECT u "
                + "FROM Utilisateur u "
                + "WHERE  u.mail='" + mail+ "'";
        return em.createQuery(queryString, Utilisateur.class).getSingleResult();
    }

    public boolean mailExist(String mail) {
        String queryString = "SELECT u.mail "
                + "FROM Utilisateur u "
                + "WHERE u.mail = '" + mail + "'";
        return (em.createQuery(queryString, String.class).getResultList().size() > 0);
    }
    
    public List<Prestataire> getBestGrades(int number) {
        String query = "SELECT p FROM Prestataire p ";
        List<Prestataire> lp = em.createQuery(query).getResultList();
        Prestataire tp[] = new Prestataire[lp.size()] ; 
        for (int i=0;i<lp.size();i++) {
            lp.get(i).recalculateMarks();
            tp[i]=lp.get(i) ; 
        }
        tp = this.triRapide(tp); 
        List<Prestataire> lpSort = new ArrayList<>();
        for (int i=0;i<number;i++) lpSort.add(tp[tp.length-1-i]) ;
        return lpSort; 
    }

    public Prestataire[] triRapide(Prestataire tableau[]) {
        int longueur = tableau.length;
        triRapide(tableau,0,longueur-1);
        return tableau;
    }

    private int partition(Prestataire tableau[], int deb, int fin) {
        int compt = deb;
        int pivot = tableau[deb].getAverage();
        for (int i = deb + 1; i <= fin; i++) {
            if (tableau[i].getAverage() < pivot) {
                compt++;
                Prestataire test = tableau[compt];
                tableau[compt] = tableau[i];
                tableau[i] = test;
            }
        }
        Prestataire test = tableau[deb];
        tableau[deb] = tableau[compt];
        tableau[compt] = test;
        return (compt);
    }

    private void triRapide(Prestataire tableau[], int deb, int fin) {
        if (deb < fin) {
            int positionPivot = partition(tableau, deb, fin);
            triRapide(tableau, deb, positionPivot - 1);
            triRapide(tableau, positionPivot + 1, fin);
        }
    }
}
