package stfn.personaltrainer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import stfn.personaltrainer.daos.ExerciseDao;
import stfn.personaltrainer.entities.Exercise;

/**
 * Created by Piotrek on 28/7/2017.
 */

@Database(entities = {Exercise.class}, version = 3)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "borrow_db").fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public abstract ExerciseDao exerciseDao();
}
