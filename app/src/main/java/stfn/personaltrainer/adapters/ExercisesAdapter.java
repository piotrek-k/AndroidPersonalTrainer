package stfn.personaltrainer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import stfn.personaltrainer.R;
import stfn.personaltrainer.entities.Exercise;

/**
 * Created by Piotrek on 27/7/2017.
 */

public class ExercisesAdapter extends ArrayAdapter<Exercise> {

    public ExercisesAdapter(Context context, List<Exercise> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Exercise exercise = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_of_items, parent, false);
        }
        // Lookup view for data population
        TextView exerciseName = (TextView) convertView.findViewById(R.id.exercice_name);
        TextView lastExercise = (TextView) convertView.findViewById(R.id.last_exercice);
        TextView day = (TextView) convertView.findViewById(R.id.day);
        TextView test_result = (TextView) convertView.findViewById(R.id.test_result);
//         Populate the data into the template view using the data object
        exerciseName.setText(exercise.getType());
        lastExercise.setText(exercise.getLastSession().toString());
        day.setText(String.valueOf(exercise.getDayOfExercise()));
        test_result.setText(String.valueOf(exercise.getTestResult()));
        // Return the completed view to render on screen
        return convertView;
    }
}
