package tkarssli.reagent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tkarssli.reagent.util.Chemical;

/**
 * Created by tkars on 6/18/2017.
 */

public class HomeAdapter extends ArrayAdapter<Chemical> {
    private Chemical[] mChemicals;
    private ArrayList<Chemical> mChemical_list;
    private int mMax = 0;

    public HomeAdapter(Context context, Chemical[] chemicals, ArrayList<Chemical> chemical_list) {
        super(context, 0, chemical_list);
        this.mChemicals = chemicals;
        this.mChemical_list = chemical_list;
    }

//    public Chemical getChemical(int position){
//        return mChemicals[position];
//    }


    // Updates the max on data set change, then calls super;
    @Override
    public void notifyDataSetChanged(){
        if(mChemical_list.size() > 0){
            mMax = mChemical_list.get(0).rank;
        }
        super.notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        Chemical chemical = getItem(position);
        String s_chemical = chemical.chemical;


        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.home_adapter_item,parent, false);
            holder = new ViewHolder();
            holder.chemName = (TextView) convertView.findViewById(R.id.chemicalTextView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if(chemical.rank >= mMax){
            convertView.findViewById(R.id.ll).setBackgroundColor(getContext().getResources().getColor(R.color.maxProb));
        } else if(chemical.rank>= mMax*.75){
            convertView.findViewById(R.id.ll).setBackgroundColor(getContext().getResources().getColor(R.color.highProb));
        } else if(chemical.rank >= mMax*.50){
            convertView.findViewById(R.id.ll).setBackgroundColor(getContext().getResources().getColor(R.color.medProb));
        } else if(chemical.rank >= 0){
            convertView.findViewById(R.id.ll).setBackgroundColor(getContext().getResources().getColor(R.color.lowProb));
        }

//        convertView.findViewById(R.id.item_container).setBackgroundColor(getContext().getResources().getColor(R.color.chemical_list_item));
//        for (int x =0; x < selectedChemical.size(); x++){
//            if(chemical.id == selectedChemical.get(x)){
//                convertView.findViewById(R.id.item_container).setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
//            }
//        }

        holder.chemName.setText(s_chemical);
        return convertView;
    }

    static class ViewHolder{
        public TextView chemName;
    }
}