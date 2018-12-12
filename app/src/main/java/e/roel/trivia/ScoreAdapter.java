package e.roel.trivia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreAdapter extends ArrayAdapter<Score> {

    private ArrayList<Score> scoreList;

    public ScoreAdapter(Context context, int resource, ArrayList<Score> scoreListIn) {
        super(context, resource, scoreListIn);
        scoreList = scoreListIn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            convertView = LayoutInflater.from(getContext())
                                    .inflate(R.layout.score_item, parent, false);
        }
        TextView nameView = convertView.findViewById(R.id.name);
        //TextView scoreView = convertView.findViewById(R.id.scoreText);
        Score thisScore = scoreList.get(position);
        nameView.setText(thisScore.getName());
        //
        // scoreView.setText(thisScore.getScore());
        return convertView;

    }
}
