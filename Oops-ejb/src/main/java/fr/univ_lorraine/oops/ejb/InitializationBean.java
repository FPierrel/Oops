package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Administrateur;
import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Soumissionnaire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
@LocalBean
public class InitializationBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @PostConstruct
    public void init() {
        this.addPrestataires();
        this.addSoumissionaires();
    }
       
    /**
     * 1 login, 1 motDePasse, 1 mail, 1 telephone, 1 nomEntreprise et 1 adresse : OBLIGATOIRES !
     */
    private void addPrestataires() {
        ArrayList<Prestataire> liste = new ArrayList<>();
        //DEBUT AJOUTS DE PRESTATAIRES : (MODIFIER CI-DESSOUS)
        //----------------------------------------------
        ArrayList<Adresse> adressesJose = new ArrayList<>();
        adressesJose.add(this.creerAdresse("10", "rue des OGM", "", "75000", "Paris", "France"));
        liste.add(this.creerPrestataire("jose", "Bové", "José", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "jose.bove@gmail.com", 
                "0123456789", "Jose Bove Enterprise", 999, 1500000, adressesJose));
        //----------------------------------------------
        ArrayList<Adresse> adressesNoupi = new ArrayList<>();
        adressesNoupi.add(this.creerAdresse("39", "rue de la colle", "", "57000", "Metz Ville", "Allemagne"));
        liste.add(this.creerPrestataire("noupi", "Le lapin", "Noupi", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "noupi@lelapin.com", 
                "0123456789", "T'es sale Noupi", 3, 10, adressesNoupi));
        //----------------------------------------------
        ArrayList<Adresse> adressesPhilippe = new ArrayList<>();
        adressesPhilippe.add(this.creerAdresse("99", "rue des héros", "", "57000", "Metz Ville", "Allemagne"));
        liste.add(this.creerPrestataire("philippe", "Le héros", "Philippe", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "philippe@hitman-lecobra.com", 
                "0123456789", "Je sais où tu te caches !", 1, 99999999, adressesPhilippe));
        //----------------------------------------------
        ArrayList<Adresse> adressesDemon = new ArrayList<>();
        adressesDemon.add(this.creerAdresse("666", "avenue de l'enfer", "", "666 666", "Le four", "Enfer"));
        liste.add(this.creerPrestataire("satan", "Satan", "Belzébuth", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "satan@satan.enfer", 
                "0123456789", "Ca brule", 1, 666666666, adressesDemon));
        //----------------------------------------------
        ArrayList<Adresse> adressesNeufchateau = new ArrayList<>();
        adressesNeufchateau.add(this.creerAdresse("12", "Rue de la rue", "", "88300", "Neufchateau", "France"));
        liste.add(this.creerPrestataire("Jacky", "Tuning", "Jacky", "123456", "jacky@tuning.fr", 
                "0123456789", "La Caisse à Jacky", 1, 50, adressesNeufchateau));
        //----------------------------------------------
        ArrayList<Adresse> adressesNancy = new ArrayList<>();
        adressesNancy.add(this.creerAdresse("38", "avenue de oui", "", "54000", "Nancy", "France"));
        liste.add(this.creerPrestataire("Riri", "Fifi", "Loulou", "123456", "a@a.b", 
                "0123456789", "Oooooh", 1, 126423, adressesNancy));
        //----------------------------------------------
        ArrayList<Adresse> adressesOui = new ArrayList<>();
        adressesOui.add(this.creerAdresse("Oui", "Oui", "", "88630", "Coussey", "France"));
        liste.add(this.creerPrestataire("Oui", "Oui", "Oui", "123456", "Oui@Oui.Oui", 
                "0123456789", "Oui", 1, 126423, adressesOui));
        //----------------------------------------------
        
        //FIN AJOUTS DE PRESTATAIRE.
        for (Prestataire p : liste) {
            this.em.persist(p);
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
