package tkarssli.reagent;

import android.app.Activity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import tkarssli.reagent.ChemicalAdapter;
import tkarssli.reagent.MainActivity;
import tkarssli.reagent.R;
import tkarssli.reagent.util.Chemical;

/**
 * Created by tkars on 6/22/2017.
 */

public class onChemicalItemClickListener implements AdapterView.OnItemClickListener {
    private ChemicalAdapter adapter;
    private MainActivity activity;
    private ArrayList<Integer> selectedRows;
    private String reagent;
    private Integer[] nonReactives;
    private ListView list;

    public onChemicalItemClickListener(ChemicalAdapter adapter,ListView list, MainActivity activity, ArrayList<Integer> selectedRows, String reagent,Integer[] nonReactives){
        this.adapter = adapter;
        this.activity = activity;
        this.selectedRows = selectedRows;
        this.reagent = reagent;
        this.nonReactives = nonReactives;
        this.list = list;


    }
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        ArrayList<MainActivity.SelectedItem> selectedChemicals = activity.selectedChemicals;

        Chemical chemical = adapter.getItem(position);
        MainActivity.SelectedItem selectedItem = new MainActivity.SelectedItem(reagent,chemical);
        int arrayLength = selectedRows.size();


        for(int x = 0; x < arrayLength; x++){
            if(arrayLength != 0){ // Check if array is empty
            // Handle selection, add/remove from HashMap, change status
                if(selectedRows.get(x) == chemical.id){
                    selectedRows.remove(x); // Remove from selection list

                    for (int y = 0; y < selectedChemicals.size(); y++){ // Remove from selection Array
                        if(selectedItem.equals(selectedChemicals.get(y))){
                            selectedChemicals.remove(y);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
        }
        if(!Arrays.asList(nonReactives).contains(chemical.id)) { // Disallow selection of nonreactive chemicals
            selectedRows.add(chemical.id);
            selectedChemicals.add(selectedItem);
        }
        adapter.notifyDataSetChanged(); // Refresh ListView
        }
};