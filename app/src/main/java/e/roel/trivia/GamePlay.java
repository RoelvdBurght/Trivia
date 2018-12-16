package e.roel.trivia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePlay extends AppCompatActivity implements QuestionRequest.Callback {

    String tag = "GamePlay";
    ArrayList<Question> allQuestions;
    int questionCounter;
    int maxQuestions;
    Question currentQuestion;
    int correctAnswer;
    int score;

    TextView questionView;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    ProgressBar pBar;
    private int diff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // Retrieve the difficulty
        Bundle diffBundle = getIntent().getExtras();
        diff = 1;
        if (diffBundle != null) {
            diff = diffBundle.getInt("diff");
        }

        // Make QuestionRequest object and make a call to the server requesting the questions
        QuestionRequest qr = new QuestionRequest(this, diff);
        qr.getQuestions(this);

        // Find the relevant views
        questionView = findViewById(R.id.qView);
        button1 = findViewById(R.id.b1);
        button2 = findViewById(R.id.b2);
        button3 = findViewById(R.id.b3);
        button4 = findViewById(R.id.b4);
        pBar = findViewById(R.id.progressBar);

        // Set buttons to be half the width of the screen minus a little for padding
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        Button[] buttons = {button1, button2, button3, button4};
        for (int i = 0; i < 4; i++) {
            buttons[i].setWidth(displayWidth - (displayWidth/16));
            buttons[i].setHeight(displayHeight/8);
        }
        score = 0;
    }

    // When the questions are retrieved from the server, initialize necessary parameters and
    // Start the game
    @Override
    public void gotQuestions(ArrayList<Question> questions) {
        allQuestions = questions;
        currentQuestion = questions.get(0);
        questionCounter = 0;
        maxQuestions = allQuestions.size();
        pBar.setMax(maxQuestions);
        diplayUpdate();
    }

    // Display error message to the user when questions could not be retrieved
    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void diplayUpdate() {
        questionView.setText(currentQuestion.getQuestion());

        // Select random button to be the one containing the correct answer
        Random rand = new Random();
        int randomNum = rand.nextInt(4);
        switch (randomNum) {
            case 0 :
                correctAnswer = 0;
                button1.setText(currentQuestion.getCorrectAnswer());
                button2.setText(currentQuestion.getWrongAnswer1());
                button3.setText(currentQuestion.getWrongAnswer2());
                button4.setText(currentQuestion.getWrongAnswer3());
                break;
            case 1 :
                correctAnswer = 1;
                button2.setText(currentQuestion.getCorrectAnswer());
                button3.setText(currentQuestion.getWrongAnswer3());
                button4.setText(currentQuestion.getWrongAnswer2());
                button1.setText(currentQuestion.getWrongAnswer1());
                break;
            case 2 :
                correctAnswer = 2;
                button3.setText(currentQuestion.getCorrectAnswer());
                button1.setText(currentQuestion.getWrongAnswer1());
                button2.setText(currentQuestion.getWrongAnswer3());
                button4.setText(currentQuestion.getWrongAnswer2());
                break;
            case 3 :
                correctAnswer = 3;
                button4.setText(currentQuestion.getCorrectAnswer());
                button3.setText(currentQuestion.getWrongAnswer3());
                button2.setText(currentQuestion.getWrongAnswer2());
                button1.setText(currentQuestion.getWrongAnswer1());
                break;
        }

        Log.d(tag, correctAnswer + "");
        pBar.setProgress(questionCounter);
    }

    // Check if the answer was correct and increment the questionCounter and call displayupdate to
    // display next question
    public void answer(View v) {

        // Check if there are still questions left
        if (questionCounter < maxQuestions - 1) {

            // Get the id of the pressed button to check if it was th   e correct one
            int pressedButton = -1;
            switch (v.getId()) {
                case R.id.b1 :
                    pressedButton = 0;
                    break;
                case R.id.b2 :
                    pressedButton = 1;
                    break;
                case R.id.b3 :
                    pressedButton = 2;
                    break;
                case R.id.b4 :
                    pressedButton = 3;
                    break;
            }

            // Increment score if correct answer clicked
            if (pressedButton == correctAnswer) {
                score++;
                questionView.setBackgroundColor(Color.GREEN);
            }
            else {
                questionView.setBackgroundColor(Color.RED);
            }

            // Handler lets the code inside it get executed after a certain amount of time
            // Screen flashes red/green for a very brief moment
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionView.setBackgroundColor(getResources().getColor(R.color.backgroundMain));
                }
            }, 10000);

            // Proceed to the next question
            questionCounter ++;
            currentQuestion = allQuestions.get(questionCounter);
            diplayUpdate();
        }

        // When the game is finished, start new activity and pass the score allong
        else {
            Intent intent = new Intent(GamePlay.this, EndGame.class);
            score = score*diff;
            intent.putExtra("score", score+"");
            startActivity(intent);
        }

    }
}
