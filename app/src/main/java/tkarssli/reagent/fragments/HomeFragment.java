package tkarssli.reagent.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tkarssli.reagent.HomeAdapter;
import tkarssli.reagent.MainActivity;
import tkarssli.reagent.R;
import tkarssli.reagent.SelectActivity;
import tkarssli.reagent.util.Chemical;

/**
 * Created by tkars on 6/17/2017.
 */

public class HomeFragment extends Fragment {
    private HomeAdapter homeAdapter;
    private PopupWindow mPopupWindow;
    private Context mContext;
    private ListView mListView;
    private View currentSelection;
    public ArrayList<String> nrFlags=new ArrayList<String>();

    ListView list;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Set Title
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("ReAgent");

        mContext = getContext();

        // Clear Checked Items
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mListView = (ListView)rootView.findViewById(R.id.home_list);

        list = (ListView) rootView.findViewById(R.id.home_list);
        homeAdapter = new HomeAdapter(((MainActivity) getActivity()).selectedChemicals);
        list.setAdapter(homeAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                final View customView = inflater.inflate(R.layout.popup_layout,null);

                // Change list item background color
                currentSelection = homeAdapter.getView(position,view,parent);
                currentSelection.findViewById(R.id.ll).setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
//                currentSelection.setBackgroundColor(getResources().getColor(R.color.colorAccent));



                // Initialize a new instance of popup window
                mPopupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

                // TEST should allow backpress to dismiss popup
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                mPopupWindow.setFocusable(true);


                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (currentSelection != null){
                            currentSelection.findViewById(R.id.ll).setBackgroundColor(getContext().getResources().getColor(R.color.chemical_list_tem));
                        }

                        }
                });
//                mPopupWindow.setOutsideTouchable(true);
//                mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
//                        {
//                            mPopupWindow.dismiss();
//                            return true;
//                        }
//                        return false;
//                    }
//                });
                mPopupWindow.setAnimationStyle(R.style.Animation);


                // Set click handlers
                customView.findViewById(R.id.popup_delete_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList selectedChemicals = ((MainActivity) getActivity()).selectedChemicals;

                        selectedChemicals.remove(position);


                        homeAdapter.notifyDataSetChanged();
                        calculateChemicalProbability();
                        mPopupWindow.dismiss();

                    }
                });

                customView.findViewById(R.id.popup_information_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Chemical chemical = homeAdapter.getChemical(position);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(chemical.url));
                        startActivity(intent);
                        mPopupWindow.dismiss();

                    }
                });

                // Get reference for the trasparent linear layout background
                        LinearLayout background_layout = (LinearLayout) customView.findViewById(R.id.popup_transparent_background);
                background_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupWindow.dismiss();
                            }
                        });

                // Get regerence for container
                LinearLayout container_layout = (LinearLayout) customView.findViewById(R.id.popup_container);
                container_layout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(mListView, Gravity.CENTER,0,0);
            }

        });

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
        menu.findItem(R.id.action_reset).setVisible(true);
        menu.findItem(R.id.action_choose).setVisible(true);
        menu.findItem(R.id.action_done).setVisible(false);
        menu.findItem(R.id.action_no_reaction).setVisible(false);
        ((MainActivity)getActivity()).mDrawerToggle.setDrawerIndicatorEnabled(true);
        ((MainActivity)getActivity()).mDrawerToggle.setDrawerSlideAnimationEnabled(true);

    }
    @Override
    public void onResume(){
        super.onResume();
        calculateChemicalProbability();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                MainActivity mainActivity = ((MainActivity)getActivity());
                ArrayList<MainActivity.SelectedItem> selectedChemicals = mainActivity.selectedChemicals;
                selectedChemicals.clear();
                homeAdapter = new HomeAdapter(selectedChemicals);
                homeAdapter.reset();
                list.setAdapter(homeAdapter);
                nrFlags = new ArrayList<String>();

                homeAdapter.notifyDataSetChanged();

                // Do Fragment menu item stuff here
                return true;

            case R.id.action_choose:
                Intent intent = new Intent(getActivity(), SelectActivity.class);
                startActivity(intent);
                getActivity().finish();
                return true;

            default:
                break;
        }
        return false;
    }

    public void calculateChemicalProbability(){
        Map.Entry<Chemical, Integer> max = null;
        TextView tv = (TextView) rootView.findViewById(R.id.probability_text) ;

        ArrayList selectedChemicals = ((MainActivity) getActivity()).selectedChemicals;

        if (selectedChemicals.size() > 0 && selectedChemicals != null){
            Map<Chemical, Integer> map = new HashMap<>();

            for(Object i : selectedChemicals){
                MainActivity.SelectedItem si = (MainActivity.SelectedItem) i;

                Integer val;
                val = map.get(si.chemical);

                for(Object o : si.chemical.nr){
                    if( nrFlags.contains(o)){
                        map.put(si.chemical, val == null ? 1 : val+ 1);
                    }
                }
                val = map.get(si.chemical);
                map.put(si.chemical, val == null ? 1 : (val*1) + 1);
            }

            for (Map.Entry<Chemical, Integer> e : map.entrySet()){
                if(max == null || e.getValue() > max.getValue()){
                    max = e;
                }
            }

            String s = "";
            int count = 0;
            for (Map.Entry<Chemical, Integer> e : map.entrySet()){
                if( e.getValue() == max.getValue() && !e.getKey().equals(max.getKey()) ){
                    s += e.getKey().chemical + ", ";
                }
            }
            s += max.getKey().chemical;
            tv.setText(s);
        } else {
            tv.setText("Start by selecting a reagent test from the top left");
        }


    }
    public void deleteListItem(){

    }

    public static class ChemChanceItem{
        public int weight;
        public String name;
        public int id;

    }

}
