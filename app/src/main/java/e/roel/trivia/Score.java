package e.roel.trivia;

import java.io.Serializable;

// Container for the names and scores
// Implements Comparable to make it easy to sort the highscores from high to low
public class Score implements Serializable, Comparable<Score> {

    String name;
    int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score other) {

        if(other.getScore() > this.score) {
            return 1;
        }

        if(other.getScore() < this.score) {
            return -1;
        }

        return 0;
    }
}
