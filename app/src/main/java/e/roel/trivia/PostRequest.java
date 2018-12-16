package e.roel.trivia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


// Objects of this class can do POST requests with parameters.
public class PostRequest extends StringRequest {

    private String name;
    private String score;

    // Constructor
    public PostRequest(int method, String url, Response.Listener<String> listener,
                       Response.ErrorListener errorListener, String n, String s) {
        super(method, url, listener, errorListener);
        this.name = n;
        this.score = s;
    }

    // Method to supply parameters to the request
    @Override
    protected Map<String, String> getParams() {

        Map<String, String> params = new HashMap<>();
        params.put(name, score);
        return params;
    }


}