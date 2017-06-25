package tkarssli.reagent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tkarssli.reagent.util.Chemical;

public class HomeAdapter extends BaseAdapter {
    private ArrayList<MainActivity.SelectedItem> mData = new ArrayList<MainActivity.SelectedItem>();
    public HomeAdapter(ArrayList<MainActivity.SelectedItem> data){
        mData  = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public String getChemical(int position) {
        Chemical chemical = mData.get(position).chemical;
        return chemical.chemical;
    }

    public String getReagent(int position) {
        return (String) mData.get(position).reagent;
    }
    public void reset(){
        mData = new ArrayList<MainActivity.SelectedItem>();
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        Pattern pattern = Pattern.compile("[a-zA-Z]+-*[a-zA-Z]+");
        String reagent = getReagent(position);
        String chemical = getChemical(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adapter_item, parent, false);
            holder = new ViewHolder();

            holder.reagentTextView = (TextView) convertView.findViewById(R.id.reagentTextView);
            holder.chemicalTextView = (TextView) convertView.findViewById(R.id.chemicalTextView);
            holder.gradientImageView = (ImageView) convertView.findViewById(R.id.gradientImageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        Matcher matcher = pattern.matcher(chemical);
        matcher.find();
        String chemical_name = matcher.group();
        matcher = pattern.matcher(reagent);
        matcher.find();
        String reagent_name = matcher.group();

        chemical_name = chemical_name.replace("-","").toLowerCase() + "_" + reagent_name;
        int imageId = convertView.getResources().getIdentifier(chemical_name , "drawable", "tkarssli.reagent");

        holder.gradientImageView.setImageResource(imageId);
        holder.reagentTextView.setText(reagent_name.substring(0,1).toUpperCase() + reagent_name.substring(1));
        holder.chemicalTextView.setText(getChemical(position));

        return convertView;
    }
    static class ViewHolder{
        public TextView reagentTextView, chemicalTextView;
        public ImageView gradientImageView;
    }
}