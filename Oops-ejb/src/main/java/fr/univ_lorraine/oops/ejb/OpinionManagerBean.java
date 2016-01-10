/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Prestataire;
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
    
    public void saveOpinion(int nC, int nP, int nQ, int nD, String contenu, Date d, Prestataire p) {
        Avis a = new Avis() ; 
        a.setNoteCom(nC);
        a.setNotePrix(nP);
        a.setNoteQualite(nQ);
        a.setNoteDelai(nD);
        a.setContenu(contenu);
        a.setpDate(d);
        a.setpLogin(p.getLogin());
        this.getEntityManager().persist(a);
    }
}
