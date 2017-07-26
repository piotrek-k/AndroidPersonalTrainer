package stfn.personaltrainer.models;

import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Piotrek on 24/7/2017.
 */

public class _BaseModel {

    public _BaseModel(){
    }

    public int _id;

    /**
     * Gets Cursor and tries to assign values to _BaseModel's name equivalents.
     * In other words: fills current class with data from Cursor.
     * @param cursor
     */
    public void LoadFromCursor(Cursor cursor){
        Field[] fields = this.getClass().getFields();
        for(Field f:fields) {
            if(!f.getName().startsWith("_") || f.getName().equals("_id")) {
                try {
                    if (f.getType().equals(int.class)) {
                        f.set(this, cursor.getInt(cursor.getColumnIndexOrThrow(f.getName())));
                    } else if (f.getType().equals(Date.class)) {
                        f.set(this, new Date(cursor.getLong(cursor.getColumnIndexOrThrow(f.getName()))));
                    } else {
                        f.set(this, cursor.getString(cursor.getColumnIndexOrThrow(f.getName())));
                    }
                    Log.v("_BaseModel", "Loading " + f.getName() + " done");
                } catch (Exception e) {
                    Log.e("msgs", "I'm just letting you know that something's fucked up xD");
                }
            }
        }
    }
}
