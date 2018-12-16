package e.roel.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class Highscore extends AppCompatActivity implements HighscoreRequest.Callback {

    String tag = "HighScore";
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
        ListView scoreList = findViewById(R.id.listViewScore);
        ScoreAdapter adapter = new ScoreAdapter(this, R.layout.score_item,
                scores);
        scoreList.setAdapter(adapter);

    }

    @Override
    public void gotHighscoreError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
