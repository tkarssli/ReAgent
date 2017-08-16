package tkarssli.reagent.util;

import java.util.ArrayList;

/**
 * Created by tkars on 6/18/2017.
 */

public class Chemical {
    public String chemical;
    public Integer id;
    public String url;
    public String[] nr;
    public int rank = 0;
    public ArrayList<String> posReagents = new ArrayList<>();

    public void chemical(){

    }
    public boolean equals(Chemical chem){
        return chemical.equals(chem.chemical) && id.equals(chem.id);
    }
}
