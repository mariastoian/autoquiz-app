package ase.dam.autoquiz.ui.main;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import ase.dam.autoquiz.R;
import ase.dam.autoquiz.data_sql.Question;
public class Plus extends Fragment {

    EditText editText_text_intrebare;
    EditText editText_rasp_A;
    EditText editText_rasp_B;
    EditText editText_rasp_C;
    Spinner spn_category;
    RadioGroup rg_raspuns;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    Button button_save;

    public Plus() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plus, container, false);
        editText_text_intrebare = view.findViewById(R.id.add_text);
        editText_rasp_A = view.findViewById(R.id.add_rasp_A);
        editText_rasp_B = view.findViewById(R.id.add_rasp_B);
        editText_rasp_C = view.findViewById(R.id.add_rasp_C);
        radioButton1=view.findViewById(R.id.btn_A);
        radioButton2=view.findViewById(R.id.btn_B);
        radioButton3=view.findViewById(R.id.btn_C);
        rg_raspuns = view.findViewById(R.id.rg_add);
        button_save = view.findViewById(R.id.add_intr_btn);
        spn_category = view.findViewById(R.id.add_spinner_categ);
        ArrayAdapter<CharSequence> categoryAdapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.categorytypelist,
                        R.layout.support_simple_spinner_dropdown_item);
        spn_category.setAdapter(categoryAdapter);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Question intrebare = createIntrebareFromView(v);
                    //lista_intrebari.add(intrebare);
                    Toast.makeText(getActivity(), intrebare.toString(), Toast.LENGTH_LONG).show();
                    Intrebari intr=new Intrebari();
                    Bundle bundle = new Bundle();
                    //bundle.putParcelable("intrebari_adaugate", (Parcelable) lista_intrebari);
                    bundle.putParcelable("intrebari_adaugate",intrebare);
                    intr.setArguments(bundle);
                    FragmentManager fragmentManager=getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container,intr).addToBackStack(null).commit();
                    //finish();
               }
            }
        });
        return view;
    }
    private Question createIntrebareFromView(View view) {
        String text = editText_text_intrebare.getText().toString();
        String textA = editText_rasp_A.getText().toString();
        String textB = editText_rasp_B.getText().toString();
        String textC = editText_rasp_C.getText().toString();
        String cat = spn_category.getSelectedItem().toString();
        Integer id=1;
        if(cat.matches("Usor"))
        {
            id=1;
        }
        if(cat.matches("Mediu"))
        {
            id=2;
        }
        if(cat.matches("Greu"))
        {
            id=3;
        }
       // RadioButton selectedRasp = view.findViewById(rg_raspuns.getCheckedRadioButtonId());
        //RadioGroup rg = view.findViewById(R.id.rg_add);
       // rg.getCheckedRadioButtonId();
        //Integer rasp = Integer.parseInt(rg.toString());
        Integer rasp=0;
        int selectedRadioButtonID = rg_raspuns.getCheckedRadioButtonId();
        if(selectedRadioButtonID == radioButton1.getId()) {
            rasp=1;
        }
        if(selectedRadioButtonID == radioButton2.getId()) {
            rasp=2;
        }
        if(selectedRadioButtonID == radioButton3.getId()) {
            rasp=3;
        }
        return new Question(text, textA, textB, textC, rasp,id);//categorie de adaugat
    }

    private boolean validate() {
        if (editText_text_intrebare.getText() == null || editText_text_intrebare.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), R.string.add_intr_text_error_message, Toast.LENGTH_LONG).show();
            return false;
        }
        if (editText_rasp_A.getText() == null || editText_rasp_A.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), R.string.add_intr_text2_error_message, Toast.LENGTH_LONG).show();
            return false;
        }
        if (editText_rasp_B.getText() == null || editText_rasp_B.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), R.string.add_intr_text3_error_message, Toast.LENGTH_LONG).show();
            return false;
        }
        if (editText_rasp_C.getText() == null || editText_rasp_C.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), R.string.add_intr_text4_error_message, Toast.LENGTH_LONG).show();
            return false;
        }
        if (rg_raspuns.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(getActivity(), R.string.add_intr_text5_error_message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}

