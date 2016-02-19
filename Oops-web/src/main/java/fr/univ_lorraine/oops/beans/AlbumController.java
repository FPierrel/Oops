/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.beans;
 
import fr.univ_lorraine.oops.ejb.AlbumEntityManager;
import fr.univ_lorraine.oops.library.model.Album;
import fr.univ_lorraine.oops.library.model.Photo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
 
 
/**
 *
 * @author Administrateur
 */
@Named(value = "albumControllerBean")
@RequestScoped
public class AlbumController {
    
    @Inject
    private AlbumEntityManager albumEM; 
    
    private Part file;
    private String nomAlbum;
    private String nomPhoto;
    private List<Album> albums = new ArrayList<>();
    private Map albumsUser = new HashMap();
    private List<Photo> photos = new ArrayList<>();
 
    public List<Photo> getPhotos() {
        return photos;
    }
 
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
 
   
    public Map getAlbumsUser() {
        return albumsUser;
    }
 
    public void setAlbumsUser(Map albumsUser) {
        this.albumsUser = albumsUser;
    }
    
    
    public List<Album> getAlbums() {
        return albums;
    }
 
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
 
    
    public String getNomPhoto() {
        return nomPhoto;
    }
 
    public void setNomPhoto(String nomPhoto) {
        this.nomPhoto = nomPhoto;
    }
    
    
 
    public String getNomAlbum() {
        return nomAlbum;
    }
 
    public void setNomAlbum(String nomAlbum) {
        this.nomAlbum = nomAlbum;
    }
    
    
    public Part getFile() {
        return file;
    }
 
    public void setFile(Part file) {
        this.file = file;
    }
    
    public byte[] upload(Part file) throws SQLException, IOException {
        byte[] photo;
        InputStream input = null;
          try {
                input=file.getInputStream();       
        } catch (IOException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "error uploading file",null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        for (int length = 0; (length = input.read(buffer)) > 0;) output.write(buffer, 0, length);
        photo=output.toByteArray(); 
        return photo;
        }
    
   
     
     public String addAlbum() throws SQLException, IOException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String login = facesContext.getExternalContext().getRemoteUser(); 
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Album album = new Album();
        album.setNomAlbum(nomAlbum); 
        album= albumEM.addAlbum(album, login);  
        session.setAttribute("idAlbum", album.getId()); 
        return "addPhoto?faces-redirect=true";
         
     }
     
     public String addPhoto() throws SQLException, IOException{
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
         Photo photo = new Photo();
         photo.setNomPhoto(nomPhoto);
         photo.setPhoto(this.upload(file)); 
         albumEM.addPhoto(photo, (Long) session.getAttribute("idAlbum")); 
         nomPhoto="";
         return "addPhoto?faces-redirect=true";
     }
     
    @PostConstruct
    public void getAllAlbumuser() { 
        albums.clear();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String login = facesContext.getExternalContext().getRemoteUser(); 
        albums=albumEM.getAllAlbumsUser(login);   
    }
    
    
    public List<Photo> getAllPhotoAlbum(Long idAlbum){ 
        photos=albumEM.getAllPhotoAlbum(idAlbum); 
        return albumEM.getAllPhotoAlbum(idAlbum);
    }
    
    public  void deleteAlbum(Long idAlbum){
        
        albumEM.deleteAlbum(idAlbum);
         
    }
}