package stfn.personaltrainer.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import stfn.personaltrainer.R;
import stfn.personaltrainer.datacontainers.TrainingToPlanAndTimer;
import stfn.personaltrainer.entities.Exercise;

public class Training extends AppCompatActivity {

    private int SeriesDone = 1;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        exercise = (Exercise) getIntent().getSerializableExtra("ExerciseData");

        TextView exerciseName = findViewById(R.id.exerciceName);
        exerciseName.setText(exercise.getType());

        TextView whatDay = findViewById(R.id.whatDay);
        whatDay.setText(exercise.getDayOfExercise() + "");

        ProgressBar progressBar = findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, exercise.getDayOfExercise() * 1000); // animate from 0 to exercise.getDayOfExercise()*1000 (6000 is max)
        animation.setDuration(2500); //in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        TextView textProgress = findViewById(R.id.textProgress);
        textProgress.setText(String.valueOf(exercise.getDayOfExercise()) + "/6");
    }

    public void goToPlanAndTimer(View view) {
        TrainingToPlanAndTimer trainingToPlanAndTimer = new TrainingToPlanAndTimer();

        Resources res = getResources();
        TypedArray factors = res.obtainTypedArray(R.array.pushups);
        String[] rounds = factors.getString(exercise.getDayOfExercise() - 1).split(";");

        trainingToPlanAndTimer.NumberOfAllRounds = rounds.length;
        trainingToPlanAndTimer.ExerciseName = exercise.getType();
        for (String r : rounds) {
            int repsPerRound = Math.round(Float.parseFloat(r) * exercise.getTestResult());
            trainingToPlanAndTimer.RepetitionsPerRound.add(repsPerRound);
        }
        //TODO: Add ability to change SecondsBetweenRounds value
        trainingToPlanAndTimer.SecondsBetweenRounds = 60;

        Intent intent = new Intent(view.getContext(), PlanAndTimer.class);
        intent.putExtra("SentData", trainingToPlanAndTimer);
        startActivity(intent);
    }
}
