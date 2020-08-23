package ase.dam.autoquiz.ui.main;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import ase.dam.autoquiz.R;
import ase.dam.autoquiz.data_sql.CustomAdaptorIntrebare;
import ase.dam.autoquiz.data_sql.Question;
import ase.dam.autoquiz.data_sql.QuizContract;
import ase.dam.autoquiz.data_sql.QuizDbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Intrebari extends Fragment  {

    //EditText editText;
    ListView listView;
    ListView listView_db;
    List<Question> questions = new ArrayList<>();
    List<Question> questionList;
    QuizDbHelper dbHelper;
    CustomAdaptorIntrebare adapter_db;
    public Intrebari() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intrebari, container, false);
        final List<Question> arrayOfQuestions = new ArrayList<Question>();
        final CustomAdaptorIntrebare adapter = new CustomAdaptorIntrebare(getActivity(), arrayOfQuestions);
        Bundle bundle = getArguments();

        Button insert_btn=view.findViewById(R.id.save_q_db);
        //dbHelper = new QuizDbHelper(getActivity());
        dbHelper= QuizDbHelper.getInstance(getActivity());

        if (bundle != null) {
            listView = (ListView) view.findViewById(R.id.list_view_intrebari);
            listView.setAdapter(adapter);
            final Question intrebare = bundle.getParcelable("intrebari_adaugate");
            arrayOfQuestions.add(intrebare);
            adapter.updateList(arrayOfQuestions);
            insert_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.addQuestion(intrebare);
                    Toast.makeText(getActivity(), R.string.ins, Toast.LENGTH_SHORT).show();
                }
            });
        }

        questionList = dbHelper.getAllQuestions();
        listView_db = (ListView) view.findViewById(R.id.list_view_intrebari_db);
        if (questionList != null) {
            adapter_db = new CustomAdaptorIntrebare(getActivity(), questionList);
            adapter_db.notifyDataSetChanged();

            listView_db.setAdapter(adapter_db);
            listView_db.setLongClickable(true);
            //refresh on listview cu notify
            //registerForContextMenu(listView_db);
            //Dml actions on popup menu DELETE/UPDATE
            listView_db.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showPopUpMenu(view,position);
                }
            });
        }

        return view;
    }

    public void showPopUpMenu(View v, final int position) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        Question q=(Question)adapter_db.getItem(position);
                        dbHelper.deleteQuestion(q.getId());
                        questionList.remove(position);
                        adapter_db.notifyDataSetChanged();
                        Toast.makeText(getActivity(), R.string.del, Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_update:
                        Update up=new Update();
                        Bundle bundle = new Bundle();
                        Question intrebare=(Question)adapter_db.getItem(position);
                        bundle.putParcelable("intrebari_adaugate",intrebare);
                        up.setArguments(bundle);
                        FragmentManager fragmentManager=getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container,up).addToBackStack(null).commit();
                        //Toast.makeText(getActivity(), R.string.updat, Toast.LENGTH_SHORT).show();
                        adapter_db.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.context_menu);
        popup.show();
    }

   /* @Override
    public void onResume() {
        super.onResume();
        //questionList.clear();
        questionList = dbHelper.getAllQuestions(); // reload the items from database
        adapter_db.notifyDataSetChanged();
    }*/
    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id=-1;




        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int index = info.position;
        id= (int) adapter_db.getItemId(index);

        switch (item.getItemId()) {
            case R.id.menu_delete:

                //if (id) {
                    dbHelper.deleteQuestion(id);
                    //listView_db.remove(info.position);
                    //adapter_db.notifyDataSetChanged();
                    Toast.makeText(getActivity(), R.string.del, Toast.LENGTH_SHORT).show();
               // }
               // else
                  // Toast.makeText(getActivity(), R.string.dell, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_update:
               // dbHelper.updateQuestion();
                //if (dbHelper.updateQuestion(id,q_text,op1,op2,op3,ans,id_cat)) {
                    //Toast.makeText(getActivity(), R.string.updat, Toast.LENGTH_SHORT).show();
               // } else
                   // Toast.makeText(getActivity(), R.string.updatee, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }*/
      /*@Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    Toast.makeText(getActivity(), R.string.del, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_update:
                    Toast.makeText(getActivity(), R.string.updat, Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        }*/
}
