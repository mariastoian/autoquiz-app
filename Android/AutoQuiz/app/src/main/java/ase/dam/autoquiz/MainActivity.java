package ase.dam.autoquiz;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ase.dam.autoquiz.ui.main.Charts;
import ase.dam.autoquiz.ui.main.Indicatoare;
import ase.dam.autoquiz.ui.main.Intrebari;
import ase.dam.autoquiz.ui.main.Plus;
import ase.dam.autoquiz.ui.main.Progres;
import ase.dam.autoquiz.ui.main.ReportTwo;

//import android.app.FragmentManager;
//import android.app.FragmentManager;
//import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity  {
    // private RelativeLayout relativeLayout;
    //public static FragmentManager fragmentManager;
    //RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //relativeLayout.findViewById(R.id.relative_layout);
        if(findViewById(R.id.container)!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
            FragmentTransaction transaction=getFragmentManager().beginTransaction();
            Progres progres=new Progres();
            transaction.add(R.id.container, progres, null);
            transaction.commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_home) {
            Toast.makeText(this, R.string.home, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,StartingScreenActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_indicatoare) {
            Toast.makeText(this, R.string.item1, Toast.LENGTH_SHORT).show();
            changeFragment(new Indicatoare());
        }
        if (id == R.id.menu_progres) {
            Toast.makeText(this, R.string.item2, Toast.LENGTH_SHORT).show();
            changeFragment(new Progres());
        }
        if (id == R.id.menu_plus) {
            Toast.makeText(this, R.string.item3, Toast.LENGTH_SHORT).show();
            changeFragment(new Plus());
        }
        if (id == R.id.menu_intrebari) {
            Toast.makeText(this, R.string.item4, Toast.LENGTH_SHORT).show();
            changeFragment(new Intrebari());
        }
        if (id == R.id.menu_map) {
            Toast.makeText(this, R.string.item5, Toast.LENGTH_SHORT).show();
            changeFragment(new ReportTwo());
        }
        if (id == R.id.menu_chart) {
            Toast.makeText(this, R.string.item6, Toast.LENGTH_SHORT).show();
            changeFragment(new Charts());
        }
        return true;
    }
    private void changeFragment(Fragment f) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, f);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}

