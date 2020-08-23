package ase.dam.autoquiz.ui.main;


import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import ase.dam.autoquiz.R;
import ase.dam.autoquiz.data_sql.CustomAdaptorIntrebare;
import ase.dam.autoquiz.data_sql.Question;
import ase.dam.autoquiz.data_sql.QuizDbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportTwo extends Fragment {

    private static final String FILE_NAME = "select1.txt";
    ListView listView_select2;
    CustomAdaptorIntrebare ad2;
    Button save2;
    List<Question> selectList2;
    QuizDbHelper dbHelper;
    public ReportTwo() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_report_two, container, false);
       // dbHelper = new QuizDbHelper(getActivity());
        dbHelper= QuizDbHelper.getInstance(getActivity());

        selectList2=dbHelper.getAllQuestionsSelect2();
        listView_select2=view.findViewById(R.id.list_view_select2);
        if(selectList2!=null)
        {
            ad2=new CustomAdaptorIntrebare(getActivity(),selectList2);
            listView_select2.setAdapter(ad2);

        }
        save2=view.findViewById(R.id.save_select2);
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveText(selectList2);
                //Toast.makeText(getActivity(), "Saved text file", Toast.LENGTH_SHORT).show();

            }
        });
//synchronize ca sa vad ce s a salvat nou


        return view;
    }
    private void saveText(List<Question> data) {

        FileOutputStream fos = null;

        try {
            // File newfile = new File(getActivity().getFilesDir().getAbsolutePath() + "/files",FILE_NAME);
            // newfile.mkdir();
            fos = getActivity().openFileOutput(FILE_NAME, getActivity().MODE_PRIVATE);
            //fos=new FileOutputStream(newfile);
            fos.write(data.toString().getBytes());
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
