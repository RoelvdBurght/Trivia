package e.roel.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class Highscore extends AppCompatActivity  implements HighscoreRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // Fetch highscores from server
        HighscoreRequest hr = new HighscoreRequest(this);
        hr.getHighscores(this);
    }

    @Override
    public void gotHighscore(ArrayList<Score> scores) {
        Toast.makeText(this, "got", Toast.LENGTH_LONG).show();

    }

    @Override
    public void gotHighscoreError(String message) {

    }
}
