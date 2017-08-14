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

    public LiveData<Exercise> getExerciseById(int id) {
        return appDatabase.exerciseDao().findById(id);
    }

    public void seedDatabase() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Exercise pushups = new Exercise("Push Ups");
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

    /**
     * Set next day to exercise and update database
     *
     * @param exercise        Exercise to change
     * @param maxNumberOfDays After what value should number of days be set to zero
     */
    public void setNextDayToExercise(Exercise exercise, int maxNumberOfDays) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //appDatabase.exerciseDao().up
                //TODO: Make SQL update query
                exercise.setDayOfExercise(exercise.getDayOfExercise() + 1);
                if (exercise.getDayOfExercise() > maxNumberOfDays) {
                    exercise.setDayOfExercise(0);
                }
                appDatabase.exerciseDao().update(exercise);
                return null;
            }
        }.execute();
    }
}
