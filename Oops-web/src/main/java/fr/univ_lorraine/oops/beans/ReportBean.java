/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.BanishmentBean;
import fr.univ_lorraine.oops.ejb.ReportManagerBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    @Inject
    BanishmentBean ban;

    private String page;
    private List<String> reasons = new ArrayList<>();
    private String reason;
    private String complement;

    /**
     * Creates a new instance of ReportBean
     */
    public ReportBean() {
    }

    public void reportFiche() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        report.reportFichePrestataire(request.getRemoteUser(), page, reason, complement);
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<String> getReasons() {
        return report.getReasons();
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reasonChosen) {
        this.reason = reasonChosen;
    }
    
    public void banishPrestataire(){
        ban.banishUser(page);
    }
   
    public void redeemPrestataire(){
        ban.redeemUser(page);
    }
    
    public boolean isBanished(){
        return ban.isUserBanished(page);
    }
}
