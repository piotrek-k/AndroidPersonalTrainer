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
    public static final String DATABASE_FILE = "Data.db";

    private static final String LOG_TAG = "_ModelBasedDatab...";

    public _BaseModel _bm;

    public String TableName = "Exercices";

    /**
     * SQLite DatabaseHelper strictly connected to model class.
     * Model class need to extend from _BaseModel.
     *
     * @param context
     * @param bm Class that will act as model - pattern on which database table is created
     * @param tableName SQL table name
     */
    public _ModelBasedDatabaseHelper(Context context, _BaseModel bm, String tableName) {
        super(context, DATABASE_FILE, null, DATABASE_VERSION);
        _bm = bm;
        TableName = tableName;
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "CREATING NEW DATABASE");

        db.execSQL(GenerateSQLCreateTableInstructions());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //If database structure is updated, delete data and start over
        db.execSQL(GenerateSQLDeleteEntriesInstructions());
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //As onUpgrade makes db from scratch (and that's what we want to do now), run its code
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Gathers data stored in class and prepares it to send them to database
     * @param obj Object that will be transfored to ContentValues
     * @return Object that can be easily passed to database
     */
    public ContentValues GenerateContentValues(_BaseModel obj){
        ContentValues values = new ContentValues();
        Field[] fields = obj.getClass().getFields();
        for(Field f:fields){
            try {
                String name = GenerateNameFromField(f);
                if(!name.startsWith("_")) {
                    Log.v(LOG_TAG, name);
                    Object o = f.get(obj);
                    if (o != null) {
                        if (f.getType().equals(int.class)) {
                            values.put(name, (int) o);
                        } else if (f.getType().equals(Date.class)) {
                            Date d = (Date)o;
                            values.put(name, d.getTime());
                        } else {
                            values.put(name, o.toString());
                        }
                    }
                }
            } catch(IllegalAccessException e){
                Log.e(LOG_TAG, ""+e);
            }
        }

        return values;
    }

    //where sample model is new _BaseModel

    /**
     * Gets all data from table connected with current helper
     * @return
     */
    public Cursor getData(){
        Class<? extends _BaseModel> sampleModel;
        sampleModel = _bm.getClass();

        SQLiteDatabase db = this.getReadableDatabase();

        Field[] fields = sampleModel.getClass().getFields();
        List<String> list = new ArrayList<String>();
        for(Field f:fields){
            String fName = GenerateNameFromField(f);
            if(!fName.startsWith("_") || fName.equals("_id")) {
                list.add(fName);
            }
        }
        String[] projection = new String[list.size()];
        Cursor cursor = db.query(TableName, projection, null, null, null, null, null);

        return cursor;
    }

    /**
     * Method meant to be a single place responsible of generating sql name in case I wanted to
     * change convention. Use it instead of field.getName()
     * @param f Single variable in class
     * @return Name that will be used as column name in database
     */
    public static String GenerateNameFromField(Field f){
        return f.getName();
    }

    /**
     * Generates CREATE TABLE instructions for SQL database based on Fields
     * present in current class
     * @return CREATE TABLE query
     */
    public String GenerateSQLCreateTableInstructions(){
        String result = "CREATE TABLE " + TableName + " (";

        Field[] fields = _bm.getClass().getFields();
        boolean first = true;
        for(Field f:fields){
            if (first) {
                first = false;
            }
            else {
                result += ", ";
            }
            String fName = GenerateNameFromField(f);
            if(!fName.startsWith("_")) {
                result += fName;

                if (f.getType().equals(int.class)) {
                    result += " INTEGER";
                } else if (f.getType().equals(Date.class)) {
                    result += " DATE";
                } else {
                    result += " TEXT";
                }
            }
            else if(fName.equals("_id")){
                result += "_id INTEGER PRIMARY KEY";
            }
        }
        result += ")";
        Log.v("msgs", "Creating database: ");
        Log.v("msgs", result);
        return result;
    }

    /**
     * Generates SQL instructions for deleting table connected to current DbHelper
     * @return
     */
    public String GenerateSQLDeleteEntriesInstructions(){
        return "DROP TABLE IF EXISTS " + TableName;
    }
}
