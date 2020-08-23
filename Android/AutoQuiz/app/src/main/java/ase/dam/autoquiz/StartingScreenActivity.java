package ase.dam.autoquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import ase.dam.autoquiz.data_sql.Categorie;
import ase.dam.autoquiz.data_sql.Question;
import ase.dam.autoquiz.data_sql.QuizDbHelper;
public class StartingScreenActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    private Spinner spinner;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    private TextView textViewHighscore;
    private int highscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);
        String[] arraySpinner = Categorie.getCategories();
        Spinner s = (Spinner) findViewById(R.id.spinner_categorie);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        textViewHighscore = findViewById(R.id.text_view_highscore);
        loadHighscore();
        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        Button buttonMaiMult = findViewById(R.id.button_altele_quiz);
        Button buttonTop = findViewById(R.id.top);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
        buttonMaiMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
        buttonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTopActivity();

            }
        });
    }
    private void startTopActivity() {
        Intent intent = new Intent(StartingScreenActivity.this, TopQuestions.class);
        startActivity(intent);
    }
    private void startQuiz() {
        //de implementat start cu categ
       // Categorie selectedCategorie = (Categorie) spinner.getSelectedItem();
       // int categorieID = selectedCategorie.getId();
       // String cat = selectedCategorie.getDenumire();
        Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);
       // intent.putExtra("id_categorie", categorieID);
       // intent.putExtra("denumire_categorie", cat);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }
    private void startMainActivity() {
        Intent intent = new Intent(StartingScreenActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }
    //utilizare fis de preferinte
    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Punctaj maxim: " + highscore);
    }
    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Punctaj maxim: " + highscore);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}