package stfn.personaltrainer.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import stfn.personaltrainer.entities.Exercise;

/**
 * Created by Piotrek on 28/7/2017.
 */

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    List<Exercise> getAll();

    @Query("SELECT * FROM exercise WHERE type LIKE :name LIMIT 1")
    Exercise findByName(String name);

    @Insert
    void insertAll(List<Exercise> products);

    @Insert
    void insert(Exercise exercise);

    @Update
    void update(Exercise product);

    @Delete
    void delete(Exercise product);
}
