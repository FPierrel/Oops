package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date inscription;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Adresse> adresses;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Report> reports;

    private int adminWarnings;
    private boolean banished;

    public Utilisateur() {
        this.adresses = new ArrayList<>();
        this.reports = new ArrayList<>();
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

    public Collection<Report> getReports() {
        return reports;
    }

    public void setReports(Collection<Report> reports) {
        this.reports = reports;
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public int getAdminWarnings() {
        return adminWarnings;
    }

    public void setAdminWarnings(int adminWarnings) {
        this.adminWarnings = adminWarnings;
    }

    public boolean isBanished() {
        return banished;
    }

    public void setBanished(boolean banished) {
        this.banished = banished;
    }

    public Date getInscription() {
        return inscription;
    }

    public void setInscription(Date inscription) {
        this.inscription = inscription;
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
}
