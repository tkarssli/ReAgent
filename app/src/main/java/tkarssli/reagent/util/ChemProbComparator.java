package tkarssli.reagent.util;

import java.util.Comparator;

import tkarssli.reagent.fragments.HomeFragment.ChemChanceItem;

/**
 * Created by tkars on 6/24/2017.
 */

public class ChemProbComparator implements Comparator<ChemChanceItem>
{
    @Override
    public int compare(ChemChanceItem x, ChemChanceItem y)
    {
        // Assume neither string is null. Real code should
        // probably be more robust
        // You could also just return x.length() - y.length(),
        // which would be more efficient.
        if (x.weight < y.weight)
        {
            return -1;
        }
        if (x.weight > y.weight)
        {
            return 1;
        }
        return 0;
    }
}