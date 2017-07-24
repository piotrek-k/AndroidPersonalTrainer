package stfn.personaltrainer.models;

import java.util.Date;

/**
 * Created by Piotrek on 24/7/2017.
 */

public class Exercise extends _BaseModel {
    public Exercise(){
        super();
    }

    //What type of exercise it is
    public String Type;

    //When did user do it last time
    public Date LastSession;

    //Score of last test (on which we calculate training progamme)
    public int TestResult;

    //Which day of plan it is
    public int DayOfExercise;
}
