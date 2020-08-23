package ase.dam.autoquiz.data_sql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
//import java.util.ArrayList;
import java.util.List;

import ase.dam.autoquiz.R;



public class CustomAdaptorIntrebare extends BaseAdapter {
    private List<Question> qList;
    private LayoutInflater inflater;
    private Context context;

    public CustomAdaptorIntrebare(Context context, List<Question> intr) {
        this.context = context;
        this.qList = intr;
        this.inflater = LayoutInflater.from(context);


    }
    @Override
    public int getCount() {
        return qList.size();
    }

    @Override
    public Object getItem(int position) {
        return qList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

       //Question q = getItem(position);

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.listview_row2, parent, false);
        }
        TextView textView1 = (TextView) rowView.findViewById(R.id.textview_id_intr);
        TextView textView2 = (TextView) rowView.findViewById(R.id.textview_text_intrb);
        TextView textView3 = (TextView) rowView.findViewById(R.id.textview_A_intr);
        TextView textView4 = (TextView) rowView.findViewById(R.id.textview_B_intr);
        TextView textView5 = (TextView) rowView.findViewById(R.id.textview_C_intr);
        TextView textView6 = (TextView) rowView.findViewById(R.id.textview_rasp_intr);
        TextView textView7 = (TextView) rowView.findViewById(R.id.textview_cat_intr);
        textView1.setText(String.valueOf(qList.get(position).getId()));
        textView2.setText(qList.get(position).getQuestion());
        textView3.setText(qList.get(position).getOption1());
        textView4.setText(qList.get(position).getOption2());
        textView5.setText(qList.get(position).getOption3());

        if(qList.get(position).getAnswerNr()==1)
        {
            textView6.setText("A");
        }
        else
        if(qList.get(position).getAnswerNr()==2)
        {
            textView6.setText("B");

        }
        else
        if(qList.get(position).getAnswerNr()==3)
        {
            textView6.setText("C");

        }



        if(qList.get(position).getCategorie_id()==1)
        {
            textView7.setText("USOR");
        }
        else
            if(qList.get(position).getCategorie_id()==2)
            {
                textView7.setText("MEDIU");

            }
            else
                if(qList.get(position).getCategorie_id()==3)
                {
                    textView7.setText("GREU");

                }

        return rowView;
    }
    public void updateList(List<Question> qq) {
        qList = qq;
        notifyDataSetChanged();
    }
    public List<Question> getQuestionsList() {
        return qList;
    }

}