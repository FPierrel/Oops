package fr.univ_lorraine.oops.ejb;

import dal.AvisDAL;
import dal.CategorieDAL;
import dal.PrestataireDAL;
import dal.UtilisateurDAL;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Categorie;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class FichePrestataireBean {
    @Inject
    PrestataireDAL pd;
    
    @Inject
    AvisDAL ad;
    
    @Inject
    CategorieDAL cd;    
    
    public Prestataire getPrestataireLogin(String login) {
        Prestataire prestataire = pd.get(login);
        return prestataire;
    }
    
    public List<Avis> getPrestataireAvis(String login) {
        return ad.getAvisPrestatire(login);
    }
    
    public List<String> getPrestataireCategoriesNames(String login){
        List<Categorie> list = cd.getCategoriesOfPrestataire(login);
        List<String> cat = new ArrayList<>();
        for (Categorie c : list)
            cat.add(c.getNom());
        return cat;
    }    
}
