package stfn.personaltrainer.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Piotrek on 28/7/2017.
 */

@Entity
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    //What type of exercise it is
    @ColumnInfo(name = "type")
    private String type;

    //When did user do it last time
    @ColumnInfo(name = "last_session")
    private Date lastSession;

    //Score of last test (on which we calculate training progamme)
    @ColumnInfo(name = "test_result")
    private int testResult;

    //Which day of plan it is
    @ColumnInfo(name = "day_of_exercise")
    private int dayOfExercise;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastSession() {
        return lastSession != null ? lastSession : new Date();
    }

    public void setLastSession(Date lastSession) {
        this.lastSession = lastSession;
    }

    public int getTestResult() {
        return testResult;
    }

    public void setTestResult(int testResult) {
        this.testResult = testResult;
    }

    public int getDayOfExercise() {
        return dayOfExercise;
    }

    public void setDayOfExercise(int dayOfExercise) {
        this.dayOfExercise = dayOfExercise;
    }


}
