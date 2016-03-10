package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.OpinionManagerBean;
import fr.univ_lorraine.oops.ejb.UserManagerBean;
import fr.univ_lorraine.oops.library.model.Avis;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value="indexBean")
@RequestScoped
public class IndexBean implements Serializable{
    @Inject
    OpinionManagerBean om;
    @Inject
    UserManagerBean umb ; 
    
    private List<Avis> avis;
    private List<Prestataire> best; 
    
    public IndexBean(){
        
    }    
    
    public List<Avis> getLastOpinions(){
        if (avis == null)
            avis = om.getLastOpinions();
        
        return avis;
    }
    
    public List<Prestataire> getBestGrades() {
        if (best==null) {
            best = umb.getBestGrades() ; 
        }
        return best ; 
    }
}
