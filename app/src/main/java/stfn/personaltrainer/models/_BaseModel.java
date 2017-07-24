package stfn.personaltrainer.models;

import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Piotrek on 24/7/2017.
 */

public class _BaseModel {

    //public String _TABLE_NAME;

    public int TEST1;
    public Date TEST2;

    public _BaseModel(){
    }

    public String GenerateSQLCreateTableInstructions(String _tableName){
        String result = "CREATE TABLE " + _tableName + " (";

        Field[] fields = this.getClass().getFields();
        boolean first = true;
        for(Field f:fields){
            if (first) {
                first = false;
            }
            else {
                result += ", ";
            }
            if(!f.getName().startsWith("_")) {
                result += f.getName();

                if (f.getType().equals(int.class)) {
                    result += " INTEGER";
                } else if (f.getType().equals(Date.class)) {
                    result += " DATE";
                } else {
                    result += " TEXT";
                }
            }
            else if(f.getName().equals("_id")){
                result += "_id INTEGER PRIMARY KEY";
            }
        }
        result += ")";
        Log.v("msgs", "Creating database: ");
        Log.v("msgs", result);
        return result;
    }

    public String GenerateSQLDeleteEntriesInstructions(String _tableName){
        return "DROP TABLE IF EXISTS " + _tableName;
    }

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
