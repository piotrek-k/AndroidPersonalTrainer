package stfn.personaltrainer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import stfn.personaltrainer.models._BaseModel;

/**
 * Created by Piotrek on 24/7/2017.
 */

public class _ModelBasedDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Exercices.db";

    public _BaseModel _bm;

    public String TableName = "Exercices";

    public _ModelBasedDatabaseHelper(Context context, _BaseModel bm, String tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _bm = bm;
        TableName = tableName;
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("msgs", "CREATING NEW DATABASE");

        db.execSQL(_bm.GenerateSQLCreateTableInstructions(TableName));
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(_bm.GenerateSQLDeleteEntriesInstructions(TableName));
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ContentValues GenerateContentValues(_BaseModel obj){
        ContentValues values = new ContentValues();
        Field[] fields = obj.getClass().getFields();
        for(Field f:fields){
            //f.setAccessible(true);
            //Log.v("msgs", f.getName());
            try {
                String name = f.getName();
                if(!name.startsWith("_")) {
                    Log.v("msgs", name);
                    Object o = f.get(obj);
                    if (o != null) {
                        if (f.getType().equals(int.class)) {
                            values.put(f.getName(), (int) o);
                        } else if (f.getType().equals(Date.class)) {
                            values.put(f.getName(), (long) o);
                        } else {
                            values.put(f.getName(), o.toString());
                        }
                    }
                }
            } catch(IllegalAccessException e){
                Log.e("msgs", ""+e);
            }
        }

        return values;
    }

    //where sample model is new _BaseModel
    public Cursor getData(Class<_BaseModel> sampleModel){
        SQLiteDatabase db = this.getReadableDatabase();

        Field[] fields = sampleModel.getClass().getFields();
        List<String> list = new ArrayList<String>();
        for(Field f:fields){
            list.add(f.getName());
        }
        String[] projection = new String[list.size()];
        Cursor cursor = db.query(TableName, projection, null, null, null, null, null);

        return cursor;
    }
}
