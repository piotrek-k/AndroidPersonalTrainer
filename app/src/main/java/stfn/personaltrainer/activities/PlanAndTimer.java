package stfn.personaltrainer.activities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import stfn.personaltrainer.R;
import stfn.personaltrainer.datacontainers.TrainingToPlanAndTimer;
import stfn.personaltrainer.viewmodels.ExercisesViewModel;

public class PlanAndTimer extends AppCompatActivity {

    TextView roundInfo;
    ConstraintLayout cpatLayout;
    Context ctx;
    Button actionButton;
    Resources res;
    TextView howManyInCurrentRound;
    CountDownTimer cdt;
    boolean counterIsWorking = false;
    ExercisesViewModel evm;
    private TrainingToPlanAndTimer data;
    private int CurrentRound = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_and_timer);
        ctx = this.getApplicationContext();
        res = getResources();

        evm = new ExercisesViewModel(this.getApplication());

        data = (TrainingToPlanAndTimer) getIntent().getSerializableExtra("SentData");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView exerciseName = findViewById(R.id.exerciseName);
        exerciseName.setText(data.exercise.getType());

        roundInfo = findViewById(R.id.roundInfo);
        cpatLayout = findViewById(R.id.cpatLayout);
        actionButton = findViewById(R.id.actionButton);
        howManyInCurrentRound = findViewById(R.id.howMany);

        updateView();
    }

    public void goToNextRound(View view) {
        if (CurrentRound < data.NumberOfAllRounds && !counterIsWorking) {
            switchToBreakTimeView();
            cdt = new CountDownTimer(data.SecondsBetweenRounds * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
//                    long millis = millisUntilFinished;
//                    int minutes = (int)TimeUnit.MILLISECONDS.toMinutes(millis);
//                    millis -= TimeUnit.MINUTES.toMillis(minutes);
//                    int seconds = (int)TimeUnit.MILLISECONDS.toMinutes(millis);
//                    millis -= TimeUnit.SECONDS.toMillis(seconds);
//                    roundInfo.setText(String.format("%d:%d:%d", minutes, millis));
                    howManyInCurrentRound.setText((new SimpleDateFormat("mm:ss")).format(new Date(millisUntilFinished)));
                    counterIsWorking = true;
                }

                public void onFinish() {
                    countingFinished();
                }
            }.start();
        } else if (CurrentRound > data.NumberOfAllRounds) {
            evm.setNextDayToExercise(data.exercise);
            finish();
        } else if (counterIsWorking) {
            cdt.cancel();
            countingFinished();
        } else if (CurrentRound == data.NumberOfAllRounds) {
            countingFinished();
        }
    }

    public void updateView() {


        if (CurrentRound <= data.NumberOfAllRounds) {
            roundInfo.setText(res.getString(R.string.roundOutOf, CurrentRound, data.NumberOfAllRounds));
            howManyInCurrentRound.setText(String.valueOf(data.RepetitionsPerRound.get(CurrentRound - 1)));
        } else if (CurrentRound > data.NumberOfAllRounds) {
            roundInfo.setText(res.getString(R.string.roundOutOf, data.NumberOfAllRounds, data.NumberOfAllRounds));
            howManyInCurrentRound.setText(res.getString(R.string.trainingCompleted));
        }
    }

    public void switchToBreakTimeView() {
        cpatLayout.setBackgroundColor(ContextCompat.getColor(ctx, R.color.veryLightRed));
        actionButton.setText(res.getString(R.string.ceaseWaiting));
    }

    public void switchToRoundView() {
        cpatLayout.setBackgroundColor(ContextCompat.getColor(ctx, R.color.veryLightGreen));
        actionButton.setText(res.getString(R.string.done));
    }

    public void countingFinished() {
        CurrentRound++;
        switchToRoundView();
        updateView();
        counterIsWorking = false;
    }
}
