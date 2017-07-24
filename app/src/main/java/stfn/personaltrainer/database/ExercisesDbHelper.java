package stfn.personaltrainer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import stfn.personaltrainer.models.Exercise;

/**
 * Created by Piotrek on 24/7/2017.
 */

public class ExercisesDbHelper extends _ModelBasedDatabaseHelper {
    public ExercisesDbHelper(Context context, Exercise bm) {
        super(context, bm, "Exercices");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        super.onCreate(db);

        // Create db values
        Exercise pushups = new Exercise();
        pushups.Type = "Push Ups";
        pushups.LastSession = new Date(System.currentTimeMillis());
        pushups.TestResult = 0;
        pushups.DayOfExercise = 1;
        db.insert(TableName, null, GenerateContentValues(pushups));

        Exercise pullups = new Exercise();
        pullups.Type = "Pull Ups";
        pullups.LastSession = new Date(System.currentTimeMillis());
        pullups.TestResult = 0;
        pullups.DayOfExercise = 1;
        db.insert(TableName, null, GenerateContentValues(pullups));
    }
}
