/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.library.model;
 
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
 
/**
 *
 * @author Administrateur
 */
@Entity
public class Photo implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomPhoto;
    @Lob
    private byte[] photo;
    @Transient
    private String photoBase64;
    
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    
    public String getPhotoBase64() {
        return "data:image/png;base64," + new sun.misc.BASE64Encoder().encode(photo); 
    }
 
    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }
 
    public String getNomPhoto() {
        return nomPhoto;
    }
 
    public void setNomPhoto(String nomPhoto) {
        this.nomPhoto = nomPhoto;
    }
 
    public byte[] getPhoto() {
        return photo;
    }
 
    public void setPhoto(byte[] photo) {
        this.photo = photo;
        this.photoBase64 = "data:image/png;base64," + new sun.misc.BASE64Encoder().encode(photo); 
    }
 
    
    
}

