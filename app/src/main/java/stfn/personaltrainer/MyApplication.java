package stfn.personaltrainer;

import android.app.Application;

import stfn.personaltrainer.database.AppDatabase;

/**
 * Created by Piotrek on 28/7/2017.
 */

public class MyApplication extends Application {

    public AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
