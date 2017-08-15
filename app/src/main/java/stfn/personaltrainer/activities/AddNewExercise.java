package stfn.personaltrainer.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import stfn.personaltrainer.R;
import stfn.personaltrainer.entities.Exercise;
import stfn.personaltrainer.viewmodels.ExercisesViewModel;

public class AddNewExercise extends AppCompatActivity {

    Spinner spinner_exercises_to_choose;
    ExercisesViewModel evm;
    String choosedExerciseName;
    int choosedExercisePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        evm = new ExercisesViewModel(this.getApplication());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner_exercises_to_choose = findViewById(R.id.spinner_exercises_to_choose);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercises_to_choose, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_exercises_to_choose.setAdapter(adapter);
        spinner_exercises_to_choose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object o = parent.getItemAtPosition(pos);
                choosedExerciseName = spinner_exercises_to_choose.getSelectedItem().toString();
                choosedExercisePos = pos;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onButtonClick(View view) {
        Exercise exercise = new Exercise();
        exercise.setDayOfExercise(0);
        exercise.setTestResult(0);
        exercise.setType(choosedExerciseName);
        exercise.setResourcesId(choosedExercisePos);
        evm.insertAsync(exercise);
        finish();
    }


}
