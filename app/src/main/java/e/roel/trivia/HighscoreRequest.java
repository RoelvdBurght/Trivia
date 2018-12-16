package e.roel.trivia;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class HighscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    String tag = "HighscoreRequest";
    Context context;
    private Callback caller;


    private static final String URL = "https://ide50-rolo18.cs50.io:8080/list";
    public interface Callback {
        void gotHighscore(ArrayList<Score> Scores);
        void gotHighscoreError(String message);
    }

    public HighscoreRequest(Context c) {
        this.context = c;
    }


    // Parse the incoming jsonarray and make score objects containing the highscores
    // Pass a sorted list of these objects back to the calling activity
    @Override
    public void onResponse(JSONArray response) {
        ArrayList<Score> scores = new ArrayList<Score>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject o = (JSONObject) response.get(i);
                Iterator<String> keys = o.keys();
                // Find the names and corresponding scores and save them in a score object
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!key.equals("id")) {
                        String name = key;
                        int score = o.getInt(key);
                        Score s = new Score(name, score);
                        scores.add(s);
                    }
                }
            }
            catch (Exception e) {
                caller.gotHighscoreError(e.getMessage());
            }
        }
        scores = sortHighScores(scores);
        caller.gotHighscore(scores);
    }

    // Sends error message to the calling activity to display it to the user
    @Override
    public void onErrorResponse(VolleyError error) {
        caller.gotHighscoreError(error.getMessage());
    }


    // Calls the server to give the questions, adds a request to the queue
    void getHighscores(Callback activity) {
        caller = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest arrReq = new JsonArrayRequest(URL, this, null);
        queue.add(arrReq);
    }

    // Makes use of the implementation of comparable and Collections.sort to sort the highscores
    // from high to low
    private ArrayList<Score> sortHighScores(ArrayList<Score> scores) {
        Collections.sort(scores);
        for (int i = 0; i < scores.size(); i++) {
            Log.d(tag, scores.get(i).getScore() + " ");
        }
        return scores;
    }
}

