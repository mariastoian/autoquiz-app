package ase.dam.autoquiz.data_sql;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ase.dam.autoquiz.R;

//adaptor personalizat pt linia din lisview pt fragment indicatoare
public class CustomAdaptor extends ArrayAdapter {

    private final Activity context;
    private final Integer[] imageIDarray;
    private final String[] numeIndicatoare;
    private final String[] categorieIndicatoare;
    public CustomAdaptor(Activity context, String[] numeIndicatoare, String[] categorieIndicatoare, Integer[] imageIDArrayParam){

        super(context, R.layout.listview_row , numeIndicatoare);
        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.numeIndicatoare = numeIndicatoare;
        this.categorieIndicatoare = categorieIndicatoare;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);
        TextView nameText = (TextView) rowView.findViewById(R.id.textview_denumire);
        TextView infoText = (TextView) rowView.findViewById(R.id.textview_categorie);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageview1ID);
        nameText.setText(numeIndicatoare[position]);
        infoText.setText(categorieIndicatoare[position]);
        imageView.setImageResource(imageIDarray[position]);
        return rowView;

    };

}
