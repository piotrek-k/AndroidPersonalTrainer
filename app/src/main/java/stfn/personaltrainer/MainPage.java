package stfn.personaltrainer;

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import stfn.personaltrainer.adapters.ExercicesCursorAdapter;
import stfn.personaltrainer.adapters.ExercisesAdapter;
import stfn.personaltrainer.daos.ExerciseDao;
import stfn.personaltrainer.database.AppDatabase;
import stfn.personaltrainer.database.DatabaseHelper;
import stfn.personaltrainer.database.ExercisesDbHelper;
import stfn.personaltrainer.database._ModelBasedDatabaseHelper;
import stfn.personaltrainer.models.Exercise;
import stfn.personaltrainer.models._BaseModel;

public class MainPage extends AppCompatActivity {

    ExercisesDbHelper db;
    ExerciseDao ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        db = new ExercisesDbHelper(this, new Exercise());
//
//        Cursor cursor = db.getData();
//        ListView exerciseItems = (ListView) findViewById(R.id.exercices_list_view);
//        ExercicesCursorAdapter lvAdapter = new ExercicesCursorAdapter(this, cursor);
//        exerciseItems.setAdapter(lvAdapter);

        //cursor.close();
//        Exercise e1 = new Exercise();
//        e1.DayOfExercise = 101;
//        e1.Type = "TEST";
//        _ModelBasedDatabaseHelper _mb = new _ModelBasedDatabaseHelper(this, new Exercise(), "Exercices");
//        _mb.GenerateContentValues(e1);

//        exerciseItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //Object o = lv.getItemAtPosition(position);
//                Log.v("msgs", "TEST");
//            }
//        });

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "personal-trainer-database").build();

        ed = db.exerciseDao();

        updateListView();

        //List<stfn.personaltrainer.entities.Exercise> items = ed.getAll();

//        if(items.isEmpty()){
//            stfn.personaltrainer.entities.Exercise pushups = new stfn.personaltrainer.entities.Exercise();
//            pushups.setType("Push Ups");
//            pushups.setLastSession(new Date(System.currentTimeMillis()));
//            ed.insert(pushups);
//
////            stfn.personaltrainer.entities.Exercise pullups = new stfn.personaltrainer.entities.Exercise();
////            pullups.type = "Pull Ups";
////            pullups.lastSession = new Date(System.currentTimeMillis());
////            pullups.testResult = 0;
////            pullups.dayOfExercise = 1;
////            ed.insert(pullups);
//
//            items = ed.getAll();
//        }

//        ExercisesAdapter lvAdapter = new ExercisesAdapter(this, items);
//        ListView exerciseItems = (ListView) findViewById(R.id.exercices_list_view);
//        exerciseItems.setAdapter(lvAdapter);
    }

    public void updateListView(){

        new AsyncTask<Void, Void, List<stfn.personaltrainer.entities.Exercise>>(){
            @Override
            protected List<stfn.personaltrainer.entities.Exercise> doInBackground(Void ... params){
                return ed.getAll();
            }

            @Override
            protected void onPostExecute(List<stfn.personaltrainer.entities.Exercise> items){
                if(items.isEmpty()) {
                    seedDatabase();
                }
                else {
                    ExercisesAdapter lvAdapter = new ExercisesAdapter(MainPage.this, items);
                    ListView exerciseItems = (ListView) findViewById(R.id.exercices_list_view);
                    exerciseItems.setAdapter(lvAdapter);
                }
            }
        }.execute();
    }

    public void seedDatabase(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void ... params){
                stfn.personaltrainer.entities.Exercise pushups = new stfn.personaltrainer.entities.Exercise();
                pushups.setType("Push Ups");
                pushups.setLastSession(new Date(System.currentTimeMillis()));
                ed.insert(pushups);

                return null;
            }

            @Override
            protected void onPostExecute(Void result){
                updateListView();
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
