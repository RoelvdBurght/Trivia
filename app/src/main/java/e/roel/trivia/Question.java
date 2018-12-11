package e.roel.trivia;

import java.io.Serializable;

// A question object is used to store the data about each question
public class Question implements Serializable {
    private String question;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;
    private String difficulty;
    private String cat;


    public Question(String question, String correctAnswer, String wrongAnswer1, String wrongAnswer2,
                    String wrongAnswer3, String difficulty, String cat) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
        this.difficulty = difficulty;
        this.cat = cat;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCat() {
        return cat;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }
}
