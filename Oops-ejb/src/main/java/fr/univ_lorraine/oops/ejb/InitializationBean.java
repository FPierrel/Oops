package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Administrateur;
import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Categorie;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.ReportFichePrestataire;
import fr.univ_lorraine.oops.library.model.ReportPhoto;
import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
@DependsOn("LuceneBean")
@LocalBean
public class InitializationBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Inject
    private LuceneBean luceneBean;

    @PostConstruct
    public void init() {
        this.addPrestataires();
        this.addSoumissionaires();
        this.addSignalements();
    }

    /**
     * 1 login, 1 motDePasse, 1 mail, 1 telephone, 1 nomEntreprise et 1 adresse
     * : OBLIGATOIRES !
     */
    private void addPrestataires() {

        Categorie tout = new Categorie();
        tout.setNom("Toutes cat\u00e9gories");

        Categorie c1 = new Categorie();
        c1.setNom("BTP, Construction");

        Categorie c1a = new Categorie();
        c1a.setNom("Ma\u00e7onnerie");
        c1.addSousCategorie(c1a);

        Categorie c1b = new Categorie();
        c1b.setNom("Electricit\u00e9");
        c1.addSousCategorie(c1b);

        Categorie c1c = new Categorie();
        c1c.setNom("Peinture");
        c1.addSousCategorie(c1c);

        Categorie c1d = new Categorie();
        c1d.setNom("Architecture");
        c1.addSousCategorie(c1d);

        tout.addSousCategorie(c1);
        //---------------------------------------------------

        Categorie c2 = new Categorie();
        c2.setNom("Banque, Finance, Gestion");

        Categorie c2a = new Categorie();
        c2a.setNom("Banque");
        c2.addSousCategorie(c2a);

        Categorie c2b = new Categorie();
        c2b.setNom("Comptabilit\u00e9");
        c2.addSousCategorie(c2b);

        Categorie c2c = new Categorie();
        c2c.setNom("Conseil financier");
        c2.addSousCategorie(c2c);

        tout.addSousCategorie(c2);
        //----------------------------------------------------

        Categorie c3 = new Categorie();
        c3.setNom("Restauration");

        Categorie c3a = new Categorie();
        c3a.setNom("Restauration rapide");
        c3.addSousCategorie(c3a);

        Categorie c3b = new Categorie();
        c3b.setNom("Pizzeria");
        c3.addSousCategorie(c3b);

        Categorie c3c = new Categorie();
        c3c.setNom("Restaurant \u00e9toil\u00e9");
        c3.addSousCategorie(c3c);

        Categorie c3d = new Categorie();
        c3d.setNom("Livraison \u00e0 domicile");
        c3.addSousCategorie(c3d);

        tout.addSousCategorie(c3);
        //---------------------------------------------------------
        Categorie c4 = new Categorie();
        c4.setNom("Transport, V\u00e9hicules, Logistique");

        Categorie c4a = new Categorie();
        c4a.setNom("Transport routier");
        c4.addSousCategorie(c4a);

        Categorie c4b = new Categorie();
        c4b.setNom("Transport express");
        c4.addSousCategorie(c4b);

        Categorie c4c = new Categorie();
        c4c.setNom("International");
        c4.addSousCategorie(c4c);

        Categorie c4d = new Categorie();
        c4d.setNom("Garage");
        c4.addSousCategorie(c4d);

        tout.addSousCategorie(c4);
        //----------------------------------------------------------
        Categorie c5 = new Categorie();
        c5.setNom("Informatique");

        Categorie c5a = new Categorie();
        c5a.setNom("Audit");
        c5.addSousCategorie(c5a);

        Categorie c5b = new Categorie();
        c5b.setNom("Conseil");
        c5.addSousCategorie(c5b);

        Categorie c5c = new Categorie();
        c5c.setNom("D\u00e9pannage");
        c5.addSousCategorie(c5c);

        Categorie c5d = new Categorie();
        c5d.setNom("Vente de mat\u00e9riel");
        c5.addSousCategorie(c5d);

        tout.addSousCategorie(c5);

        em.persist(tout);

        ArrayList<Prestataire> liste = new ArrayList<>();
        //DEBUT AJOUTS DE PRESTATAIRES : (MODIFIER CI-DESSOUS)
        //----------------------------------------------
        ArrayList<Adresse> adressesJose = new ArrayList<>();
        adressesJose.add(this.creerAdresse("44", "Rue Rennequin", "", "75000", "PARIS (75001-75002-75003-75004-75005-75006-75007-75008-75009-75010-75011-75012-75013-75014-75015-75016-75017-75018-75019-75020-75116)", "France"));
        Prestataire pres1 = this.creerPrestataire("jose", "Bové", "José", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "jose.bove@gmail.com",
                "0123456789", "Jose Bove Enterprise", 999, 1500000, adressesJose);
        pres1.setDescription("Chez José Bové, il y a des josés !");
        Collection<Categorie> col1 = new ArrayList<>();
        col1.add(tout);
        pres1.setCategories(col1);
        liste.add(pres1);
        //----------------------------------------------
        ArrayList<Adresse> adressesNoupi = new ArrayList<>();
        adressesNoupi.add(this.creerAdresse("8", "Rue Lafayette", "", "57000", "METZ (57000-57050-57070)", "Allemagne"));
        Prestataire pres2 = this.creerPrestataire("noupi", "Le lapin", "Noupi", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "noupi@lelapin.com",
                "0123456789", "T'es sale Noupi", 3, 10, adressesNoupi);
        pres2.setDescription("Ceci est un garenne...");
        Collection<Categorie> col2 = new ArrayList<>();
        col2.add(c4d);
        col2.add(c4c);
        pres2.setCategories(col2);
        Avis avis1 = new Avis();
        avis1.setContenu("Bonjour");
        avis1.setOwner(pres1);
        avis1.setNoteCom(1);
        avis1.setNoteDelai(3);
        avis1.setNotePrix(2);
        avis1.setpDate(new Date());
        avis1.setNoteQualite(5);
        avis1.setLoginPrestaire("noupi");
        pres2.addAvis(avis1);
        Avis avis22 = new Avis();
        avis22.setContenu("Bonjour satan");
        avis22.setOwner(pres1);
        avis22.setNoteCom(0);
        avis22.setNoteDelai(0);
        avis22.setNotePrix(0);
        avis22.setpDate(new Date());
        avis22.setNoteQualite(0);
        avis22.setLoginPrestaire("noupi");
        pres2.addAvis(avis22);
        liste.add(pres2);
        //----------------------------------------------
        ArrayList<Adresse> adressesPhilippe = new ArrayList<>();
        adressesPhilippe.add(this.creerAdresse("17", "Rue Saint-Pierre", "", "57000", "METZ (57000-57050-57070)", "Allemagne"));
        Prestataire pres3 = this.creerPrestataire("philippe", "Le héros", "Philippe", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "philippe@hitman-lecobra.com",
                "0123456789", "Je sais où tu te caches !", 1, 99999999, adressesPhilippe);
        pres3.setDescription("Ceci est la description de Philippe ...");
        Collection<Categorie> col3 = new ArrayList<>();
        col3.add(c1);
        pres3.setCategories(col3);
        Avis avis2 = new Avis();
        avis2.setContenu("Bonjour les amis");
        avis2.setOwner(pres1);
        avis2.setNoteCom(1);
        avis2.setNoteDelai(3);
        avis2.setNotePrix(2);
        avis2.setpDate(new Date());
        avis2.setNoteQualite(5);
        avis2.setLoginPrestaire("philippe");
        pres3.addAvis(avis2);
        Avis avis3 = new Avis();
        avis3.setContenu("Bonsoir !");
        avis3.setOwner(pres2);
        avis3.setNoteCom(4);
        avis3.setNoteDelai(3);
        avis3.setNotePrix(0);
        avis3.setpDate(new Date());
        avis3.setNoteQualite(3);
        avis3.setLoginPrestaire("philippe");
        pres3.addAvis(avis3);
        liste.add(pres3);
        //----------------------------------------------
        ArrayList<Adresse> adressesDemon = new ArrayList<>();
        adressesDemon.add(this.creerAdresse("7", "Quai des Iranees", "", "88250", "LA BRESSE (88250)", "Enfer"));
        Prestataire pres4 = this.creerPrestataire("satan", "Satan", "Belzébuth", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "satan@satan.enfer",
                "0123456789", "Ca brule", 1, 666666666, adressesDemon);
        pres4.setDescription("Il fait chaud la bas !");
        Collection<Categorie> col4 = new ArrayList<>();
        col4.add(c3d);
        pres4.setCategories(col4);
        liste.add(pres4);
        //----------------------------------------------
        ArrayList<Adresse> adressesNeufchateau = new ArrayList<>();
        adressesNeufchateau.add(this.creerAdresse("3", "Rue de la comédie", "", "88300", "NEUFCHATEAU (88300)", "France"));
        Prestataire pres5 = this.creerPrestataire("Jacky", "Tuning", "Jacky", "123456", "thomas.pagelot8@etu.univ-lorraine.fr",
                "0123456789", "La Caisse à Jacky", 1, 50, adressesNeufchateau);
        pres5.setDescription("Jacky Tuning c'est le meilleur !");
        Collection<Categorie> col5 = new ArrayList<>();
        col5.add(c4d);
        pres5.setCategories(col5);
        pres5.setBanished(true);
        liste.add(pres5);
        //----------------------------------------------
        ArrayList<Adresse> adressesNancy = new ArrayList<>();
        adressesNancy.add(this.creerAdresse("18", "Cours Léopold", "", "54000", "NANCY (54100)", "France"));
        Prestataire pres6 = this.creerPrestataire("Riri", "Fifi", "Loulou", "123456", "a@a.b",
                "0123456789", "Oooooh", 1, 126423, adressesNancy);
        pres6.setDescription("Ooooooooooooooooooooooooooooohhhhh la description !!!");
        Collection<Categorie> col6 = new ArrayList<>();
        col6.add(c1c);
        col6.add(c1d);
        pres6.setCategories(col6);
        pres6.setBanished(true);
        liste.add(pres6);
        //----------------------------------------------
        ArrayList<Adresse> adressesOui = new ArrayList<>();
        adressesOui.add(this.creerAdresse("21", "Rue d'Alger", "", "88630", "COUSSEY (88630)", "France"));
        Prestataire pres7 = this.creerPrestataire("Oui", "Oui", "Oui", "123456", "thibaut.humbert5@etu.univ-lorraine.fr",
                "0123456789", "Oui", 1, 126423, adressesOui);
        pres7.setDescription("Oui oui oui oui oui oui oui oui...");
        Collection<Categorie> col7 = new ArrayList<>();
        col7.add(c3b);
        pres7.setCategories(col7);
        liste.add(pres7);
        //----------------------------------------------

        //FIN AJOUTS DE PRESTATAIRE.
        for (Prestataire p : liste) {
            this.em.persist(p);
            //Ajout dans le moteur de recherche
            luceneBean.indexPrestataire(p);
        }
    }

    /**
     * 1 login, 1 motDePasse, 1 mail, 1 nom, 1 prenom et 1 adresse :
     * OBLIGATOIRES !
     */
    private void addSoumissionaires() {
        ArrayList<Soumissionnaire> liste = new ArrayList<>();
        //DEBUT AJOUTS DE SOUMISSIONNAIRES : (MODIFIER CI-DESSOUS)
        //----------------------------------------------
        ArrayList<Adresse> adressesJohn = new ArrayList<>();
        adressesJohn.add(this.creerAdresse("22", "rue de la zappette", "", "54000", "NANCY (54100)", "France"));
        liste.add(this.creerSoumissionnaire("john", "Siffleur", "John", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "romain.bailloux8@etu.univ-lorraine.fr",
                "0123456789", adressesJohn));
        //----------------------------------------------
        //FIN AJOUTS DE SOUMISSIONNAIRES.
        for (Soumissionnaire s : liste) {
            this.em.persist(s);
        }
    }

    private Prestataire creerPrestataire(String login, String nom, String prenom, String motDePasse, String mail,
            String telephone, String nomEntreprise, int nbEmployes, int chiffreAffaire, ArrayList<Adresse> adresses) {
        Prestataire prestataire = new Prestataire();
        prestataire.setLogin(login);
        prestataire.setNom(nom);
        prestataire.setPrenom(prenom);
        prestataire.setMotDePasse(motDePasse);
        prestataire.setMail(mail);
        prestataire.setNumeroTelephone(telephone);
        prestataire.setNomEntreprise(nomEntreprise);
        prestataire.setNbEmployes(nbEmployes);
        prestataire.setChiffreAffaire(chiffreAffaire);
        prestataire.setAdresses(adresses);
        prestataire.setInscription(new Date());
        if (login == "jose") {
            prestataire.setGroupe(Administrateur.administrateur);
        }
        return prestataire;
    }

    private Soumissionnaire creerSoumissionnaire(String login, String nom, String prenom, String motDePasse, String mail,
            String telephone, ArrayList<Adresse> adresses) {
        Soumissionnaire soumissionnaire = new Soumissionnaire();
        soumissionnaire.setLogin(login);
        soumissionnaire.setNom(nom);
        soumissionnaire.setPrenom(prenom);
        soumissionnaire.setMotDePasse(motDePasse);
        soumissionnaire.setMail(mail);
        soumissionnaire.setNumeroTelephone(telephone);
        soumissionnaire.setAdresses(adresses);
        soumissionnaire.setInscription(new Date());
        return soumissionnaire;
    }

    private Adresse creerAdresse(String numero, String rue, String complement, String codePostal, String ville, String pays) {
        Adresse adresse = new Adresse();
        adresse.setNumero(numero);
        adresse.setRue(rue);
        adresse.setComplement(complement);
        adresse.setCodePostal(codePostal);
        adresse.setVille(ville);
        return adresse;
    }

    private void addSignalements() {
        ArrayList<ReportFichePrestataire> liste = new ArrayList<>();
        //DEBUT AJOUTS DE SIGNALEMENTS DE FICHES: (MODIFIER CI-DESSOUS)
        //----------------------------------------------
        liste.add(this.creerSignalementFiche("john","Autre","Le prestataire n'a pas facturé le prix initialement négocié.",new Date(),"philippe"));
        liste.add(this.creerSignalementFiche("philippe","Usurpation d'indentité","Cette entreprise utilise mes informations pour se faire passer pour mon entreprise.",new Date(),"Jacky"));
        //----------------------------------------------
        //FIN AJOUTS DE SOUMISSIONNAIRES.
        for (ReportFichePrestataire r : liste) {
            this.em.persist(r);
        }
    }

    private ReportFichePrestataire creerSignalementFiche(String userReporting, String reason, String complement, Date reportingDate, String userReported) {
        ReportFichePrestataire r = new ReportFichePrestataire();
        r.setUserReporting(userReporting);
        r.setUserReported(userReported);
        r.setReason(reason);
        r.setComplement(complement);
        r.setReportingDate(reportingDate);
        return r;
    }/*
    private ReportFichePrestataire creerSignalementPhoto(String userReporting, String reason, String complement, Date reportingDate, String userReported) {
        ReportPhoto r = new ReportPhoto();
        r.setUserReporting(userReporting);
        r.setUserReported(userReported);
        r.setReason(reason);
        r.setComplement(complement);
        r.setReportingDate(reportingDate);
        r.setPhoto(new P);
        return r;
    }
*/
}
        
