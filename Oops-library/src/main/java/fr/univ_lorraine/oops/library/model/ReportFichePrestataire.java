/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Romain
 */
@Entity
public class ReportFichePrestataire extends Report implements Serializable {
/*
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Prestataire prestataire;

    public ReportFichePrestataire() {
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }
*/
}
