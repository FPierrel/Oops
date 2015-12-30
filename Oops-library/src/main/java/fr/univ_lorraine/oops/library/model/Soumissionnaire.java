package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Soumissionnaire extends Utilisateur implements Serializable {    
    private static final long serialVersionUID = 1L;
    public final static String soumissionnaire = "SOUMISSIONNAIRE";
    
    public Soumissionnaire() {
        this.groupe = soumissionnaire;
    }
    
}
