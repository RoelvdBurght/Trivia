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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // Fetch highscores from server
        HighscoreRequest hr = new HighscoreRequest(this);
        hr.getHighscores(this);
    }

    @Override
    public void gotHighscore(ArrayList<RowEntry> rowEntries) {
        Toast.makeText(this, "got", Toast.LENGTH_LONG).show();

        ListView scoreList = findViewById(R.id.listViewScore);
        ScoreAdapter adapter = new ScoreAdapter(this, R.layout.score_item,
                rowEntries);
        scoreList.setAdapter(adapter);

    }

    @Override
    public void gotHighscoreError(String message) {

    }
/*

    // Listener class for the category listview, sends us to the corresponding category when clicked
    public class CategoryListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String categoryClicked = parent.getItemAtPosition(position).toString();
            Intent intent = new Intent(.this, MenuActivity.class);
            intent.putExtra("category", categoryClicked);
            startActivity(intent);
        }

    }
    */
}
