package ase.dam.autoquiz.ui.main;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ListView;
import ase.dam.autoquiz.R;
import ase.dam.autoquiz.data_sql.CustomAdaptor;


/**
 * A simple {@link Fragment} subclass.
 */
public class Indicatoare extends Fragment {
    String[] numeIndic = {"Drum Alunecos","Inainte/Dreapta","Stop","Interzis"
    ,"Trecere de pietoni","Limita 40km/h","Limita 90km/h"};
    String[] categorieIndic = {
            "avertizare", "obligatoriu", "obligatoriu",
            "obligatoriu","avertizare","obligatoriu","obligatoriu"
    };
    Integer[] imageArray = {R.drawable.drum_alunecos,
            R.drawable.oblig_dr_in, R.drawable.oprire,
    R.drawable.interzis,R.drawable.trecere, R.drawable.patru, R.drawable.noua,};
    ListView listView;
    public Indicatoare() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_indicatoare, container, false);

        CustomAdaptor customAdaptor = new CustomAdaptor(getActivity(), numeIndic, categorieIndic, imageArray);
        listView = (ListView) view.findViewById(R.id.list_view_indicatoare);
        listView.setAdapter(customAdaptor);
        return view;
    }
}
