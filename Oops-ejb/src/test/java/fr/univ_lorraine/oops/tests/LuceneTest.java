package fr.univ_lorraine.oops.tests;

import fr.univ_lorraine.oops.ejb.LuceneBean;
import fr.univ_lorraine.oops.library.model.Administrateur;
import fr.univ_lorraine.oops.library.model.Adresse;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;    

public class LuceneTest {
    private LuceneBean lb;

    public void LuceneTest() {

    }
    
    @Before
    public void setUp() throws IOException{
        lb = new LuceneBean();
        this.indexAllPrestataires();
    }

    @Test
    public void indexPrestataireTest() throws IOException {
        LuceneBean lb = new LuceneBean();
        Assert.assertEquals(0, lb.search("Jacky").size());
        //----------------------------------------------
        ArrayList<Adresse> adressesNeufchateau = new ArrayList<>();
        adressesNeufchateau.add(this.creerAdresse("12", "Rue de la rue", "", "88300", "Neufchateau", "France"));
        lb.indexPrestataire(this.creerPrestataire("Jacky", "Tuning", "Jacky", "123456", "jacky@tuning.fr",
                "0123456789", "La Caisse à Jacky", 1, 50, adressesNeufchateau));
        //----------------------------------------------
        
        Assert.assertEquals(1, lb.search("Jacky").size());
    }
    
    @Test
    public void removePrestataireTest()
    {
        lb.removePrestataire("Jacky");
        Assert.assertEquals(0, lb.search("Jacky").size());        
    }
    
    @Test
    public void simpleExactSearchTest(){
        Assert.assertEquals(1, lb.search("Jacky").size());
    }
    
    @Test
    public void fuzzySearchTest(){
        Assert.assertEquals(1, lb.search("Jack").size());
        Assert.assertEquals(1, lb.search("JaCkY").size());
        Assert.assertEquals(1, lb.search("acky").size());
        Assert.assertEquals(1, lb.search("La caisse").size());
        Assert.assertEquals(1, lb.search("lac aissea jakcy").size());
    }
    
    @Test
    public void updatePrestataireTest() throws IOException{
        LuceneBean lb = new LuceneBean();
        Prestataire p;
        Assert.assertEquals(0, lb.search("Jacky").size());
        //----------------------------------------------
        ArrayList<Adresse> adressesNeufchateau = new ArrayList<>();
        adressesNeufchateau.add(this.creerAdresse("12", "Rue de la rue", "", "88300", "Neufchateau", "France"));
        p = this.creerPrestataire("Jacky", "Tuning", "Jacky", "123456", "jacky@tuning.fr",
                "0123456789", "La Caisse à Jacky", 1, 50, adressesNeufchateau);
        lb.indexPrestataire(p);
        //----------------------------------------------        
        Assert.assertEquals(1, lb.search("La Caisse à Jacky").size());
        Assert.assertEquals(0, lb.search("Artiste").size());
        p.setNomEntreprise("Artiste");
        lb.updatePrestataire(p);
        Assert.assertEquals(0, lb.search("La Caisse à Jacky").size());
        Assert.assertEquals(1, lb.search("Artiste").size());
    }

    private void indexAllPrestataires(){
               ArrayList<Prestataire> liste = new ArrayList<>();
        //DEBUT AJOUTS DE PRESTATAIRES : (MODIFIER CI-DESSOUS)
        //----------------------------------------------
        ArrayList<Adresse> adressesJose = new ArrayList<>();
        adressesJose.add(this.creerAdresse("10", "rue des OGM", "", "75000", "Paris", "France"));
        liste.add(this.creerPrestataire("jose", "Bové", "José", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "jose.bove@gmail.com", 
                "0123456789", "Jose Bove Enterprise", 999, 1500000, adressesJose));
        //----------------------------------------------
        ArrayList<Adresse> adressesNoupi = new ArrayList<>();
        adressesNoupi.add(this.creerAdresse("39", "rue de la colle", "", "57000", "Metz", "Allemagne"));
        liste.add(this.creerPrestataire("noupi", "Le lapin", "Noupi", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "noupi@lelapin.com", 
                "0123456789", "T'es sale Noupi", 3, 10, adressesNoupi));
        //----------------------------------------------
        ArrayList<Adresse> adressesPhilippe = new ArrayList<>();
        adressesPhilippe.add(this.creerAdresse("99", "rue des héros", "", "57000", "Metz", "Allemagne"));
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
            //Ajout dans le moteur de recherche
            lb.indexPrestataire(p);
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
        if (login == "jose") {
            prestataire.setGroupe(Administrateur.administrateur);
        }
        return prestataire;
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
