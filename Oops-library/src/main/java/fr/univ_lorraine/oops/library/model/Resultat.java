package fr.univ_lorraine.oops.library.model;

public class Resultat implements Comparable{
    private final String id;
    private final float score;
    
    public Resultat(String cle, float score){
        this.id = cle;
        this.score = score;
    }
    
    public String getId(){
        return id;
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
