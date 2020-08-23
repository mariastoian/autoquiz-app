package ase.dam.autoquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import ase.dam.autoquiz.data_sql.CustomAdaptorIntrebare;
import ase.dam.autoquiz.data_sql.JsonRead;
import ase.dam.autoquiz.data_sql.Question;
public class TopQuestions extends AppCompatActivity {
    private ListView lv;
    private CustomAdaptorIntrebare adapter;
    private List<Question> questions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_questions);
        connect();
        lv = findViewById(R.id.list_view_intrebariJson);
        adapter = new CustomAdaptorIntrebare(this,questions);
        lv.setAdapter(adapter);
    }
    private void connect() {
        JsonRead jsonRead = new JsonRead(){
            @Override
            protected void onPostExecute(String s) {
                List<Question> questions = parseJson(s);
                adapter.updateList(questions);
            }
        };
        try {
            jsonRead.execute(new URL("https://api.myjson.com/bins/19u7qq") );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

