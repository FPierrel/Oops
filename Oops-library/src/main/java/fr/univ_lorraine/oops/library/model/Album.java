/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.library.model;
 
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
 
 
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
 
/**
 *
 * @author Administrateur
 */
@Entity
public class Album implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomAlbum; 
    @Lob
    private byte[] photo;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "LOGIN", referencedColumnName = "LOGIN",updatable = true, insertable = true)
    private Utilisateur user ;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "album")
    private List<Photo> photos= new ArrayList<Photo>();
     
    public Album() {
    }
 
    public Album(Long id, String nomAlbum, byte[] photo) {
        this.id = id;
        this.nomAlbum = nomAlbum;
        this.photo = photo;
    }
 
  
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getNomAlbum() {
        return nomAlbum;
    }
 
    public void setNomAlbum(String nomAlbum) {
        this.nomAlbum = nomAlbum;
    }
 
    public byte[] getPhoto() {
        return photo;
    }
 
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
 
    public Utilisateur getUser() {
        return user;
    }
 
    public void setUser(Utilisateur user) {
        this.user = user;
    }
 
    public List<Photo> getPhotos() {
        return photos;
    }
 
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    
    
    
}