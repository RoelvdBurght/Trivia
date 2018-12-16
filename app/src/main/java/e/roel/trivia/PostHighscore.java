package e.roel.trivia;


import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

//
public class PostHighscore implements Response.Listener<String>, Response.ErrorListener {

    private String name;
    private String score;
    private CallbackPost caller;
    Context context;
    private static final String URL = "https://ide50-rolo18.cs50.io:8080/list";

    // Interfacce for posting request
    public interface CallbackPost {
        void posted(String response);
        void postError(String message);
    }

    // Constructor
    public PostHighscore(Context c, String n, String s) {
        context = c;
        this.name = n;
        this.score = s;
    }

    // On error, return the message to be displayed
    @Override
    public void onErrorResponse(VolleyError error) {
        caller.postError(error.getMessage());
    }

    // On response, send message to user
    @Override
    public void onResponse(String response) {
        caller.posted(response);
    }

    // Make post request
    public void postTheScore(CallbackPost activity) {
        caller = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        PostRequest req = new PostRequest(Request.Method.POST, URL, this,  this,
                                            name, score);
        queue.add(req);
    }
}
