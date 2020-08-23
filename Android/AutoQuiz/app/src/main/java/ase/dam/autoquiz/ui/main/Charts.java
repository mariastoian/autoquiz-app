package ase.dam.autoquiz.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ase.dam.autoquiz.R;
import ase.dam.autoquiz.data_sql.Categorie;
import ase.dam.autoquiz.data_sql.QuizDbHelper;


public class Charts extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    TextView legend;
    QuizDbHelper dbHelper;
    private LinearLayout linearLayout;

    public Charts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_charts, container, false);
        //dbHelper = new QuizDbHelper(getActivity());
        dbHelper= QuizDbHelper.getInstance(getActivity());
        linearLayout = view.findViewById(R.id.linear);
        int values[] = {dbHelper.getNumbers(1),dbHelper.getNumbers(2),dbHelper.getNumbers(3)};
        List<Categorie> valuesXX=dbHelper.getCategorii();
        legend=view.findViewById(R.id.legend);

        String s="K";
        for(Categorie c:valuesXX) {
            s+=" :"+c.getId()+"->intrebari "+c.getDenumire();

        }
        legend.setText(s);


        linearLayout.addView(new MyChart(getActivity(),values));

        return view;
    }
    public  class MyChart extends View {

        Paint paint;
        Random g;
        int[] valueDegree;

        public MyChart (Context context, int[] values)
        {
            super(context);
            g = new Random ();
            paint = new Paint();
            int col= context.getResources().getColor(R.color.colorBackground);
            paint.setColor(col);
            valueDegree = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                valueDegree[i] = values[i];
            }
        }



        protected void onDraw (Canvas c)
        {
            int i;
            super.onDraw (c);
            c.drawPaint(paint);
            paint.setAntiAlias (true);
            for (i = 0; i < valueDegree.length; i++)
            {
                int h, R, G, B;
                h=600-valueDegree[i]*100;
                R = g.nextInt (255);
                G = g.nextInt (255);
                B = g.nextInt (255);
                paint.setTextSize(45);
                paint.setStyle (Paint.Style.FILL);
                paint.setColor (0xFF000000 + (R << 16) + (G << 8) + B);
                c.drawRect (i*200,  h, 200 + i*200, 600, paint);
                paint.setStyle (Paint.Style.STROKE);
                paint.setColor (Color.BLACK);
                c.drawRect (i*200,  h, 200 + i*200, 600, paint);
                paint.setStyle (Paint.Style.FILL);
                c.drawText ("K"+i+"="+valueDegree[i]+" ",   i*200, c.getHeight()/2+i, paint);

            }


        }



    }



}








