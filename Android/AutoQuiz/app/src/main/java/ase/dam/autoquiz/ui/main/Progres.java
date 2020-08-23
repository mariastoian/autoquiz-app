package ase.dam.autoquiz.ui.main;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;

import ase.dam.autoquiz.R;

import ase.dam.autoquiz.data_sql.CustomAdaptorIntrebare;
import ase.dam.autoquiz.data_sql.Question;
import ase.dam.autoquiz.data_sql.QuizDbHelper;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Progres extends Fragment {

    private static final String FILE_NAME2 = "select2.csv";
    ListView listView_select1;



    CustomAdaptorIntrebare ad1;
    Button save1;
    List<Question> selectList;
    QuizDbHelper dbHelper;
    public Progres() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_progres, container, false);
       // dbHelper = new QuizDbHelper(getActivity());
        dbHelper= QuizDbHelper.getInstance(getActivity());

        selectList=dbHelper.getAllQuestionsSelect1();

        listView_select1=view.findViewById(R.id.list_view_select1);


        if(selectList!=null)
        {
            ad1=new CustomAdaptorIntrebare(getActivity(),selectList);
            listView_select1.setAdapter(ad1);

        }

        save1=view.findViewById(R.id.save_select1);

        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCsv(selectList);
            }
        });

//synchronize ca sa vad ce s a salvat nou


        return view;
    }

    private void saveCsv(List<Question> selectList) {

        FileOutputStream fos = null;
        try {
            // File newfile = new File(getActivity().getFilesDir().getAbsolutePath() + "/files",FILE_NAME);
            // newfile.mkdir();
            fos = getActivity().openFileOutput(FILE_NAME2, getActivity().MODE_PRIVATE);
            //fos=new FileOutputStream(newfile);
            for(Question q:selectList)
            {
                fos.write(String.valueOf(q.getId()).getBytes());
                fos.write("\n".getBytes());
                fos.write(String.valueOf(q.getQuestion()).getBytes());
                fos.write("\n".getBytes());
                fos.write(String.valueOf(q.getOption1()).getBytes());
                fos.write("\n".getBytes());
                fos.write(String.valueOf(q.getOption2()).getBytes());
                fos.write("\n".getBytes());
                fos.write(String.valueOf(q.getOption3()).getBytes());
                fos.write("\n".getBytes());
                fos.write(String.valueOf(q.getAnswerNr()).getBytes());
                fos.write("\n".getBytes());
                fos.write(String.valueOf(q.getCategorie_id()).getBytes());
                fos.write("\n".getBytes());
            }

            Toast.makeText(getActivity(), R.string.txtsave , Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
