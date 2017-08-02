package stfn.personaltrainer;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import stfn.personaltrainer.adapters.ExercisesAdapter;
import stfn.personaltrainer.entities.Exercise;
import stfn.personaltrainer.viewmodels.ExercisesViewModel;

//NOTE: At the time of writing this code, LiveData is not yet released as stable, so I needed to implement
//LifecycleRegistryOwner in order to omit extending LifecycleActivity. It will be implemented in AppCompatActivity
//in the future

public class MainPage extends AppCompatActivity implements LifecycleRegistryOwner {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    ExercisesViewModel evm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();

                Exercise e = new Exercise("New exercise");
                e.setTestResult(1000);
                e.setDayOfExercise(1);
                evm.insertAsync(e);
            }
        });

        evm = new ExercisesViewModel(this.getApplication());

        evm.getAllExercises().observe(this, exercises -> {
            if (exercises.isEmpty()) {
                evm.seedDatabase();
            }

            ExercisesAdapter lvAdapter = new ExercisesAdapter(MainPage.this, exercises);
            ListView exerciseItems = findViewById(R.id.exercices_list_view);
            exerciseItems.setAdapter(lvAdapter);
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

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
