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
        String cursor_exercice_type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FeedEntry.COLUMN_NAME_TYPE));
        int cursor_last_exercice = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FeedEntry.COLUMN_NAME_LAST_SESSION));
        int cursor_test_result = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FeedEntry.COLUMN_NAME_TEST_RESULT));
        int cursor_day = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FeedEntry.COLUMN_NAME_DAY));

        Date dateOfLastExercice = new Date(cursor_last_exercice);
        // Populate fields with extracted properties
        exerciceName.setText(cursor_exercice_type);
        lastExercice.setText(dateOfLastExercice.toString());
        //day.setText(cursor_test_result);
        //test_result.setText(cursor_day);
    }
}
