package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "Prestataire.findNomPrenom", query = "SELECT p FROM Prestataire p WHERE p.nom = :nom AND p.prenom = :prenom"),
    @NamedQuery(name = "Prestataire.findLogin", query = "SELECT p FROM Prestataire p WHERE p.login = :login")
})
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
