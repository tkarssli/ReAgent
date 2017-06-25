package tkarssli.reagent.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import tkarssli.reagent.HomeAdapter;
import tkarssli.reagent.MainActivity;
import tkarssli.reagent.R;
import tkarssli.reagent.util.ChemProbComparator;
import tkarssli.reagent.util.Chemical;

import static android.R.id.list;

/**
 * Created by tkars on 6/17/2017.
 */

public class HomeFragment extends Fragment {
    private HomeAdapter homeAdapter;
    ListView list;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set Title
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("ReAgent");

        // Clear Checked Items
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        list = (ListView) rootView.findViewById(R.id.home_list);
        homeAdapter = new HomeAdapter(((MainActivity) getActivity()).selectedChemicals);
        list.setAdapter(homeAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ///....
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_settings).setVisible(true);
        menu.findItem(R.id.action_done).setVisible(false);
    }
    @Override
    public void onResume(){
        super.onResume();
        calculateChemicalProbability();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                MainActivity mainActivity = ((MainActivity)getActivity());
                ArrayList<MainActivity.SelectedItem> selectedChemicals = mainActivity.selectedChemicals;
                selectedChemicals.clear();
                homeAdapter = new HomeAdapter(selectedChemicals);
                homeAdapter.reset();
                list.setAdapter(homeAdapter);
                homeAdapter.notifyDataSetChanged();

                // Do Fragment menu item stuff here
                return true;

            default:
                break;
        }
        return false;
    }

    public void calculateChemicalProbability(){
        Map.Entry<String, Integer> max = null;



        ArrayList selectedChemicals = ((MainActivity) getActivity()).selectedChemicals;

        if (selectedChemicals.size() > 0 && selectedChemicals != null){
            Map<String, Integer> map = new HashMap<>();

            for(Object i : selectedChemicals){
                MainActivity.SelectedItem si = (MainActivity.SelectedItem) i;
                Integer val = map.get(si.chemical.chemical);
                map.put(si.chemical.chemical, val == null ? 1 : val + 1);
            }

            for (Map.Entry<String, Integer> e : map.entrySet()){
                if(max == null || e.getValue() > max.getValue()){
                    max = e;
                }
            }

            TextView tv = (TextView) rootView.findViewById(R.id.probability_text) ;
            tv.setText(max.getKey());
        }


    }

    public static class ChemChanceItem{
        public int weight;
        public String name;
        public int id;

    }

}
