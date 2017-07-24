package stfn.personaltrainer.models;

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
        int i = 1;
        for(Field f:fields){
            if(!f.getName().startsWith("_")) {
                result += f.getName();

                if (f.getType().equals(int.class)) {
                    result += " INTEGER";
                } else if (f.getType().equals(Date.class)) {
                    result += " DATE";
                } else {
                    result += " TEXT";
                }

                if (i++ < fields.length-1) {
                    result += ", ";
                }
            }
            else if(f.getName().equals("_ID")){
                result += "_id INTEGER PRIMARY KEY, ";
            }
        }
        result += ")";
        return result;
    }

    public String GenerateSQLDeleteEntriesInstructions(String _tableName){
        return "DROP TABLE IF EXISTS " + _tableName;
    }
}
