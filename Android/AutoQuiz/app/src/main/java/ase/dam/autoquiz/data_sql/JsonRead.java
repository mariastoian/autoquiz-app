package ase.dam.autoquiz.data_sql;


import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonRead extends AsyncTask<URL, Void, String> {

   // private ProgressDialog pDialog;

    @Override
    protected String doInBackground(URL... urls) {

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            inputStreamReader.close();
            inputStream.close();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
    public List<Question> parseJson(String json) {
        List<Question> questions = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray qArray = jsonObject.getJSONArray("questions");
            for (int i = 0; i < qArray.length(); i++) {
                JSONObject currentObject = qArray.getJSONObject(i);
                Question question = new Question();
                question.setId(Integer.valueOf(currentObject.getString("id")));
                question.setQuestion(currentObject.getString("text"));
                question.setOption1(currentObject.getString("A"));
                question.setOption2(currentObject.getString("B"));
                question.setOption3(currentObject.getString("C"));
                question.setAnswerNr(Integer.valueOf(currentObject.getString("nr")));
                question.setCategorie_id(Integer.valueOf(currentObject.getString("categ_id")));
                questions.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return questions;
    }
}
