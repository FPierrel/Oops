package fr.univ_lorraine.oops.beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Named(value = "connexionBean")
@RequestScoped
public class ConnexionBean {

    /**
     * Creates a new instance of ConnexionBean
     */
    public ConnexionBean() {
    }
    
}
