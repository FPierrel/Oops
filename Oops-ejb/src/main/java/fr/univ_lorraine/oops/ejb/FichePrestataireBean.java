package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.AvisDAL;
import fr.univ_lorraine.oops.dal.CategorieDAL;
import fr.univ_lorraine.oops.dal.PrestataireDAL;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Categorie;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class FichePrestataireBean {

    @Inject
    PrestataireDAL pd;

    @Inject
    AvisDAL ad;

    @Inject
    CategorieDAL cd;

    /**
     * Méthode donnant un prestataire à partir de son login.
     *
     * @param login : login du prestataire voulu.
     * @return le prestataire voulu.
     */
    public Prestataire getPrestataireLogin(String login) {
        Prestataire prestataire = pd.get(login);
        return prestataire;
    }

    /**
     * Méthode donnant la liste des avis sur la fiche du prestataire.
     *
     * @param login : login du prestataire.
     * @return la liste des avis sur la fiche du prestataire.
     */
    public List<Avis> getPrestataireAvis(String login) {
        return ad.getAvisPrestatire(login);
    }

    /**
     * Méthode donnant la liste des noms de catégories d'un prestataire.
     *
     * @param login : login du prestataire.
     * @return la liste des noms de catégories du prestataire.
     */
    public List<String> getPrestataireCategoriesNames(String login) {
        List<Categorie> list = cd.getCategoriesOfPrestataire(login);
        List<String> cat = new ArrayList<>();
        for (Categorie c : list) {
            cat.add(c.getNom());
        }
        return cat;
    }
}
