package fr.univ_lorraine.oops.beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named(value = "authenticationBean")
@RequestScoped
public class AuthenticationBean {

    public AuthenticationBean() {
    }

    /**
     * Méthode permettant de vérifier les différents scénario concernant la session d'un utilisateur.
     * @param event 
     */
    public void checkErrors(ComponentSystemEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if ("true".equals((String) request.getParameter("failed"))) {
            /* GET parameter "failed" has been sent in the HTTP request... */
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Connexion échouée !", null));
        } else if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid() && request.getParameter("logout") == null) {
            /* The user session has timed out (not caused by a logout action)... */
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Votre session a expiré !", null));
        } else if (request.getParameter("logout") != null && request.getParameter("logout").equalsIgnoreCase("true")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Déconnexion réussie !", null));
        } else if (request.getParameter("blocked") != null && request.getParameter("blocked").equalsIgnoreCase("true")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Votre compte a été bloqué par un administrateur !", null));
        }
    }

    /**
     * Méthode permettant la déconnexion d'un utilisateur.
     * @return page vers laquelle on est redirigé.
     */
    public String logout() {
        String page = "/login?logout=true&faces-redirect=true";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Déconnexion échouée !", null));
            page = "";
        }
        return page;
    }

}
