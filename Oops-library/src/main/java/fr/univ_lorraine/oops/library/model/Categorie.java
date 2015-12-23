/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.library.model;

import java.util.Collection;

/**
 *
 * @author Thomas
 */
public class Categorie {
    private String nom;
    private Collection<String> motsCles;
    private Collection<Categorie> sousCategories;
    
    public Categorie(){
        
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
    
    
}
