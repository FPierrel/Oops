package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.AvisDAL;
import fr.univ_lorraine.oops.dal.CommentaireDAL;
import fr.univ_lorraine.oops.dal.UtilisateurDAL;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Commentaire;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class OpinionManagerBean {

    @Inject
    UtilisateurDAL ud;
    
    @Inject
    AvisDAL ad;
    
    @Inject
    CommentaireDAL cd;
    
    /**
     * Register a new Avis
     * @param nC grade in communication
     * @param nP grade in price
     * @param nQ grade in quality
     * @param nD grade in delay
     * @param contenu text of the Avis
     * @param d date of the Avis
     * @param p Prestataire receiving the Avis
     * @param loginPoseurAvis login of the Utilisateur creating the Avis
     */
    public void saveOpinion(int nC, int nP, int nQ, int nD, String contenu, Date d, Prestataire p, String loginPoseurAvis) {
        Utilisateur user = ud.get(loginPoseurAvis);
        Avis a = new Avis();
        a.setNoteCom(nC);
        a.setNotePrix(nP);
        a.setNoteQualite(nQ);
        a.setNoteDelai(nD);
        a.setContenu(contenu);
        a.setpDate(d);
        a.setOwner(user);
        a.setLoginPrestaire(p.getLogin());
        p.addAvis(a);

        ud.update(p);;
    }

    /**
     * Register a new Comment
     * @param d date of the Comment
     * @param login login of the Utilisateur commenting
     * @param avis Avis being commented on
     * @param message text of the Comment
     */
    public void saveComment(Date d, String login, Avis avis, String message) {
        Avis a = ad.get(avis.getId());
        Commentaire c = new Commentaire();
        c.setComDate(d);
        c.setContenu(message);
        c.setProfil(login);
        a.addCommentaire(c);
        ad.update(a);
    }

    /**
     * Delete a Avis
     * @param avis Avis to delete
     */
    public void removeOpinion(Avis avis) {
        Prestataire p = (Prestataire) ud.get(avis.getLoginPrestaire());
        p.removeAvis(avis);
        ud.update(p);
        ad.delete(avis);
        p.recalculateMarks();
    }

    /**
     * Delete a Comment
     * @param c Comment to delete
     * @param avis Avis containing the Comment to delete
     */
    public void removeComment(Commentaire c, Avis avis) {
        avis.removeCom(c);
        ad.update(avis);
        cd.delete(c);
    }

    /**
     * Return the four last Avis registered
     * @return
     */
    public List<Avis> getLastOpinions() {
        return ad.getLastAvis(4);
    }

}
