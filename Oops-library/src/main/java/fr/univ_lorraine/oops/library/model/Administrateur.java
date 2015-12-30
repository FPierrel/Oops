package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Administrateur extends Moderateur implements Serializable {
    private static final long serialVersionUID = 1L;    
    public final static String administrateur = "ADMINISTRATEUR";
    
    public Administrateur() {
        this.groupe = administrateur;
    }
    
}
