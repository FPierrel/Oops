package fr.univ_lorraine.oops.beans;

import fr.univ_lorraine.oops.ejb.OpinionManagerBean;
import fr.univ_lorraine.oops.library.model.Avis;
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
    
    private List<Avis> avis;
    
    public IndexBean(){
        
    }    
    
    public List<Avis> getLastOpinions(){
        if (avis == null)
            avis = om.getLastOpinions();
        
        return avis;
    }
}
