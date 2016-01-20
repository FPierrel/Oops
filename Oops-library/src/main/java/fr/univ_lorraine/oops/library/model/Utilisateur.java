package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public abstract class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    public final static String defaut = "DEFAUT";
    @Id
    private String login;
    private String nom;
    private String prenom;
    private String motDePasse;
    private String mail;
    private String numeroTelephone;
    protected String groupe;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Adresse> adresses;
    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Avis> avisSoumis;*/
        
    public Utilisateur() {
        this.adresses = new ArrayList<>();
        //this.avisSoumis = new ArrayList<>() ; 
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public Collection<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(Collection<Adresse> adresses) {
        this.adresses = adresses;
    }
    
    public void addAdresse(Adresse adresse) {
        this.adresses.add(adresse);
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.login);
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
        final Utilisateur other = (Utilisateur) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "login=" + login + ", nom=" + nom + ", prenom=" + prenom + ", motDePasse=" + motDePasse + ", mail=" + mail + ", numeroTelephone=" + numeroTelephone + '}';
    }   

    /*public Collection<Avis> getAvisSoumis() {
        return avisSoumis;
    }

    public void setAvisSoumis(Collection<Avis> avisSoumis) {
        this.avisSoumis = avisSoumis;
    }
    
    public void addAvisSoumis( Avis a ) {
        this.avisSoumis.add(a) ; 
    }*/
}
