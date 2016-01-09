package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "Prestataire.findNomPrenom", query = "SELECT p FROM Prestataire p WHERE p.nom = :nom AND p.prenom = :prenom"),
})
public class Prestataire extends Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    public final static String prestataire = "PRESTATAIRE";
    private String nomEntreprise;
    private int nbEmployes;
    private int chiffreAffaire;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<Categorie> categories;

    public Prestataire() {
        this.groupe = prestataire;
        this.categories = new ArrayList<>();
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
     public Collection<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Categorie> categories) {
        this.categories = categories;
    }
    
/*    
    public String getLuceneCategorieDescription(){
        String s = "";
        for (Categorie c : categories)
            s+= c.getLuceneDescription();
        return s;
    }*/
}
