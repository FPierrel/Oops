package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Categorie.findByName",query = "SELECT c FROM Categorie c WHERE c.nom = :name")
public class Categorie implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;
    
    @ElementCollection
    private Collection<String> motsCles;
    
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
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
        motsCles.add(nom);
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

    public void addSousCategorie(Categorie c) {
        c.addMotCle(nom);
        this.sousCategories.add(c);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }   
    
    public String getLuceneDescription(){
        String s; 
        s = "nom" + " ";
        for (String ss : motsCles)
            s += ss + " ";
        for (Categorie c : sousCategories)
            s += c.getLuceneDescription();
        return s;
    }

    public ArrayList<String> getListCategories(ArrayList<String> list, int depth) {
        //String s = new String(new char[depth * 2]).replace("\0", "-");
        String s = "";
        list.add(s + nom);
        for (Categorie c : sousCategories) {
            c.getListCategories(list, depth + 1);
        }
        return list;
    }

    public void addMotCle(String motCle) {
        this.motsCles.add(motCle);
        for(Categorie c : sousCategories){
            c.addMotCle(motCle);
        }
    }
}
