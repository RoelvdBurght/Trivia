package e.roel.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Bundle scoreBundle = getIntent().getExtras();
        String score = scoreBundle.getString("score");

        TextView scoreView = findViewById(R.id.scoreView);
        scoreView.setText(score);
    }

    public void highscore(View v) {
        Intent highscoreIntent = new Intent(EndGame.this, Highscore.class);
        startActivity(highscoreIntent);
    }

    public void newGame(View v) {
        Intent newGameIntent = new Intent(EndGame.this, GamePlay.class);
        startActivity(newGameIntent);
    }
}
