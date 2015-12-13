package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.FichePrestataireBean;
import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.util.Collection;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Named(value = "ficheBean")
@RequestScoped
public class FicheBean {

    @Inject
    private FichePrestataireBean fiche;

    private Prestataire p = null;
    private String prenomSearch, nomSearch, loginSearch;

    public FicheBean() {

    }

    public String searchLogin() {
        p = null;
        List<Prestataire> listPres = fiche.getPrestataireLogin(loginSearch);
        if (listPres.size() > 0) {
            p = listPres.get(0);
            return "fiche.xhtml";
        }
        return "index.xhtml";
    }

    public String getPrenomSearch() {
        return prenomSearch;
    }

    public void setPrenomSearch(String prenomSearch) {
        this.prenomSearch = prenomSearch;
    }

    public String getNomSearch() {
        return nomSearch;
    }

    public void setNomSearch(String nomSeach) {
        this.nomSearch = nomSeach;
    }

    public String getLoginSearch() {
        return loginSearch;
    }

    public void setLoginSearch(String loginSearch) {
        this.loginSearch = loginSearch;
        searchLogin();
    }

    public String getPrenom() {
        return p.getPrenom();
    }

    public void setPrenom(String prenom) {
        p.setPrenom(prenom);
    }

    public String getNom() {
        return p.getNom();
    }

    public void setNom(String nom) {
        p.setNom(nom);
    }

    public String getMail() {
        return p.getMail();
    }

    public void setMail(String mail) {
        p.setMail(mail);
    }

    public String getNumeroTelephone() {
        return p.getNumeroTelephone();
    }

    public void setNumeroTelephone(String numeroTelephone) {
        p.setNumeroTelephone(numeroTelephone);
    }

    public String getNomEntreprise() {
        return p.getNomEntreprise();
    }

    public void setNomEntreprise(String nomEntreprise) {
        p.setNomEntreprise(nomEntreprise);
    }

    public int getNbEmployes() {
        return p.getNbEmployes();
    }

    public void setNbEmployes(int nbEmployes) {
        p.setNbEmployes(nbEmployes);
    }

    public int getChiffreAffaire() {
        return p.getChiffreAffaire();
    }

    public void setChiffreAffaire(int chiffreAffaire) {
        p.setChiffreAffaire(chiffreAffaire);
    }

    public Collection<Adresse> getAdresses() {
        return p.getAdresses();
    }

    public void setAdresses(Collection<Adresse> adresses) {
        p.setAdresses(adresses);
    }

}
