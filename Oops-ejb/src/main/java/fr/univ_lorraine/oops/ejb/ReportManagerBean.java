/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Romain
 */
@Stateless
@LocalBean
public class ReportManagerBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void reportFichePrestataire(String loginReporting, String loginFicheReported, String reason) {
        Utilisateur user = this.getEntityManager().find(Utilisateur.class, loginReporting);
        Prestataire pres = this.getEntityManager().find(Prestataire.class, loginFicheReported);
        ReportFichePrestataire report = new ReportFichePrestataire();
        report.setReason(reason);
        report.setPrestataire(pres);
        report.setUserReporting(user);
        report.setReportingDate(new Date());
        this.getEntityManager().persist(report);
    }
}
