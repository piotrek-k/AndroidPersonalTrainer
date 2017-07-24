package stfn.personaltrainer;

import android.database.Cursor;
import android.os.Bundle;
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

import stfn.personaltrainer.adapters.ExercicesCursorAdapter;
import stfn.personaltrainer.database.DatabaseHelper;
import stfn.personaltrainer.database._ModelBasedDatabaseHelper;
import stfn.personaltrainer.models.Exercise;
import stfn.personaltrainer.models._BaseModel;

public class MainPage extends AppCompatActivity {

    DatabaseHelper db;

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

        db = new DatabaseHelper(this);

        Cursor cursor = db.getData();
        ListView exerciseItems = (ListView) findViewById(R.id.exercices_list_view);
        ExercicesCursorAdapter lvAdapter = new ExercicesCursorAdapter(this, cursor);
        exerciseItems.setAdapter(lvAdapter);

        //cursor.close();
        Exercise e1 = new Exercise();
        e1.DayOfExercise = 101;
        e1.Type = "TEST";
        _ModelBasedDatabaseHelper _mb = new _ModelBasedDatabaseHelper(this, new Exercise(), "Exercices");
        _mb.GenerateContentValues(e1);

        exerciseItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Object o = lv.getItemAtPosition(position);
                Log.v("msgs", "TEST");
            }
        });
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
