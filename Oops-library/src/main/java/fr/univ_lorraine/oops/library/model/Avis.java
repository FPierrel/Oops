package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Avis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int noteCom ; 
    private int notePrix ; 
    private int noteQualite ; 
    private int noteDelai ; 
    private String contenu ; 
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Commentaire> commentaires;
    @Temporal(TemporalType.TIMESTAMP)
    private Date pDate ; 
    private Utilisateur owner ;
    private String loginPrestaire;
    private boolean moderated;
            
    public Avis () {
        this.commentaires = new ArrayList<>() ; 
        this.moderated = false;
    }
    
    public Avis (int nC, int nP, int nQ, int nD, String contenu) {
        this.commentaires = new ArrayList<>() ; 
        this.noteCom=nC;
        this.notePrix=nP;
        this.noteQualite=nQ;
        this.noteDelai=nD;
        this.contenu=contenu;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avis)) {
            return false;
        }
        Avis other = (Avis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.univ_lorraine.oops.library.model.Avis[ id=" + id + " ]";
    }

    public int getNotePrix() {
        return notePrix;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Collection<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Collection<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public int getNoteQualite() {
        return noteQualite;
    }
    public int getNoteDelai() {
        return noteDelai;
    }  

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public int getNoteCom() {
        return noteCom;
    }

    public void setNoteCom(int noteCom) {
        this.noteCom = noteCom;
    }

    public void setNotePrix(int notePrix) {
        this.notePrix = notePrix;
    }

    public void setNoteQualite(int noteQualite) {
        this.noteQualite = noteQualite;
    }


    public void setNoteDelai(int noteDelai) {
        this.noteDelai = noteDelai;
    }

    public Utilisateur getOwner() {
        return owner;
    }

    public void setOwner(Utilisateur owner) {
        this.owner = owner;
    }

    public boolean isModerated() {
        return moderated;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    } 

    public String getLoginPrestaire() {
        return loginPrestaire;
    }

    public void setLoginPrestaire(String loginPrestaire) {
        this.loginPrestaire = loginPrestaire;
    }
    
}
