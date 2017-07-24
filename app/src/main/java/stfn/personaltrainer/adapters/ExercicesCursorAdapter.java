package stfn.personaltrainer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.Date;

import stfn.personaltrainer.R;
import stfn.personaltrainer.database.DatabaseHelper;
import stfn.personaltrainer.database.ExercisesDbHelper;
import stfn.personaltrainer.database._ModelBasedDatabaseHelper;
import stfn.personaltrainer.models.Exercise;

/**
 * Created by Piotrek on 24/7/2017.
 */

public class ExercicesCursorAdapter extends CursorAdapter {

    public ExercicesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_of_items, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView exerciceName = (TextView) view.findViewById(R.id.exercice_name);
        TextView lastExercice = (TextView) view.findViewById(R.id.last_exercice);
        TextView day = (TextView) view.findViewById(R.id.day);
        TextView test_result = (TextView) view.findViewById(R.id.test_result);
        // Extract properties from cursor
//        String cursor_exercice_type = cursor.getString(cursor.getColumnIndexOrThrow(ExercisesDbHelper.GenerateNameFromField(Exercise.Type.get)));
//        int cursor_last_exercice = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FeedEntry.COLUMN_NAME_LAST_SESSION));
//        int cursor_test_result = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FeedEntry.COLUMN_NAME_TEST_RESULT));
//        int cursor_day = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FeedEntry.COLUMN_NAME_DAY));
        Exercise exercise = new Exercise();
        exercise.LoadFromCursor(cursor);

        //Date dateOfLastExercice = new Date(cursor_last_exercice);
        // Populate fields with extracted properties
        exerciceName.setText(exercise.Type);
        lastExercice.setText(exercise.LastSession.toString());
        day.setText(String.valueOf(exercise.DayOfExercise));
        test_result.setText(String.valueOf(exercise.TestResult));
    }
}
