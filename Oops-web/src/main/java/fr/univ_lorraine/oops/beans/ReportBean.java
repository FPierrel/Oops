/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.ReportManagerBean;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Romain
 */
@Named(value = "reportBean")
@ViewScoped
public class ReportBean implements Serializable {

    @Inject
    ReportManagerBean report;

    private String page;
    private String reason;

    /**
     * Creates a new instance of ReportBean
     */
    public ReportBean() {
    }

    public void reportFiche() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        report.reportFichePrestataire(request.getRemoteUser(), page, reason);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
