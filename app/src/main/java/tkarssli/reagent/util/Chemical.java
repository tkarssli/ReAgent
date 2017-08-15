package tkarssli.reagent.util;

/**
 * Created by tkars on 6/18/2017.
 */

public class Chemical {
    public String chemical;
    public Integer id;
    public String url;
    public String[] nr;
    public int rank = 0;

    public void chemical(){

    }
    public boolean equals(Chemical chem){
        return chemical.equals(chem.chemical) && id.equals(chem.id);
    }
}
