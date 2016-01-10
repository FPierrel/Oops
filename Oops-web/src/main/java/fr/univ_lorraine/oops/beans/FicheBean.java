package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.FichePrestataireBean;
import fr.univ_lorraine.oops.ejb.OpinionManagerBean;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.io.Serializable;
import static java.lang.Math.round;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "ficheBean")
@ConversationScoped
public class FicheBean implements Serializable {

    @Inject
    private FichePrestataireBean fiche;
    @Inject
    OpinionManagerBean om ; 
    
    private String page;
    private Prestataire prestataire;
    private List<Avis> lAvis ; 
    private int rate1 ; 
    private int rate2 ; 
    private int rate3 ; 
    private int rate4 ; 
    private String opinion ; 
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private int noteGlobPrix ; 
    private int noteGlobQualite ; 
    private int noteGlobDelai ; 
    private int noteGlobCom ; 
    private int noteTotal ; 
    private int nbAvis ; 
    
    /**
     * Creates a new instance of FicheBean
     */
    public FicheBean() {

    }

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if(this.page.isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mauvaise requête ! Utilisez un lien correct !", null);
            context.addMessage(null, message); 
        }        
        this.prestataire = this.fiche.getPrestataireLogin(this.page);        
        if(this.prestataire == null && !(this.page.isEmpty())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mauvaise requête ! Utilisez un lien correct !", null);
            context.addMessage(null, message);            
        }   
        this.lAvis = this.fiche.getPrestataireAvis(this.prestataire.getLogin()); 
        this.noteGlobCom = (int) round(this.fiche.getNoteGlobCom(this.prestataire.getLogin())) ;
        this.noteGlobQualite = (int) round(this.fiche.getNoteGlobQualite(this.prestataire.getLogin())) ;
        this.noteGlobPrix = (int) round(this.fiche.getNoteGlobDelai(this.prestataire.getLogin())) ;
        this.noteGlobDelai = (int) round(this.fiche.getNoteGlobPrix(this.prestataire.getLogin())) ;
        this.nbAvis = this.lAvis.size() ; 
        this.noteTotal = (this.noteGlobCom + this.noteGlobDelai + this.noteGlobPrix + this.noteGlobQualite)/4 ; 
    }
    
    public void saveOpinion() {
        Prestataire pres = this.fiche.getPrestataireLogin(this.page);   
        this.om.saveOpinion(rate1, rate3, rate2, rate4, opinion, new Date(),pres) ; 
    }
    
    public Prestataire getPrestataire() {
        return this.prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public String getPage() {
        return this.page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Avis> getlAvis() {
        return lAvis;
    }

    public void setlAvis(List<Avis> lAvis) {
        this.lAvis = lAvis;
    }

    public int getRate1() {
        return rate1;
    }

    public void setRate1(int rate1) {
        this.rate1 = rate1;
    }

    public int getRate2() {
        return rate2;
    }

    public void setRate2(int rate2) {
        this.rate2 = rate2;
    }

    public int getRate3() {
        return rate3;
    }

    public void setRate3(int rate3) {
        this.rate3 = rate3;
    }

    public int getRate4() {
        return rate4;
    }

    public void setRate4(int rate4) {
        this.rate4 = rate4;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public int getNoteGlobPrix() {
        return (int)noteGlobPrix;
    }

    public void setNoteGlobPrix(int noteGlobPrix) {
        this.noteGlobPrix = noteGlobPrix;
    }

    public int getNoteGlobQualite() {
        return (int)noteGlobQualite;
    }

    public void setNoteGlobQualite(int noteGlobQualite) {
        this.noteGlobQualite = noteGlobQualite;
    }

    public int getNoteGlobDelai() {
        return (int)noteGlobDelai;
    }

    public void setNoteGlobDelai(int noteGlobDelai) {
        this.noteGlobDelai = noteGlobDelai;
    }

    public int getNoteGlobCom() {
        return (int)noteGlobCom;
    }

    public void setNoteGlobCom(int noteGlobCom) {
        this.noteGlobCom = noteGlobCom;
    }

    public int getNoteTotal() {
        return noteTotal;
    }

    public void setNoteTotal(int noteTotal) {
        this.noteTotal = noteTotal;
    }

    public int getNbAvis() {
        return nbAvis;
    }

    public void setNbAvis(int nbAvis) {
        this.nbAvis = nbAvis;
    }
 
}
