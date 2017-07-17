package tkarssli.reagent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import java.util.HashMap;

public class SelectActivity extends AppCompatActivity {
    private HashMap<Integer,String> checkedReagents = new HashMap<Integer,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Reagents to Test");
        setSupportActionBar(toolbar);

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        if(checked) {
            view.setBackgroundColor(getResources().getColor(R.color.colorButtonRed));
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.marquis_box:
                if (checked) {
                    checkedReagents.put(0, "marquis");
                } else {
                    checkedReagents.remove(0);}
                break;
            case R.id.mecke_box:
                if (checked) {
                    checkedReagents.put(1, "mecke");
                } else {
                    checkedReagents.remove(1);}
                break;
            case R.id.mandelin_box:
                if (checked) {
                    checkedReagents.put(2, "mandelin");
                } else {
                    checkedReagents.remove(2);}
                break;
            case R.id.simon_box:
                if (checked) {
                    checkedReagents.put(3, "simon");
                } else {
                    checkedReagents.remove(3);}
                break;
            case R.id.liebermann_box:
                if (checked) {
                    checkedReagents.put(4, "liebermann");
                } else {
                    checkedReagents.remove(4);}
                break;
            case R.id.froehde_box:
                if (checked) {
                    checkedReagents.put(5, "froehde");
                } else {
                    checkedReagents.remove(5);}
                break;
            case R.id.folin_box:
                if (checked) {
                    checkedReagents.put(6, "folin");
                } else {
                    checkedReagents.remove(6);}
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.select_next){

            // Go back to home fragment on button press
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("checkedReagents", checkedReagents);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
