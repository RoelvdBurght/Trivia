package e.roel.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SelectDiff extends AppCompatActivity {

    int diff = 1;
    String tag = "SelectDiff";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_diff);

        // Set text to description for easy
        TextView description = findViewById(R.id.description);
        String text = getResources().getString(R.string.des1);
        description.setText(text);
    }

    // When a radiobutton is clicked, find out which one using switch statement and update
    // description text accordingly
    public void radioClicked(View v) {
        int id = v.getId();
        Log.d(tag, id + "");
        TextView description = findViewById(R.id.description);
        String text;
        switch (id) {
            case R.id.easy:
                Log.d(tag, "easy");
                diff = 1;
                text = getResources().getString(R.string.des1);
                description.setText(text);
                break;

            case R.id.medium:
                diff = 2;
                Log.d(tag, "med");
                text = getResources().getString(R.string.des2);
                description.setText(text);
                break;

            case R.id.hard:
                diff = 3;
                Log.d(tag, "hard");
                text = getResources().getString(R.string.des3);
                description.setText(text);
                break;
        }
    }

    // Start the game when the button is pressed, pass the difficulty with the intent
    public void startGame(View v) {
        Intent startIntent = new Intent(SelectDiff.this, GamePlay.class);
        startIntent.putExtra("diff", diff);
        startActivity(startIntent);
    }
}
