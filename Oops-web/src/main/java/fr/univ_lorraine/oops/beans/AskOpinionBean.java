package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.MailManagerBean;
import java.io.Serializable;
import java.util.regex.Pattern;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Named(value = "askOpinionBean")
@ViewScoped
public class AskOpinionBean implements Serializable{

    @Inject
    MailManagerBean mMB;

    private String mail;
    private String feedback = "";
    private UIComponent emailComponent;
    private String page;

    public AskOpinionBean() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public UIComponent getEmailComponent() {
        return emailComponent;
    }

    public void setEmailComponent(UIComponent emailComponent) {
        this.emailComponent = emailComponent;
    }

    public String send() {
        System.out.println("************ SEND ******************");
        FacesContext context = FacesContext.getCurrentInstance();
        if (!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", this.mail)) {
            FacesMessage fM = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adresse mail non valide, veuillez recommencer !", null);
            context.addMessage(this.emailComponent.getClientId(), fM);
            return "fiche.xhtml?page"+page;
        }
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (mMB.sendAskOpinion(request.getRemoteUser(), mail, "http://pi-r-l.ovh:8080/Oops-web/"+"?page="+page)) {
            feedback = "Message envoyé";
        } else {
            feedback = "Ce mail ne correspond à personne inscrit sur le site";
        }
        return "fiche.xhtml?page"+page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    
}
