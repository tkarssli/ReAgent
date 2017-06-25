package tkarssli.reagent.util;

/**
 * Created by tkars on 6/18/2017.
 */

public class Chemical {
    public String chemical;
    public String family;
    public Integer id;

    public void chemical(){

    }
    public boolean equals(Chemical chem){
        return chemical.equals(chem.chemical) && id.equals(chem.id);
    }
}
