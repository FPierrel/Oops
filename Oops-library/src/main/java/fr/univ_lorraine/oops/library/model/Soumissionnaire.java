package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Soumissionnaire extends Utilisateur implements Serializable {
    
    public Soumissionnaire() {
        
    }
    
}
