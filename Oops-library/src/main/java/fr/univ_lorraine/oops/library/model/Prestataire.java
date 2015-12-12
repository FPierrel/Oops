package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "Prestataire.findByLastname", query = "SELECT p FROM Prestataire p WHERE p.nom = :lastname"),
    @NamedQuery(name = "Prestataire.findByFirstname", query = "SELECT p FROM Prestataire p WHERE p.prenom = :firstname"),
    @NamedQuery(name = "Prestataire.findByEmployee", query = "SELECT p FROM Prestataire p WHERE p.nbEmployes >= :employee"),
    @NamedQuery(name = "Prestataire.findByRating", query = "SELECT p FROM Prestataire p WHERE p.rating >= :rating"),
    @NamedQuery(name = "Prestataire.findByLogin", query = "SELECT p FROM Prestataire p WHERE p.login = :login")
})
public class Prestataire extends Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nomEntreprise;
    private int nbEmployes;
    private int chiffreAffaire;
    private final float rating = 3;

    public Prestataire() {

    }

    public float getRating() {
        return rating;
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

    @Override
    public String toString() {
        return super.toString()+" Prestataire{" + "nomEntreprise=" + nomEntreprise + ", nbEmployes=" + nbEmployes + ", chiffreAffaire=" + chiffreAffaire + ", rating=" + rating + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.nomEntreprise);
        hash = 19 * hash + this.nbEmployes;
        hash = 19 * hash + this.chiffreAffaire;
        hash = 19 * hash + Float.floatToIntBits(this.rating);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prestataire other = (Prestataire) obj;
        if (!Objects.equals(this.nomEntreprise, other.nomEntreprise)) {
            return false;
        }
        if (this.nbEmployes != other.nbEmployes) {
            return false;
        }
        if (this.chiffreAffaire != other.chiffreAffaire) {
            return false;
        }
        if (Float.floatToIntBits(this.rating) != Float.floatToIntBits(other.rating)) {
            return false;
        }
        return true;
    }
    
    

}
