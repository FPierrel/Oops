package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ReportPhoto extends Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne
    private Photo photo;

    public ReportPhoto() {
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

}
