/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Commentaire;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Thibaut
 */
@Stateless
@LocalBean
public class OpinionManagerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void saveOpinion(int nC, int nP, int nQ, int nD, String contenu, Date d, Prestataire p, String loginPoseurAvis) {
        Utilisateur user = this.getEntityManager().find(Utilisateur.class, loginPoseurAvis);
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
        //user.addAvisSoumis(a);
        //this.getEntityManager().merge(user) ; 
        this.getEntityManager().merge(p);
    }

    public void saveComment(Date d, String login, Avis avis, String message) {
        Avis a = this.getEntityManager().find(Avis.class, avis.getId());
        Commentaire c = new Commentaire();
        c.setComDate(d);
        c.setContenu(message);
        c.setProfil(login);
        a.addCommentaire(c);
        
        this.getEntityManager().merge(a);
    }
}
