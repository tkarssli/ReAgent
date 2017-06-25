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
    private Integer[] nonReactives;
    private List<Integer> selectedChemical;

    public ChemicalAdapter(Context context, Chemical[] chemicals, Integer[] nonReactives, String reagent, List<Integer> selectedChemical) {
        super(context, 0, chemicals);
        this.nonReactives = nonReactives;
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

        int imageId;

        Matcher matcher = pattern.matcher(chemical.chemical);
        matcher.find();
        String name = matcher.group();
        name = name.replace("-","").toLowerCase() + "_" + REAGENT_NAME;
        imageId = convertView.getResources().getIdentifier(name , "drawable", getContext().getPackageName());

        if (imageId != 0){
            holder.chemImage.setImageResource(imageId);
        } else if (Arrays.asList(nonReactives).contains(chemical.id)){
            holder.chemImage.setImageResource(R.drawable.noreaction);
        } else {
            holder.chemImage.setImageResource(R.drawable.placeholder);
        }

        convertView.findViewById(R.id.item_container).setBackgroundColor(getContext().getResources().getColor(R.color.chemical_list_tem));
        for (int x =0; x < selectedChemical.size(); x++){
            if(chemical.id == selectedChemical.get(x)){
                convertView.findViewById(R.id.item_container).setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
            }
        }

        holder.chemName.setText(s_chemical);
//        holder.chemName.setMaxTextSize(44);
//        holder.chemName.mNeedsResize = true;
//        holder.chemName.resizeText();

//        if(s_chemical.equals("pma")){
//            int color = convertView.getResources().getColor(R.color.colorAccent);
//            convertView.findViewById(R.id.item_container).setBackgroundColor(color);
//        }


        return convertView;
    }

    static class ViewHolder{
        public TextView chemName;
        public TextView chemFamily;
        public ImageView chemImage;
    }
}