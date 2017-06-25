package tkarssli.reagent.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import tkarssli.reagent.ChemicalAdapter;
import tkarssli.reagent.MainActivity;
import tkarssli.reagent.R;
import tkarssli.reagent.onChemicalItemClickListener;
import tkarssli.reagent.util.Chemical;

/**
 * Created by tkars on 6/17/2017.
 */

public class FroehdeFragment extends Fragment {
    public ChemicalAdapter adapter;
    private String mREAGENT = "froehde"; // Reagent name
    private final Integer[] nonReactives = {8,10,11,12,13,14,18,19,20,21,27}; // Non reactive chemical list
    private ArrayList<Integer> mSelectedRows = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Chemical array from main activity
        Chemical[] chemicals= ((MainActivity)getActivity()).chemicals;

        // Set Title
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(mREAGENT.substring(0,1).toUpperCase() + mREAGENT.substring(1));

        // Inflate layout
        View rootView = inflater.inflate(R.layout.fragment_froehde, container, false);

        // Set list adapter
        ListView list = (ListView) rootView.findViewById(R.id.froehde_list);
        adapter = new ChemicalAdapter(getActivity(), chemicals, nonReactives, mREAGENT, mSelectedRows);
        list.setAdapter(adapter);

        // Set list click listener
        AdapterView.OnItemClickListener mMessageClickedHandler = new onChemicalItemClickListener(adapter,list,((MainActivity)getActivity()),mSelectedRows,mREAGENT,nonReactives);
        list.setOnItemClickListener(mMessageClickedHandler);

        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).SetNavItemChecked(5); // Change drawer item background to reflect status
        int i = ((MainActivity)getActivity()).selectedChemicals.size();
        if(i == 0){ // Clear checked item from fragment if home was reset
            mSelectedRows.clear();
            adapter.notifyDataSetChanged();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_settings).setVisible(false);
    }
}