package stfn.personaltrainer.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import stfn.personaltrainer.R;
import stfn.personaltrainer.entities.Exercise;

/**
 * Created by Piotrek on 27/7/2017.
 */

public class ExercisesAdapter extends ArrayAdapter<Exercise> {

    public Context context;

    public ExercisesAdapter(Context context, List<Exercise> users) {
        super(context, 0, users);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Exercise exercise = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_of_items, parent, false);
        }
        if (exercise.getNextWorkout().compareTo(Calendar.getInstance().getTime()) < 0) {
            convertView.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.veryLightGreen, null));
        }
        // Lookup view for data population
        TextView exerciseName = convertView.findViewById(R.id.exercice_name);
        TextView lastExercise = convertView.findViewById(R.id.last_exercice);
        TextView day = convertView.findViewById(R.id.day);
        TextView test_result = convertView.findViewById(R.id.test_result);
        TextView residtextview = convertView.findViewById(R.id.residtextview);
//         Populate the data into the template view using the data object
        exerciseName.setText(exercise.getType());
        lastExercise.setText(exercise.getLastSession().toString());
        day.setText(String.valueOf(exercise.getDayOfExercise()));
        test_result.setText(String.valueOf(exercise.getTestResult()));
        residtextview.setText(String.valueOf(exercise.getResourcesId()));
        // Return the completed view to render on screen
        return convertView;
    }
}
