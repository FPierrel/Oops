/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.AlbumEntityManager;
import fr.univ_lorraine.oops.library.model.Album;
import fr.univ_lorraine.oops.library.model.Photo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.extensions.model.fluidgrid.FluidGridItem;

/**
 *
 * @author Thomas
 */
@Named(value = "photoBean")
@ViewScoped
public class PhotoBean implements Serializable {

    @Inject
    private AlbumEntityManager albumEM;

    private long idAlbum;
    private Album album;
    private Collection<Photo> photos;
    private String page;
    private Album defaultAlbum;
    private Photo first;

    /**
     * Creates a new instance of PhotoBean
     */
    public PhotoBean() {
    }

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        this.album = this.albumEM.getAlbum(this.idAlbum, request.getRemoteUser());

        this.defaultAlbum = this.albumEM.getDefault(request.getRemoteUser());
        this.photos = album.getPhotos();
    }

    public void initAlternative() {
        this.defaultAlbum = this.albumEM.getDefault(page);
        this.photos = this.defaultAlbum.getPhotos();
    }

    public long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void handleFileUpload(FileUploadEvent event) {
        Photo p = new Photo();
        p.setNomPhoto(event.getFile().getFileName());
        p.setPhoto(event.getFile().getContents());
        this.photos.add(p);

        this.albumEM.addPhoto(p, this.album.getId());
    }

    public Collection<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Collection<Photo> photos) {
        this.photos = photos;
    }

    public Collection<String> getPhotosBase64() {
        ArrayList<String> al = new ArrayList<>();
        for (Photo p : this.getPhotos()) {
            al.add(p.getPhotoBase64());
        }

        return al;
    }

    public List<FluidGridItem> getPhotosGrid() {
        ArrayList<FluidGridItem> al = new ArrayList<>();
        for (Photo p : this.photos) {
            al.add(new FluidGridItem(p));
        }

        return al;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void deletePhoto(long id) {
        System.out.println("SALUT LES ENFANTS");
        this.albumEM.deletePhoto(this.album, id);
    }

    public void test() throws Exception {
        System.out.println("BONJOUR LES AMIS");
        throw new Exception("Va bien te faire mettre");
    }

    public Photo getFirst() {
        return first;
    }

    public void setFirst(Photo first) {
        this.first = first;
    }

}
