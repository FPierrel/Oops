/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author geoffrey
 */
@Named(value = "connexionBean")
@RequestScoped
public class ConnexionBean {

    /**
     * Creates a new instance of ConnexionBean
     */
    public ConnexionBean() {
    }
    
}
