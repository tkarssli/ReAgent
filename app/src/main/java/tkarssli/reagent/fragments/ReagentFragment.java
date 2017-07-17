package tkarssli.reagent.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class ReagentFragment extends Fragment {

    public ChemicalAdapter adapter;
    public ArrayList<MainActivity.SelectedItem> selectedItemQueue = new ArrayList<MainActivity.SelectedItem>();

    private String mREAGENT = ""; // Reagent name
    public int[] nonReactives; // Non reactive chemical list
    public ArrayList<Integer> mSELECTEDROWS = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity mainActivity = ((MainActivity)getActivity());

        // Set variables
        Bundle bundle = getArguments();
        mREAGENT = bundle.getString("reagent");
        nonReactives = bundle.getIntArray("non_reactives");

        // Chemical array from main activity
        Chemical[] chemicals = ((MainActivity)getActivity()).chemicals;
        adapter = new ChemicalAdapter(mainActivity, chemicals, mREAGENT, mSELECTEDROWS);
        // Inflate layout
        View rootView = inflater.inflate(R.layout.fragment_reagent, container, false);



        // Set Title
        ActionBar actionBar = mainActivity.getSupportActionBar();
        actionBar.setTitle(mREAGENT.substring(0,1).toUpperCase() + mREAGENT.substring(1));

        // Set list adapter
        ListView list = (ListView) rootView.findViewById(R.id.reagent_list);
        list.setAdapter(adapter);

        // Set list click listener
        AdapterView.OnItemClickListener mMessageClickedHandler = new onChemicalItemClickListener(adapter,list, mSELECTEDROWS, mREAGENT, selectedItemQueue, mainActivity);
        list.setOnItemClickListener(mMessageClickedHandler);
        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).SetNavItemChecked(0); // Change drawer item background to reflect status
        int i = ((MainActivity)getActivity()).selectedChemicals.size();
        if(i == 0){ // Clear checked items from fragment if home was reset
            mSELECTEDROWS.clear();
            selectedItemQueue.clear();
            adapter.notifyDataSetChanged();
        } else {
            mSELECTEDROWS.clear();
            selectedItemQueue.clear();

            for (Object o : ((MainActivity)getActivity()).selectedChemicals){
                MainActivity.SelectedItem SI = (MainActivity.SelectedItem) o;
                if(SI.reagent.equals(mREAGENT)){
                    mSELECTEDROWS.add(SI.chemical.id);
                    selectedItemQueue.add(SI);

                }
            }
            adapter.notifyDataSetChanged();
        }
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_reset).setVisible(false);
        menu.findItem(R.id.action_choose).setVisible(false);
//        menu.findItem(R.id.action_no_reaction).setVisible(true);
        ((MainActivity)getActivity()).mDrawerToggle.setDrawerIndicatorEnabled(false);
        ((MainActivity)getActivity()).mDrawerToggle.setDrawerSlideAnimationEnabled(false);
        ((MainActivity)getActivity()).mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_no_reaction){
            mSELECTEDROWS.clear();
            return ((MainActivity)getActivity()).onNoReactionOptionsItemSelected(item, mREAGENT);
        } else if(id == R.id.action_done){
            HomeFragment homeFragment = (HomeFragment)((MainActivity)getActivity()).getSupportFragmentManager().findFragmentByTag("home");
            if(homeFragment.nrFlags.contains(mREAGENT)){
                homeFragment.nrFlags.remove(homeFragment.nrFlags.indexOf(mREAGENT));
            }

            ArrayList selectedChemicals = ((MainActivity) getActivity()).selectedChemicals;


            for(int x =0; x < selectedChemicals.size(); x++){
                MainActivity.SelectedItem i = (MainActivity.SelectedItem) selectedChemicals.get(x);
                if(i.reagent.equals(mREAGENT)){
                    selectedChemicals.remove(x);
                    x = x-1;
                }
            }

            for(Object o : selectedItemQueue){
                selectedChemicals.add((MainActivity.SelectedItem) o);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}