package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Categorie implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nom;
    private Collection<String> motsCles;
    
    @OneToMany
    private Collection<Categorie> sousCategories;
        
    public Categorie(){
        motsCles = new ArrayList<>();
        sousCategories = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<String> getMotsCles() {
        return motsCles;
    }

    public void setMotsCles(Collection<String> motsCles) {
        this.motsCles = motsCles;
    }

    public Collection<Categorie> getSousCategories() {
        return sousCategories;
    }

    public void setSousCategories(Collection<Categorie> sousCategories) {
        this.sousCategories = sousCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }   
}
