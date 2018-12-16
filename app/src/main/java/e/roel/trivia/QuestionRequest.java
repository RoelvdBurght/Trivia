package e.roel.trivia;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;


public class QuestionRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    String tag = "QuestionRequest";
    Context context;
    private Callback caller;
    private static final String URL = "https://opentdb.com/api.php?amount=10&type=multiple";

    public interface Callback {
        void gotQuestions(ArrayList<Question> questions);
        void gotQuestionsError(String message);
    }

    public QuestionRequest(Context c) {
        this.context = c;
    }

    // Parse the incoming json object, make and fill a question object for each question, and
    // send these in an arraylist back to the main caller.
    @Override
    public void onResponse(JSONObject response) {
        ArrayList<Question> questions = new ArrayList<Question>();
        JSONArray jsonCategories;
        try {
            jsonCategories = response.getJSONArray("results");
            questions = fillQuestionArray(jsonCategories);
            caller.gotQuestions(questions);
        }
        catch(Exception e) {
            caller.gotQuestionsError(e.getMessage());
        }
    }

    // On error, pass the error to the main view and show it to the user.
    @Override
    public void onErrorResponse(VolleyError error) {
        caller.gotQuestionsError(error.getMessage());
    }


    // Calls the server to give the questions, adds a request to the queue
    void getQuestions(Callback activity) {
        caller = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, this,this);
        queue.add(jsonObjectRequest);
    }

    // Takes a JSON array containing the questions and parses it, filling an arraylist with
    // question objects containing the data. This arraylist is returned.
    // If something fails the error is shown to the user.
    private ArrayList<Question> fillQuestionArray(JSONArray js) {
        ArrayList<Question> arr = new ArrayList<Question>();
        int length = js.length();
        for (int i = 0; i < length; i++) {

            try {
                // Retrieve the questions and answer strings from the json object
                JSONObject questionEntry = (JSONObject) js.get(i);
                String q = questionEntry.getString("question");
                String cA = questionEntry.getString("correct_answer");
                JSONArray wrongQs = questionEntry.getJSONArray("incorrect_answers");
                String wA1 = wrongQs.getString(0);
                String wA2 = wrongQs.getString(1);
                String wA3 = wrongQs.getString(2);
                String cat = questionEntry.getString("category");
                String diff = questionEntry.getString("difficulty");

                // Decode the special characters
                q = Html.fromHtml(q).toString();
                cA = Html.fromHtml(cA).toString();
                wA1 = Html.fromHtml(wA1).toString();
                wA2 = Html.fromHtml(wA2).toString();
                wA3 = Html.fromHtml(wA3).toString();
                cat = Html.fromHtml(cat).toString();
                diff = Html.fromHtml(diff).toString();
                Question qObject = new Question(q, cA, wA1, wA2, wA3, diff, cat);
                arr.add(qObject);
            }

            catch (Exception e) {
                caller.gotQuestionsError(e.getMessage());
            }

        }
        return arr;
    }
}

