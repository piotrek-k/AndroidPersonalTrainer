package stfn.personaltrainer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import stfn.personaltrainer.daos.ExerciseDao;
import stfn.personaltrainer.entities.Exercise;

/**
 * Created by Piotrek on 28/7/2017.
 */

@Database(entities = {Exercise.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExerciseDao exerciseDao();
}
