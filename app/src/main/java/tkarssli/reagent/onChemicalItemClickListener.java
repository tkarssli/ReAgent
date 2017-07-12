package tkarssli.reagent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

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
    private ArrayList selectedItemQueue;

    public onChemicalItemClickListener(ChemicalAdapter adapter, ListView list, ArrayList<Integer> selectedRows, String reagent, ArrayList<MainActivity.SelectedItem>selectedItemQueue, MainActivity activity){
        this.adapter = adapter;
        this.activity = activity;
        this.selectedRows = selectedRows;
        this.reagent = reagent;
        this.nonReactives = nonReactives;
        this.list = list;
        this.selectedItemQueue = selectedItemQueue;


    }
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Chemical chemical = adapter.getItem(position);
        MainActivity.SelectedItem selectedItem = new MainActivity.SelectedItem(reagent,chemical);
        int arrayLength = selectedRows.size();


        for(int x = 0; x < arrayLength; x++){
            if(arrayLength != 0){ // Check if array is empty
            // Handle selection, add/remove from HashMap, change status
                if(selectedRows.get(x) == chemical.id){
                    selectedRows.remove(x); // Remove from local selection array

                    for (int y = 0; y < selectedItemQueue.size(); y++){ // Remove from local selection queue
                        if(selectedItem.equals(selectedItemQueue.get(y))){
                            selectedItemQueue.remove(y);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
        }
        String[] non_reactives = adapter.getItem(position).nr;
        Boolean bool=false;
        for (String s : non_reactives) if (s.equals(reagent)) bool = true;
        if(!bool) { // Disallow selection of nonreactive chemicals
            selectedRows.add(chemical.id);
            selectedItemQueue.add(selectedItem);
        }
        adapter.notifyDataSetChanged(); // Refresh ListView
        }
};