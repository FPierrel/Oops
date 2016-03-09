package fr.univ_lorraine.oops.ejb;

import dal.AvisDAL;
import dal.CommentaireDAL;
import dal.UtilisateurDAL;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Commentaire;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class OpinionManagerBean {

    @Inject
    UtilisateurDAL ud;
    
    @Inject
    AvisDAL ad;
    
    @Inject
    CommentaireDAL cd;
    
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

    public void saveComment(Date d, String login, Avis avis, String message) {
        Avis a = ad.get(avis.getId());
        Commentaire c = new Commentaire();
        c.setComDate(d);
        c.setContenu(message);
        c.setProfil(login);
        a.addCommentaire(c);
        ad.update(a);
    }

    public void removeOpinion(Avis avis) {
        Prestataire p = (Prestataire) ud.get(avis.getLoginPrestaire());
        p.removeAvis(avis);
        ud.update(p);
        ad.delete(avis);
        p.recalculateMarks();
    }

    public void removeComment(Commentaire c, Avis avis) {
        avis.removeCom(c);
        ad.update(avis);
        cd.delete(c);
    }

    public List<Avis> getLastOpinions() {
        return ad.getLastAvis(4);
    }

}
