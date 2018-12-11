package e.roel.trivia;

import android.app.Activity;
import android.content.Context;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class HighscoreRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    String tag = "HighscoreRequest";
    Context context;
    private Callback caller;

    private static final String URL = "http://127.0.0.1:5000/list";
    public interface Callback {
        void gotHighscore(ArrayList<Score> scores);
        void gotHighscoreError(String message);
    }

    public HighscoreRequest(Context c) {
        this.context = c;
    }


    @Override
    public void onResponse(JSONObject response) {
        Log.d(tag, response.toString());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
        Log.d(tag, error.getMessage());
    }


    // Calls the server to give the questions, adds a request to the queue
    void getHighscores(Callback activity) {
        caller = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, this,this);
        queue.add(jsonObjectRequest);
    }
}

