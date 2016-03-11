package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.AlbumEntityManager;
import fr.univ_lorraine.oops.library.model.Album;
import fr.univ_lorraine.oops.library.model.Photo;
import java.io.IOException;
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
import org.primefaces.model.UploadedFile;

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
    private boolean options = false;
    private Photo photoToDelete;
    private UploadedFile file;
    private String description;

    public PhotoBean() {
    }

    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        this.album = this.albumEM.getAlbum(this.idAlbum, request.getRemoteUser());

        this.defaultAlbum = this.albumEM.getDefault(request.getRemoteUser());
        this.photos = album.getPhotos();
    }

    public void initAlternative() {
        this.album = this.albumEM.getDefault(page);
        
        if(this.album != null){
            this.photos = this.album.getPhotos();
        }
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
        this.albumEM.addPhoto(p, this.album.getId());
    }

    public Collection<Photo> getPhotos() {
        return this.albumEM.getAllPhotoAlbum(album.getId());
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

    public void deletePhoto(){
        this.options = false;
        this.albumEM.deletePhoto(this.album, this.photoToDelete);
    }
    
    public void modifyDesc(){
        this.options = false;
        this.albumEM.updatePhotoDesc(this.photoToDelete, this.description);
    }

    public Photo getFirst() {
        return first;
    }

    public void setFirst(Photo first) {
        this.first = first;
    }

    public boolean getOptions() {
        return options;
    }

    public void setOptions(Photo p) {
        this.photoToDelete = p;
        this.description = this.photoToDelete.getDescription();
        
        if (this.photoToDelete.equals(p)) {
            this.options = !options;
        }
    }

    public void resetOptions() throws Exception {
        this.photoToDelete = null;
        this.options = false;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            Photo p = new Photo();
            p.setNomPhoto(this.file.getFileName());
            p.setPhoto(this.getFile().getContents());
            this.albumEM.addPhoto(p, this.album.getId());
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Album getDefaultAlbum() {
        return defaultAlbum;
    }

    public void setDefaultAlbum(Album defaultAlbum) {
        this.defaultAlbum = defaultAlbum;
    }
    
    

}
