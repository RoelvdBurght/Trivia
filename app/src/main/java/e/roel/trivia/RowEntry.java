package e.roel.trivia;

import java.io.Serializable;

public class RowEntry implements Serializable {
    String name;
    int score;

    public RowEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
