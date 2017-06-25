package tkarssli.reagent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tkarssli.reagent.fragments.HomeFragment;
import tkarssli.reagent.fragments.FolinFragment;
import tkarssli.reagent.fragments.FroehdeFragment;
import tkarssli.reagent.fragments.LiebermannFragment;
import tkarssli.reagent.fragments.MandelinFragment;
import tkarssli.reagent.fragments.MarquisFragment;
import tkarssli.reagent.fragments.MeckeFragment;
import tkarssli.reagent.fragments.SimonFragment;
import tkarssli.reagent.util.Chemical;
import tkarssli.reagent.util.ChemicalResourceReader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private HashMap<Integer, String> checkedReagents;
    private boolean mDoneState = false;

    private NavigationView navigationView;
    public Chemical[] chemicals;
    public ArrayList<SelectedItem> selectedChemicals = new ArrayList<SelectedItem>();

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        checkedReagents = (HashMap) intent.getSerializableExtra("checkedReagents");
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            return;
        }
        initViews();


    }

    private void initViews(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Parse in chemicals
        ChemicalResourceReader reader = new ChemicalResourceReader(this.getResources(), R.raw.chemicals);
        chemicals = reader.constructUsingGson(Chemical[].class);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.content_container) != null) { // Check if container has been created
            HomeFragment firstFragment = new HomeFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_container, firstFragment, "home").commit();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        mDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_menu = nav_view.getMenu();

        Set<Integer> values = checkedReagents.keySet();
        Integer key;

        for(Iterator it = values.iterator(); it.hasNext();){
             key = (Integer)it.next();
            String value = checkedReagents.get(key);
            if (value.equals("marquis")){nav_menu.findItem(R.id.marquis).setEnabled(true);}
            if (value.equals("mecke")){nav_menu.findItem(R.id.mecke).setEnabled(true);}
            if (value.equals("mandelin")){nav_menu.findItem(R.id.mandelin).setEnabled(true);}
            if (value.equals("simon")){nav_menu.findItem(R.id.simon).setEnabled(true);}
            if (value.equals("liebermann")){nav_menu.findItem(R.id.liebermann).setEnabled(true);}
            if (value.equals("froehde")){nav_menu.findItem(R.id.froehde).setEnabled(true);}
            if (value.equals("folin")){nav_menu.findItem(R.id.folin).setEnabled(true);}
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
            mDoneState = true;
            invalidateOptionsMenu();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);




        MenuItem menuItem = menu.findItem(R.id.action_settings);
        menuItem.setVisible(mDoneState);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done){

            // Go back to home fragment on button press
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
            transaction.replace(R.id.content_container, homeFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            mDoneState = false; // Set button visibility back to false
            invalidateOptionsMenu(); // Reload ActionBar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        item.setEnabled(false);


        if (id == R.id.marquis) {
            MarquisFragment marquisFragment;
            marquisFragment = (MarquisFragment) getSupportFragmentManager().findFragmentByTag("marquis");

            if(marquisFragment != null){
                transaction.replace(R.id.content_container, marquisFragment);
            } else {
                marquisFragment = new MarquisFragment();
            }
            transaction.replace(R.id.content_container, marquisFragment, "marquis");
            if(!marquisFragment.isVisible()){
                transaction.addToBackStack(null);
            }
            transaction.commit();

        } else if (id == R.id.mecke) {
            MeckeFragment meckeFragment;
            meckeFragment = (MeckeFragment) getSupportFragmentManager().findFragmentByTag("mecke");

            if(meckeFragment != null){
                transaction.replace(R.id.content_container, meckeFragment);
            } else {
                meckeFragment = new MeckeFragment();
            }
            transaction.replace(R.id.content_container, meckeFragment, "mecke");
            if(!meckeFragment.isVisible()){
                transaction.addToBackStack(null);
            }
            transaction.commit();

        } else if (id == R.id.mandelin) {
            MandelinFragment mandelinFragment;
            mandelinFragment = (MandelinFragment) getSupportFragmentManager().findFragmentByTag("mandelin");

            if(mandelinFragment != null){
                transaction.replace(R.id.content_container, mandelinFragment);
            } else {
                mandelinFragment = new MandelinFragment();
            }
            transaction.replace(R.id.content_container, mandelinFragment, "mandelin");
            if(!mandelinFragment.isVisible()){
                transaction.addToBackStack(null);
            }
            transaction.commit();
        } else if (id == R.id.simon) {
            SimonFragment simonFragment;
            simonFragment = (SimonFragment) getSupportFragmentManager().findFragmentByTag("simon");

            if(simonFragment != null){
                transaction.replace(R.id.content_container, simonFragment);
            } else {
                simonFragment = new SimonFragment();
            }
            transaction.replace(R.id.content_container, simonFragment, "simon");
            if(!simonFragment.isVisible()){
                transaction.addToBackStack(null);
            }
            transaction.commit();

        } else if (id == R.id.liebermann) {
            LiebermannFragment liebermannFragment;
            liebermannFragment = (LiebermannFragment) getSupportFragmentManager().findFragmentByTag("liebermann");

            if(liebermannFragment != null){
                transaction.replace(R.id.content_container, liebermannFragment);
            } else {
                liebermannFragment = new LiebermannFragment();
            }
            transaction.replace(R.id.content_container, liebermannFragment, "liebermann");
            if(!liebermannFragment.isVisible()){
                transaction.addToBackStack(null);
            }
            transaction.commit();

        } else if (id == R.id.froehde) {
            FroehdeFragment froehdeFragment;
            froehdeFragment = (FroehdeFragment) getSupportFragmentManager().findFragmentByTag("froehde");

            if(froehdeFragment != null){
                transaction.replace(R.id.content_container, froehdeFragment);
            } else {
                froehdeFragment = new FroehdeFragment();
            }
            transaction.replace(R.id.content_container, froehdeFragment, "froehde");
            if(!froehdeFragment.isVisible()){
                transaction.addToBackStack(null);
            }
            transaction.commit();

        } else if (id == R.id.folin) {
            FolinFragment folinFragment;
            folinFragment = (FolinFragment) getSupportFragmentManager().findFragmentByTag("folin");

            if(folinFragment != null){
                transaction.replace(R.id.content_container, folinFragment);
            } else {
                folinFragment = new FolinFragment();
            }
            transaction.replace(R.id.content_container, folinFragment, "folin");
            if(!folinFragment.isVisible()){
                transaction.addToBackStack(null);
            }
            transaction.commit();
        }
        mDoneState = true;
        invalidateOptionsMenu();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Set which Nav drawer item is selected
    public void SetNavItemChecked(int id)
    {
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.getItem(id);
        menuItem.setChecked(true);
    }

    public static class SelectedItem {
        final public String reagent;
        final public Chemical chemical;
        public SelectedItem(String reagent, Chemical chemical){
            this. reagent = reagent;
            this.chemical = chemical;
        }

        public boolean equals(SelectedItem item){
            return reagent.equals(item.reagent) && chemical.equals(item.chemical);
        }

    }
}


