package stfn.personaltrainer.activities;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Random;

import stfn.personaltrainer.R;
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
                Exercise e = new Exercise("Pompki");
                e.setTestResult(23);
                Random r = new Random();
                e.setDayOfExercise(1);
                evm.insertAsync(e);
            }
        });

        evm = new ExercisesViewModel(this.getApplication());

        ListView exerciseItems = findViewById(R.id.exercices_list_view);

        evm.getAllExercises().observe(this, exercises -> {
            if (exercises.isEmpty()) {
                evm.seedDatabase();
            }

            ExercisesAdapter lvAdapter = new ExercisesAdapter(MainPage.this, exercises);
            exerciseItems.setAdapter(lvAdapter);
        });

        exerciseItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.v("msgs", "TEST");
                Exercise chosenExercise = evm.getAllExercises().getValue().get(position);
                Intent intent = new Intent(view.getContext(), Training.class);
                intent.putExtra("ExerciseData", chosenExercise.getUid());
                startActivity(intent);
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

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
