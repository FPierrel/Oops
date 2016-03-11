package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.AlbumEntityManager;
import fr.univ_lorraine.oops.library.model.Album;
import fr.univ_lorraine.oops.library.model.Photo;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named(value = "albumBean")
@ViewScoped
public class AlbumBean implements Serializable {

    @Inject
    private AlbumEntityManager albumEM;

    private Long id;
    private String nomAlbum;
    private Collection<Album> albums;
    private Album album;
    private UploadedFile file;
    private Collection<Photo> photos;

    public AlbumBean() {

    }

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String login = request.getRemoteUser();

        this.albums = this.albumEM.getAllAlbumsUser(login);
    }

    public void init2() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        this.album = this.albumEM.getAlbum(this.id);
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

    public void ajouterAlbum() {
        Album album = new Album();
        album.setNom(nomAlbum);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String login = request.getRemoteUser();

        album.setLogin(login);

        albumEM.addAlbum(album, login);
    }

    public Collection<Album> getAlbums() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String login = request.getRemoteUser();

        return this.albumEM.getAllAlbumsUser(login);
    }

    public void setAlbums(Collection<Album> albums) {
        this.albums = albums;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void deleteAlbum(Album album) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String login = request.getRemoteUser();

        albumEM.deleteAlbum(album, login);
    }

    public boolean isAlbum() {
        return this.album == null ? false : true;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Collection<Photo> getPhotos() {
        return this.album.getPhotos();
    }

    public StreamedContent toContent(Photo p) {
        InputStream in = new ByteArrayInputStream(p.getPhoto());
        StreamedContent image = new DefaultStreamedContent(in, "image/jpeg");
        return image;
    }

    public StreamedContent retrieve(byte[] array) {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            return new DefaultStreamedContent(new ByteArrayInputStream(array));
        }
    }

}
