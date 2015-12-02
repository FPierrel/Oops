package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Prestataire extends Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nomEntreprise;
    private int nbEmployes;
    private int chiffreAffaire;
    
    public Prestataire() {
        
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }    

    public int getNbEmployes() {
        return nbEmployes;
    }

    public void setNbEmployes(int nbEmployes) {
        this.nbEmployes = nbEmployes;
    }

    public int getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(int chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }    
    
}
