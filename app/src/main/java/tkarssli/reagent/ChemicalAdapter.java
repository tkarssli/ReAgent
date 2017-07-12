package tkarssli.reagent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tkarssli.reagent.util.Chemical;

/**
 * Created by tkars on 6/18/2017.
 */

public class ChemicalAdapter extends ArrayAdapter<Chemical> {
    private String REAGENT_NAME;
    private List<Integer> selectedChemical;

    public ChemicalAdapter(Context context, Chemical[] chemicals, String reagent, List<Integer> selectedChemical) {
        super(context, 0, chemicals);
        this.REAGENT_NAME = reagent;
        this.selectedChemical = selectedChemical;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        Chemical chemical = getItem(position);
        String s_chemical = chemical.chemical;

        Pattern pattern = Pattern.compile("[a-zA-Z]+-*[a-zA-Z]+");

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chemical,parent, false);
            holder = new ViewHolder();
            holder.chemName = (TextView) convertView.findViewById(R.id.chemName);
            holder.chemImage = (ImageView) convertView.findViewById(R.id.chemImage);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Matcher matcher = pattern.matcher(chemical.chemical);
        matcher.find();
        String name = matcher.group();
        name = name.replace("-","").toLowerCase() + "_" + REAGENT_NAME;
        int imageId = convertView.getResources().getIdentifier(name , "drawable", getContext().getPackageName());

        View view = (View) holder.chemImage.getParent();

        if (imageId != 0){
            holder.chemImage.setImageResource(imageId);
//            View view = (View) holder.chemImage.getParent();
            view.setClickable(false);
        } else if (chemical.nr != null){
            if(Arrays.asList(chemical.nr).contains(REAGENT_NAME)){
                holder.chemImage.setImageResource(R.drawable.noreaction);

                view.setClickable(true);
            } else {
                holder.chemImage.setImageResource(R.drawable.unknown);
                view.setClickable(true);
            }
        }

        convertView.findViewById(R.id.item_container).setBackgroundColor(getContext().getResources().getColor(R.color.chemical_list_tem));
        for (int x =0; x < selectedChemical.size(); x++){
            if(chemical.id == selectedChemical.get(x)){
                convertView.findViewById(R.id.item_container).setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
            }
        }

        holder.chemName.setText(s_chemical);
        return convertView;
    }

    static class ViewHolder{
        public TextView chemName;
        public ImageView chemImage;
    }
}