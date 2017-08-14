package stfn.personaltrainer.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import stfn.personaltrainer.R;
import stfn.personaltrainer.entities.Exercise;
import stfn.personaltrainer.viewmodels.ExercisesViewModel;

public class Test extends AppCompatActivity {

    ExercisesViewModel evm;
    Exercise exercise;
    EditText testResultInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        evm = new ExercisesViewModel(this.getApplication());
        exercise = (Exercise) getIntent().getSerializableExtra("Exercise");
        testResultInput = findViewById(R.id.testResultInput);
    }

    public void saveTrainingResult(View view) {
        exercise.setDayOfExercise(1);
        int testResult;
        try {
            testResult = Integer.parseInt(testResultInput.getText().toString());
        } catch (NumberFormatException e) {
            testResultInput.setBackgroundColor(ContextCompat.getColor(this, R.color.veryLightRed));
            return;
        }
        exercise.setTestResult(testResult);
        exercise.setLastSession(Calendar.getInstance().getTime());
        evm.updateAsync(exercise);
        finish();
    }
}
