package fr.univ_lorraine.oops.library.model;

import java.util.Comparator;

public class Resultat implements Comparable{
    private String cle;
    private float score;
    
    public Resultat(String cle, float score){
        this.cle = cle;
        this.score = score;
    }
    
    public String getCle(){
        return cle;
    }
    
    public float getScore(){
        return score;
    }

    @Override
    public int compareTo(Object o) {
        if (score < ((Resultat)o).score)
            return -1;
        else if (score == ((Resultat)o).score)
            return 0;
        else
            return 1;
    }


}
