package stfn.personaltrainer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

import stfn.personaltrainer.database.AppDatabase;
import stfn.personaltrainer.entities.Exercise;

/**
 * Created by Piotrek on 2/8/2017.
 */

/**
 * Class contains all methods needed for activity
 */
public class ExercisesViewModel extends AndroidViewModel {

    private final LiveData<List<Exercise>> allExercises;
    private AppDatabase appDatabase;

    public ExercisesViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        allExercises = appDatabase.exerciseDao().getAll();
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public void seedDatabase() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Exercise pushups = new Exercise();
                pushups.setType("Push Ups");
                pushups.setLastSession(new Date(System.currentTimeMillis()));
                appDatabase.exerciseDao().insert(pushups);

                return null;
            }
        }.execute();
    }

    public void insertAsync(final Exercise exercise) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                appDatabase.exerciseDao().insert(exercise);

                return null;
            }
        }.execute();
    }

    public void deleteAsync(final Exercise exercise) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                appDatabase.exerciseDao().delete(exercise);

                return null;
            }
        }.execute();
    }
}
