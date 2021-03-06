package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.FichePrestataireBean;
import fr.univ_lorraine.oops.ejb.OpinionManagerBean;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Commentaire;
import fr.univ_lorraine.oops.library.model.Prestataire;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Named(value = "ficheBean")
@ViewScoped
public class FicheBean implements Serializable {

    @Inject
    private FichePrestataireBean fiche;
    @Inject
    OpinionManagerBean om;

    private String page;
    private Prestataire prestataire;
    private List<Avis> lAvis;
    private List<String> categories;
    private String[] commentaires;
    private int rate1;
    private int rate2;
    private int rate3;
    private int rate4;
    private String opinion;
    private String commentaire;
    private int noteGlobPrix = 0;
    private int noteGlobQualite = 0;
    private int noteGlobDelai = 0;
    private int noteGlobCom = 0;
    private int noteTotal = 0;
    private int nbAvis = 0;

    public FicheBean() {

    }

    /**
     * Méthode permettant d'initialiser les champs pour la fiche du prestataire.
     */
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (this.page.isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mauvaise requête ! Utilisez un lien correct !", null);
            context.addMessage(null, message);
        }
        this.prestataire = this.fiche.getPrestataireLogin(this.page);
        if (this.prestataire == null && !(this.page.isEmpty())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mauvaise requête ! Utilisez un lien correct !", null);
            context.addMessage(null, message);
        }
        this.lAvis = this.fiche.getPrestataireAvis(this.prestataire.getLogin());

        if (lAvis.get(0) != null) {
            this.commentaires = new String[lAvis.size()];
            this.noteTotal = this.prestataire.getAverage();
            this.noteGlobCom = this.prestataire.getCommunication();
            this.noteGlobDelai = this.prestataire.getDelay();
            this.noteGlobPrix = this.prestataire.getPrice();
            this.noteGlobQualite = this.prestataire.getQuality();
            this.nbAvis = this.lAvis.size();
        } else {
            this.lAvis = new ArrayList<>();
        }

        this.categories = this.fiche.getPrestataireCategoriesNames(this.prestataire.getLogin());
    }

    /**
     * Méthode permettant de laisser un avis.
     */
    public void saveOpinion() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Prestataire pres = this.fiche.getPrestataireLogin(this.page);
        if (!this.opinion.isEmpty()) {
            this.om.saveOpinion(rate1, rate3, rate2, rate4, opinion, new Date(), pres, request.getRemoteUser());
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veuillez renseigner un avis !", null);
            context.addMessage(null, message);
        }
        this.init();
    }

    /**
     * Méthode permettant de laisser un commentaire sur un avis.
     * @param i : indice de l'avis à commenter.
     */
    public void saveComment(int i) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Prestataire pres = this.fiche.getPrestataireLogin(this.page);
        String com = request.getParameter("co" + i);
        if (com != null && !com.equals("")) {
            this.om.saveComment(new Date(), request.getRemoteUser(), this.lAvis.get(i), com);
        }
        this.init();
    }

    public void removeOpinion(Avis a) {
        this.om.removeOpinion(a);
        this.init();
    }

    public void removeComment(Commentaire c, int i) {
        this.om.removeComment(c, this.lAvis.get(i));
        this.init();
    }

    public Prestataire getPrestataire() {
        return this.prestataire;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
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

    public int getNoteGlobPrix() {
        return (int) noteGlobPrix;
    }

    public void setNoteGlobPrix(int noteGlobPrix) {
        this.noteGlobPrix = noteGlobPrix;
    }

    public int getNoteGlobQualite() {
        return (int) noteGlobQualite;
    }

    public void setNoteGlobQualite(int noteGlobQualite) {
        this.noteGlobQualite = noteGlobQualite;
    }

    public int getNoteGlobDelai() {
        return (int) noteGlobDelai;
    }

    public void setNoteGlobDelai(int noteGlobDelai) {
        this.noteGlobDelai = noteGlobDelai;
    }

    public int getNoteGlobCom() {
        return (int) noteGlobCom;
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

    public String[] getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String[] commentaires) {
        this.commentaires = commentaires;
    }

    /**
     * Méthode donnant l'image de profil d'un utilisateur.
     * @param owner : utilisateur.
     * @return la photo de profil de l'utilisateur.
     */
    public String getProfilePicture(Utilisateur owner) {
        if (owner instanceof Prestataire) {
            if (((Prestataire) owner).getProfilePicture() != null) {
                return ((Prestataire) owner).getProfilePicture().getPhotoBase64();
            }
        }

        return null;
    }
    
    /**
     * Méthode donnant l'image de profil d'un utilisateur à partir de son login.
     * @param login : login de l'utilisateur.
     * @return la photo de profil de l'utilisateur.
     */
    public String getProfilePictureByLogin(String login) {
        Utilisateur owner = this.fiche.getPrestataireLogin(login);
        if (owner instanceof Prestataire) {
            if (((Prestataire) owner).getProfilePicture() != null) {
                return ((Prestataire) owner).getProfilePicture().getPhotoBase64();
            }
        }

        return null;
    }
}
