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

import java.util.ArrayList;
import java.util.Iterator;

public class HighscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    String tag = "HighscoreRequest";
    Context context;
    private Callback caller;


    private static final String URL = "https://ide50-rolo18.cs50.io:8080/list";
    public interface Callback {
        void gotHighscore(ArrayList<RowEntry> rowEntries);
        void gotHighscoreError(String message);
    }

    public HighscoreRequest(Context c) {
        this.context = c;
    }


    @Override
    public void onResponse(JSONArray response) {
        ArrayList<RowEntry> rowEntries = new ArrayList<RowEntry>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject o = (JSONObject) response.get(i);
                Iterator<String> keys = o.keys();
                // Find the names and corresponding scores and savve them in a rowentry object
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!key.equals("id")) {

                        RowEntry row = new RowEntry(key, 1);
                        rowEntries.add(row);
                    }
                }
            }
            catch (Exception e) {
                caller.gotHighscoreError(e.getMessage());
            }
        }
        caller.gotHighscore(rowEntries);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(tag, "error respomse");
        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
        Log.d(tag, error.getMessage());
    }


    // Calls the server to give the questions, adds a request to the queue
    void getHighscores(Callback activity) {
        caller = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest arrReq = new JsonArrayRequest(URL, this, null);
        //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, this,this);
        queue.add(arrReq);
    }
}

