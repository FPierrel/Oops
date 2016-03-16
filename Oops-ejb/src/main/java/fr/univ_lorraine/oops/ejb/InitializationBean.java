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
        pres1.setFormeJuridique(Prestataire.Type.SARL.toString());
        Collection<Categorie> col1 = new ArrayList<>();
        col1.add(tout);
        pres1.setCategories(col1);
        liste.add(pres1);
        //----------------------------------------------
        ArrayList<Adresse> adressesNoupi = new ArrayList<>();
        adressesNoupi.add(this.creerAdresse("8", "Rue Lafayette", "", "57000", "METZ (57000-57050-57070)", "France"));
        Prestataire pres2 = this.creerPrestataire("Restopolitan", "Gérard", "André", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "restopolitan@gmail.com",
                "0123456789", "Restopolitan", 12, 45603, adressesNoupi);
        pres2.setDescription("Restaurant Ouvert du Mardi midi au Dimanche midi inclus\n"
                + "Fermé le dimanche soir, lundi  toute la journée .\n"
                + "\n"
                + "Début des services :\n"
                + "12 heures le midi / dernière prise de commande 13h30min \n"
                + "19H30 le soir / dernière prise de commande 21h45 min\n"
                + "\n"
                + "\n"
                + "Le restaurant vous accueille dans un cadre chaleureux où plusieurs petites salles vous permettent de passer un agréable moment tout en dégustant la cuisine au goût du jour d'Hervé Klein.\n"
                + "Situées sur plusieurs niveaux, les salles peuvent accueillir jusqu'à 15 couverts");
        pres2.setFormeJuridique(Prestataire.Type.SAS.toString());
        Collection<Categorie> col2 = new ArrayList<>();
        col2.add(c3);
        col2.add(c3c);
        pres2.setCategories(col2);
        Avis avis1 = new Avis();
        avis1.setContenu("Accueil moyen, restaurant surcoté, je regrette mon repas");
        avis1.setOwner(pres1);
        avis1.setNoteCom(1);
        avis1.setNoteDelai(3);
        avis1.setNotePrix(2);
        avis1.setpDate(new Date());
        avis1.setNoteQualite(2);
        avis1.setLoginPrestaire("Restopolitan");
        pres2.addAvis(avis1);

        liste.add(pres2);
        //----------------------------------------------
        ArrayList<Adresse> adressesPhilippe = new ArrayList<>();
        adressesPhilippe.add(this.creerAdresse("17", "Rue Saint-Pierre", "", "57000", "METZ (57000-57050-57070)", "France"));
        Prestataire pres3 = this.creerPrestataire("Phil Logistic", "Montrat", "Philippe", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "montrat@aol.com",
                "0123456789", "Phil Logistic", 31, 20000, adressesPhilippe);
        pres3.setDescription("Depuis de nombreuses années, notre entreprise prend en charge le transport de différents produits : bois, agro-alimentaire, explosifs, chimiques, matériels roulants.\n"
                + "\n"
                + "Soucieux de vous satisfaire, nous vous proposons un service personnalisé et un suivi rigoureux de vos commandes avec une information quotidienne sur leur position.");
        pres3.setFormeJuridique(Prestataire.Type.EURL.toString());
        Collection<Categorie> col3 = new ArrayList<>();
        col3.add(c4);
        col3.add(c4b);
        pres3.setCategories(col3);
        Avis avis2 = new Avis();
        avis2.setContenu("Transport rapide et bien emballé, j'utiliserai de nouveau.");
        avis2.setOwner(pres1);
        avis2.setNoteCom(1);
        avis2.setNoteDelai(3);
        avis2.setNotePrix(2);
        avis2.setpDate(new Date());
        avis2.setNoteQualite(5);
        avis2.setLoginPrestaire("Phil Logistic");
        pres3.addAvis(avis2);
        liste.add(pres3);
        //----------------------------------------------
        /*ArrayList<Adresse> adressesDemon = new ArrayList<>();
        adressesDemon.add(this.creerAdresse("7", "Quai des Iranees", "", "88250", "LA BRESSE (88250)", "Enfer"));
        Prestataire pres4 = this.creerPrestataire("satan", "Satan", "Belzébuth", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "satan@satan.enfer",
                "0123456789", "Ca brule", 1, 666666666, adressesDemon);
        pres4.setDescription("Il fait chaud la bas !");
        pres4.setFormeJuridique(Prestataire.Type.SARL.toString());
        Collection<Categorie> col4 = new ArrayList<>();
        col4.add(c3d);
        pres4.setCategories(col4);
        liste.add(pres4);*/
        //----------------------------------------------      
        ArrayList<Adresse> a = new ArrayList<>();
        a.add(this.creerAdresse("5", "Rue Jacques Callot", "", "54500", "VANDOEUVRE-LES-NANCY (54500)", "France"));
        Prestataire pres5 = this.creerPrestataire("Devis et Jones", "Forlan", "Davis", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "thomas.pagelot8@etu.univ-lorraine.fr",
                "0123456789", "Devis et Jones", 4, 1000000, a);
        pres5.setDescription("Forte de la complémentarité de 2 architectes, aux approches architecturales différentes, notre agence est en mesure de répondre à tout type de projet.");
        pres5.setFormeJuridique(Prestataire.Type.SASU.toString());
        Collection<Categorie> col5 = new ArrayList<>();
        col5.add(c1d);
        pres5.setCategories(col5);
        //pres5.setBanished(true);
        liste.add(pres5);

        //----------------------------------------------
        /*ArrayList<Adresse> adressesNancy = new ArrayList<>();
        adressesNancy.add(this.creerAdresse("18", "Cours Léopold", "", "54000", "NANCY (54100)", "France"));
        Prestataire pres6 = this.creerPrestataire("Riri", "Fifi", "Loulou", "123456", "a@a.b",
                "0123456789", "Oooooh", 1, 126423, adressesNancy);
        pres6.setDescription("Ooooooooooooooooooooooooooooohhhhh la description !!!");
        pres6.setFormeJuridique(Prestataire.Type.EIRL.toString());
        Collection<Categorie> col6 = new ArrayList<>();
        col6.add(c1c);
        col6.add(c1d);
        pres6.setCategories(col6);
        pres6.setBanished(true);
        liste.add(pres6);*/
        //----------------------------------------------
        ArrayList<Adresse> adressesOui = new ArrayList<>();
        adressesOui.add(this.creerAdresse("1", "1 Rue Dr Archambault", "", "54520", "LAXOU (54520)", "France"));
        Prestataire pres7 = this.creerPrestataire("Bernard et Fils", "Bernard", "Jean", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "thibaut.humbert5@etu.univ-lorraine.fr",
                "1523459878", "Bernard et Fils", 2, 14, adressesOui);
        pres7.setDescription("Artisan qualifié, maison fondée depuis plus de 15 ans - Electricité- agréé FFB. Dépannage entretien,installation. Remise aux normes Rénovation complète, disjoncteur, tableau électrique, courant faible, courant fort. Mise à la terre, raccord équipotentiel installation. Depuis 1994... Urgence 7h30 à 23h, 6 jours / 7. Agréé Consuel et Apav.");
        pres7.setFormeJuridique(Prestataire.Type.SA.toString());
        Collection<Categorie> col7 = new ArrayList<>();
        col7.add(c1b);
        col7.add(c1c);
        pres7.setCategories(col7);
        liste.add(pres7);
        //----------------------------------------------
        ArrayList<Adresse> adressesStahl = new ArrayList<>();
        adressesStahl.add(this.creerAdresse("2", "avenue du General Leclerc", "", "54000", "NANCY (54100)", "France"));
        Prestataire pres8 = this.creerPrestataire("Stahl Auto", "Martin", "Patrick", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "stahlauto@gmail.com",
                "0123456789", "Stahl Auto", 20, 15000, adressesStahl);
        pres8.setDescription("Les professionnels de chez Stahl Auto assurent différents services pour votre véhicule 2 et 4 roues : "
                + "passage controle technique, "
                + "révision véhicule, "
                + "devis et réparations (possibilité d'avoir un véhicule de remplacement si plusieurs jours de travail sont nécéssaires, "
                + "vente et reprise de véhicules d'occasion. "
                + "Nous contacter par téléphone ou email(joint à cette fiche), accueil sans rendez-vous du lundi au vendredi 8h-18h.");
        pres8.setFormeJuridique(Prestataire.Type.SNC.toString());
        Collection<Categorie> col8 = new ArrayList<>();
        col8.add(c4d);
        pres8.setCategories(col8);
        liste.add(pres8);
        Avis avisSA = new Avis();
        avisSA.setContenu("Travail sérieux, bonne disponibilité des employés, prix raisonnable.");
        avisSA.setOwner(pres1);
        avisSA.setNoteCom(5);
        avisSA.setNoteDelai(4);
        avisSA.setNotePrix(3);
        avisSA.setpDate(new Date());
        avisSA.setNoteQualite(5);
        avisSA.setLoginPrestaire("Stahl Auto");
        pres8.addAvis(avisSA);
        //----------------------------------------------
        ArrayList<Adresse> adressesTech = new ArrayList<>();
        adressesTech.add(this.creerAdresse("20", "route de Longwy", "", "57100", "THIONVILLE (57100)", "France"));
        Prestataire pres9 = this.creerPrestataire("Clark et Al", "Clark", "David", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "clarketal@ultime.de",
                "0666886688", "Clark et Al", 368, 11652000, adressesTech);
        pres9.setDescription("Spécialiste des solutions en informatique et de réputation mondiale, Clark et Al ouvre sa première succursale dans le Grand Est français. "
                + "De la conception à la réalisation, nos équipes accompagnent votre société tout au long de votre projet. "
                + "Pour toute information, visitez notre site www.clarketal.io ou contactez nous via les renseignements disponibles sur la fiche. ");
        pres9.setFormeJuridique(Prestataire.Type.SARL.toString());
        Collection<Categorie> col9 = new ArrayList<>();
        col9.add(c5);
        col9.add(c5a);
        col9.add(c5b);
        pres9.setCategories(col9);
        liste.add(pres9);
        Avis avisTech = new Avis();
        avisTech.setContenu("Le meilleur dans le domaine informatique, un service de qualité et un maintien constant de la solution. ");
        avisTech.setOwner(pres2);
        avisTech.setNoteCom(5);
        avisTech.setNoteDelai(4);
        avisTech.setNotePrix(4);
        avisTech.setpDate(new Date());
        avisTech.setNoteQualite(5);
        avisTech.setLoginPrestaire("Clark et Al");
        pres9.addAvis(avisTech);
        Avis avisTech2 = new Avis();
        avisTech2.setContenu("L'assurance d'un service impécable, je recommande. ");
        avisTech2.setOwner(pres1);
        avisTech2.setNoteCom(5);
        avisTech2.setNoteDelai(4);
        avisTech2.setNotePrix(5);
        avisTech2.setpDate(new Date());
        avisTech2.setNoteQualite(5);
        avisTech2.setLoginPrestaire("Clark et Al");
        pres9.addAvis(avisTech2);
        Avis avisTech3 = new Avis();
        avisTech3.setContenu("Des tarifs au dessus de la moyenne, mais la qualité est au rendez vous.  ");
        avisTech3.setOwner(pres1);
        avisTech3.setNoteCom(5);
        avisTech3.setNoteDelai(5);
        avisTech3.setNotePrix(2);
        avisTech3.setpDate(new Date());
        avisTech3.setNoteQualite(5);
        avisTech3.setLoginPrestaire("Clark et Al");
        pres9.addAvis(avisTech3);
        //----------------------------------------------
        ArrayList<Adresse> adressesBTP = new ArrayList<>();
        adressesBTP.add(this.creerAdresse("33", "avenue du Cassoulet", "", "31000", "TOULOUSE (31000-31100-31200-31300-31400-31500)", "France"));
        Prestataire pres10 = this.creerPrestataire("BTP Grand Selve", "Pennington", "Ty", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "btpgrandselve@sudouest.fr",
                "0233775521", "BTP Grand Selve", 1500, 500001, adressesBTP);
        pres10.setDescription("Réunissant tous les métiers de la construction à la réhabilitation à Toulouse, BTP Grand Selve est un acteur historique, indépendant, de proximité,  au service de ses clients, de ses partenaires et de ses collaborateurs. "
                + "Avec une activité principale de gros œuvre dans les travaux neufs, la société forge également son savoir-faire dans l’entreprise générale, les travaux de rénovation lourde structurante, les travaux services et les travaux de second œuvre (bois et plâtre). ");
        pres10.setFormeJuridique(Prestataire.Type.EURL.toString());
        Collection<Categorie> col10 = new ArrayList<>();
        col10.add(c1);
        col10.add(c1a);
        col10.add(c1d);
        pres10.setCategories(col10);
        liste.add(pres10);
        Avis avisB = new Avis();
        avisB.setContenu("Entrepreneur sérieux, travail impécable. ");
        avisB.setOwner(pres1);
        avisB.setNoteCom(5);
        avisB.setNoteDelai(4);
        avisB.setNotePrix(4);
        avisB.setpDate(new Date());
        avisB.setNoteQualite(5);
        avisB.setLoginPrestaire("BTP Grand Selve");
        pres10.addAvis(avisB);
        //----------------------------------------------
        ArrayList<Adresse> adressesR = new ArrayList<>();
        adressesR.add(this.creerAdresse("69", "rue des Verts", "", "42000", "SAINT-ETIENNE (42000-42100-42230)", "France"));
        Prestataire pres11 = this.creerPrestataire("Cabinet Schmidt et Russel", "Russel", "John", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "cser@stannet.fr",
                "0233775574", "Cabinet Schmidt et Russel", 20, 233000000, adressesR);
        pres11.setDescription("Les banques sont soumises à de nouvelles règlementations qui visent à développer la concurrence et la transparence. "
                + "Ces nouvelles réglementations devraient conduire à une baisse de 8% à 15% du PNB des banques de détail. Parallèlement, les banques doivent s’adapter à des clients mieux informés et plus exigeants. "
                + "Elles doivent également lutter contre l’arrivée de nouveaux entrants dans le domaine des paiements. "
                + "Notre approche : La définition de la trajectoire est aussi importante que celle de la cible.");
        pres11.setFormeJuridique(Prestataire.Type.SAS.toString());
        Collection<Categorie> col11 = new ArrayList<>();
        col11.add(c2);
        pres11.setCategories(col11);
        liste.add(pres11);
        Avis avisR = new Avis();
        avisR.setContenu("Un travail de qualité, des conseillers éfficaces et des solutions innovantes ");
        avisR.setOwner(pres1);
        avisR.setNoteCom(5);
        avisR.setNoteDelai(3);
        avisR.setNotePrix(3);
        avisR.setpDate(new Date());
        avisR.setNoteQualite(5);
        avisR.setLoginPrestaire("Cabinet Schmidt et Russel");
        pres11.addAvis(avisR);

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
        liste.add(this.creerSignalementFiche("john", "Autre", "Le prestataire n'a pas facturé le prix initialement négocié.", new Date(), "philippe"));
        liste.add(this.creerSignalementFiche("philippe", "Usurpation d'indentité", "Cette entreprise utilise mes informations pour se faire passer pour mon entreprise.", new Date(), "Jacky"));
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
