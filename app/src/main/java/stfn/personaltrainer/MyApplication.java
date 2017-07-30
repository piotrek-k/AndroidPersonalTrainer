package stfn.personaltrainer;

import android.app.Application;
import android.arch.persistence.room.Room;

import stfn.personaltrainer.database.AppDatabase;

/**
 * Created by Piotrek on 28/7/2017.
 */

public class MyApplication extends Application {

    public AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        //database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "personal-trainer-database").build();
    }
}
