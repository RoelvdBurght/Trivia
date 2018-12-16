package e.roel.trivia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EndGame extends AppCompatActivity implements PostHighscore.CallbackPost {

    private static String score;
    private static final String URL = "https://ide50-rolo18.cs50.io:8080/list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Bundle scoreBundle = getIntent().getExtras();
        score = scoreBundle.getString("score");

        TextView scoreView = findViewById(R.id.scoreView);
        scoreView.setText(score);
    }

    // Show highscores when high score button is clicked
    public void highscore(View v) {
        Intent highscoreIntent = new Intent(EndGame.this, Highscore.class);
        startActivity(highscoreIntent);
    }

    // Start a new game when new game button is clicked
    public void newGame(View v) {
        Intent newGameIntent = new Intent(EndGame.this, SelectDiff.class);
        startActivity(newGameIntent);
    }

    // When the save button is clicked, check if the field is not blank. If filled in, post the
    // score to the server
    public void saveScore(View v) {
        EditText nameField = findViewById(R.id.inputName);
        String name = nameField.getText().toString();
        if (name.matches("")) {
            Toast.makeText(this, "Please enter a name before posting highscore",
                    Toast.LENGTH_LONG).show();
        }
        else {
            PostHighscore post = new PostHighscore(this, name, score);
            post.postTheScore(this);
        }

    }

    // Display a message to the user to show the post succeeded
    @Override
    public void posted(String response) {
        Toast.makeText(this, "Your highscore as been posted!", Toast.LENGTH_SHORT).show();
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setVisibility(View.INVISIBLE);
    }

    // On error when posting, display a message to the user showing this. (The message argument
    // was empty when checking so i hardcoded the message)
    @Override
    public void postError(String message) {
        Toast.makeText(this, "Server connection error, the server might be down",
                Toast.LENGTH_LONG).show();
    }
}
