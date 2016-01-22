package fr.univ_lorraine.oops.beans;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;

@Named(value = "testBean")
@SessionScoped
public class TestBean implements Serializable {

    private List<String> liste;
    private String item;

    public TestBean() {
        this.liste = new ArrayList<>();
        this.liste.add("Bonjour");
        this.liste.add("Salut");
    }

    public List<String> getListe() {
        return liste;
    }

    public void setListe(List<String> liste) {
        this.liste = liste;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
    
    public void addItem(String item) {
        this.liste.add(item);
    }
    
    public void maj() {
        this.addItem(this.item);
    }
    
}
