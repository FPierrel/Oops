package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Moderateur extends Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Moderateur() {
        
    }
    
}
