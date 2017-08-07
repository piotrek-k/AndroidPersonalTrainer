package stfn.personaltrainer.datacontainers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotrek on 7/8/2017.
 */

public class TrainingToPlanAndTimer implements Serializable {
    public String ExerciseName;
    public List<Integer> RepetitionsPerRound;
    public int SecondsBetweenRounds;
    public int NumberOfAllRounds;

    public TrainingToPlanAndTimer() {
        RepetitionsPerRound = new ArrayList<Integer>();
    }
}
