package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
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
    private String nomEntreprise, description, siteWeb;
    private int nbEmployes, chiffreAffaire;
    @OneToMany
    private Collection<Categorie> categories;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Avis> cAvis= new ArrayList<>() ; ;

    private int communication, quality, price, delay, average;

    public Prestataire() {
        this.groupe = prestataire;
        this.categories = new ArrayList<>();
        this.nomEntreprise = "";
        this.description = "";
        this.siteWeb = "";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public int getCommunication() {
        return communication;
    }

    public void setCommunication(int communication) {
        this.communication = communication;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }
    
    public Collection<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Categorie> categories) {
        this.categories = categories;
    }
    
    public List<String> getListCategories(){
        List<String> l = new ArrayList<>();
        for (Categorie c : categories) {
            l.add(c.getNom());
        }
        return l;
    }

    public void addAvis(Avis avis) {
        this.cAvis.add(avis);
        recalculateMarks();
    }

    public Collection<Avis> getcAvis() {
        return cAvis;
    }

    public void setcAvis(Collection<Avis> cAvis) {
        this.cAvis = cAvis;
    }
    
    public int getNbAvis(){
        return cAvis.size();
    }

    public String getLuceneCategorieDescription() {
        String s = "";
        for (Categorie c : categories) {
            s += c.getLuceneDescription();
        }
        return s;
    }

    public void recalculateMarks() {
        int noteGlobCom = 0;
        int noteGlobQualite = 0;
        int noteGlobPrix = 0;
        int noteGlobDelai = 0;

        for (Avis a : this.cAvis) {
            noteGlobCom += a.getNoteCom();
            noteGlobQualite += a.getNoteQualite();
            noteGlobPrix += a.getNotePrix();
            noteGlobDelai += a.getNoteDelai();
        }
        this.average = round(((noteGlobCom / cAvis.size()) + (noteGlobDelai / cAvis.size()) + (noteGlobPrix / cAvis.size()) + (noteGlobQualite) / cAvis.size()) / 4);
        this.communication = (int) round(noteGlobCom / cAvis.size());
        this.quality = (int) round(noteGlobQualite / cAvis.size());
        this.price = (int) round(noteGlobPrix / cAvis.size());
        this.delay = (int) round(noteGlobDelai / cAvis.size());
    }

}
