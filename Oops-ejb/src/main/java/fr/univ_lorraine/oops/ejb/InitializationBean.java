package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Administrateur;
import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Categorie;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.ArrayList;
import java.util.Collection;
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
    }

    /**
     * 1 login, 1 motDePasse, 1 mail, 1 telephone, 1 nomEntreprise et 1 adresse : OBLIGATOIRES !
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
        adressesJose.add(this.creerAdresse("10", "rue des OGM", "", "75000", "Paris", "France"));
        Prestataire pres1 = this.creerPrestataire("jose", "Bové", "José", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "jose.bove@gmail.com",
                "0123456789", "Jose Bove Enterprise", 999, 1500000, adressesJose);
        Collection<Categorie> col1 = new ArrayList<>();
        col1.add(tout);
        pres1.setCategories(col1);
        liste.add(pres1);
        //----------------------------------------------
        ArrayList<Adresse> adressesNoupi = new ArrayList<>();
        adressesNoupi.add(this.creerAdresse("39", "rue de la colle", "", "57000", "Metz", "Allemagne"));
        Prestataire pres2 = this.creerPrestataire("noupi", "Le lapin", "Noupi", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "noupi@lelapin.com", 
                "0123456789", "T'es sale Noupi", 3, 10, adressesNoupi);
        Collection<Categorie> col2 = new ArrayList<>();
        col2.add(c4d);
        col2.add(c4c);
        pres2.setCategories(col2);
        liste.add(pres2);
        //----------------------------------------------
        ArrayList<Adresse> adressesPhilippe = new ArrayList<>();
        adressesPhilippe.add(this.creerAdresse("99", "rue des héros", "", "57000", "Metz", "Allemagne"));
        Prestataire pres3 = this.creerPrestataire("philippe", "Le héros", "Philippe", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "philippe@hitman-lecobra.com", 
                "0123456789", "Je sais où tu te caches !", 1, 99999999, adressesPhilippe);
        Collection<Categorie> col3 = new ArrayList<>();
        col3.add(c1);
        pres3.setCategories(col3);
        liste.add(pres3);
        //----------------------------------------------
        ArrayList<Adresse> adressesDemon = new ArrayList<>();
        adressesDemon.add(this.creerAdresse("666", "avenue de l'enfer", "", "666 666", "Le four", "Enfer"));
        Prestataire pres4 = this.creerPrestataire("satan", "Satan", "Belzébuth", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "satan@satan.enfer", 
                "0123456789", "Ca brule", 1, 666666666, adressesDemon);
        Collection<Categorie> col4 = new ArrayList<>();
        col4.add(c3d);
        pres4.setCategories(col4);
        liste.add(pres4);
        //----------------------------------------------
        ArrayList<Adresse> adressesNeufchateau = new ArrayList<>();
        adressesNeufchateau.add(this.creerAdresse("12", "Rue de la rue", "", "88300", "Neufchateau", "France"));
        Prestataire pres5 = this.creerPrestataire("Jacky", "Tuning", "Jacky", "123456", "jacky@tuning.fr", 
                "0123456789", "La Caisse à Jacky", 1, 50, adressesNeufchateau);
        Collection<Categorie> col5 = new ArrayList<>();
        col5.add(c4d);
        pres5.setCategories(col5);
        liste.add(pres5);
        //----------------------------------------------
        ArrayList<Adresse> adressesNancy = new ArrayList<>();
        adressesNancy.add(this.creerAdresse("38", "avenue de oui", "", "54000", "Nancy", "France"));
        Prestataire pres6 = this.creerPrestataire("Riri", "Fifi", "Loulou", "123456", "a@a.b", 
                "0123456789", "Oooooh", 1, 126423, adressesNancy);
        Collection<Categorie> col6 = new ArrayList<>();
        col6.add(c1c);
        col6.add(c1d);
        pres6.setCategories(col6);
        liste.add(pres6);
        //----------------------------------------------
        ArrayList<Adresse> adressesOui = new ArrayList<>();
        adressesOui.add(this.creerAdresse("Oui", "Oui", "", "88630", "Coussey", "France"));
        Prestataire pres7 = this.creerPrestataire("Oui", "Oui", "Oui", "123456", "Oui@Oui.Oui", 
                "0123456789", "Oui", 1, 126423, adressesOui);
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
     * 1 login, 1 motDePasse, 1 mail, 1 nom, 1 prenom et 1 adresse : OBLIGATOIRES !
     */
    private void addSoumissionaires() {
        ArrayList<Soumissionnaire> liste = new ArrayList<>();
        //DEBUT AJOUTS DE SOUMISSIONNAIRES : (MODIFIER CI-DESSOUS)
        //----------------------------------------------
        ArrayList<Adresse> adressesJohn = new ArrayList<>();
        adressesJohn.add(this.creerAdresse("22", "rue de la zappette", "", "54000", "Nancy", "France"));
        liste.add(this.creerSoumissionnaire("john", "Siffleur", "John", "123456", "john.siffleur@nenette.fr", 
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
        if(login == "jose")
            prestataire.setGroupe(Administrateur.administrateur);
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
        return soumissionnaire;
    }
    
    private Adresse creerAdresse(String numero, String rue, String complement, String codePostal, String ville, String pays) {
        Adresse adresse = new Adresse();
        adresse.setNumero(numero);
        adresse.setRue(rue);
        adresse.setComplement(complement);
        adresse.setCodePostal(codePostal);
        adresse.setVille(ville);
        adresse.setPays(pays);
        return adresse;
    }

}
