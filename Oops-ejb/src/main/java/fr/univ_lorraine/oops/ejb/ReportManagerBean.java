/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Report;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

    public void reportFichePrestataire(String loginReporting, String loginFicheReported, String reason, String complement) {
        Prestataire pres = this.getEntityManager().find(Prestataire.class, loginFicheReported);
        ReportFichePrestataire report = new ReportFichePrestataire();
        report.setReason(reason);
        report.setPrestataire(pres);
        report.setUserReporting(loginReporting);
        report.setReportingDate(new Date());
        report.setComplement(complement);
        pres.addReport(report);
        this.getEntityManager().merge(pres);
    }

    public List<String> getReasons() {
        ArrayList<String> reasons = new ArrayList<>();
        reasons.add("Informations incorrectes");
        reasons.add("Usurpation d'indentité");
        reasons.add("Autre");
        return reasons;
    }

    public boolean isUserBanished(String login) {
        String queryString = "SELECT r "
                + "FROM Report r, Utilisateur u "
                + "WHERE r.justified='" + true + "' "
                + "AND u.login = '"+login+"'"
                + "AND r MEMBER OF u.reports";

        TypedQuery<Report> query = this.getEntityManager().createQuery(queryString, Report.class);
        int nb = query.getResultList().size();
        return nb >= 3;
    }
}
