package ase.dam.autoquiz.ui.main;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import ase.dam.autoquiz.R;
import ase.dam.autoquiz.data_sql.Question;
import ase.dam.autoquiz.data_sql.QuizDbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Update extends Fragment {

    EditText editText_text_intrebare;
    EditText editText_rasp_A;
    EditText editText_rasp_B;
    EditText editText_rasp_C;
    Spinner spn_category;
    RadioGroup rg_raspuns;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    Button button_update;
    QuizDbHelper dbHelper;

    public Update() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     final View view=inflater.inflate(R.layout.fragment_update, container, false);
        editText_text_intrebare = view.findViewById(R.id.update_text);
        editText_rasp_A = view.findViewById(R.id.update_rasp_A);
        editText_rasp_B = view.findViewById(R.id.update_rasp_B);
        editText_rasp_C = view.findViewById(R.id.update_rasp_C);
        radioButton1=view.findViewById(R.id.btn_A_update);
        radioButton2=view.findViewById(R.id.btn_B_update);
        radioButton3=view.findViewById(R.id.btn_C_update);
        rg_raspuns = view.findViewById(R.id.rg_update);
        button_update = view.findViewById(R.id.update_intr_btn);
        spn_category = view.findViewById(R.id.update_spinner_categ);
        ArrayAdapter<CharSequence> categoryAdapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.categorytypelist,
                        R.layout.support_simple_spinner_dropdown_item);
        spn_category.setAdapter(categoryAdapter);
        //dbHelper = new QuizDbHelper(getActivity());
        dbHelper= QuizDbHelper.getInstance(getActivity());

        Bundle bundle=getArguments();

        if (bundle != null) {
            Question intrebare = bundle.getParcelable("intrebari_adaugate");
            Toast.makeText(getActivity(), intrebare.toString(), Toast.LENGTH_LONG).show();
            final int id=intrebare.getId();
            editText_text_intrebare.setText(intrebare.getQuestion());
            editText_rasp_A.setText(intrebare.getOption1());
            editText_rasp_B.setText(intrebare.getOption2());
            editText_rasp_C.setText(intrebare.getOption3());
            if(intrebare.getAnswerNr()==1)
            {
                radioButton1.setChecked(true);
            }
            else
                if(intrebare.getAnswerNr()==2)
                {
                    radioButton2.setChecked(true);
                }
                else
                    if(intrebare.getAnswerNr()==3)
                    {
                        radioButton3.setChecked(true);
                    }

            if(intrebare.getCategorie_id()==1)
            {
                spn_category.setSelection(0);
            }
            else
            if(intrebare.getCategorie_id()==2)
            {
                spn_category.setSelection(1);

            }
            else
            if(intrebare.getCategorie_id()==3)
            {
                spn_category.setSelection(2);

            }
            button_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()) {
                        Question q=createIntrebareFromView(view);
                        dbHelper.updateQuestion(id,q.getQuestion(),q.getOption1(),q.getOption2(),q.getOption3(),q.getAnswerNr(),q.getCategorie_id());
                        Toast.makeText(getActivity(), R.string.updat, Toast.LENGTH_LONG).show();
                        //Toast.makeText(getActivity(), q.toString(), Toast.LENGTH_LONG).show();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, new Intrebari());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }

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
