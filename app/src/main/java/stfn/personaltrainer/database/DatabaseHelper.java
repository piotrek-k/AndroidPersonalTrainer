package stfn.personaltrainer.database;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.Settings;
import android.util.Log;

import java.util.Date;

/**
 * Created by Piotrek on 24/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercices";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_LAST_SESSION = "last_session";
        public static final String COLUMN_NAME_TEST_RESULT = "test_result";
        public static final String COLUMN_NAME_DAY = "day";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TYPE + " TEXT," +
                    FeedEntry.COLUMN_NAME_LAST_SESSION + " DATE," +
                    FeedEntry.COLUMN_NAME_TEST_RESULT + " INTEGER," +
                    FeedEntry.COLUMN_NAME_DAY + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Exercices.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("msgs", "CREATING NEW DATABASE");

        db.execSQL(SQL_CREATE_ENTRIES);
        String[] names = {"pushups", "pullups"};

        for (String name : names) {
            ContentValues values = new ContentValues();
            values.put(FeedEntry.COLUMN_NAME_TYPE, name);
            values.put(FeedEntry.COLUMN_NAME_LAST_SESSION, System.currentTimeMillis());
            values.put(FeedEntry.COLUMN_NAME_TEST_RESULT, 0);
            values.put(FeedEntry.COLUMN_NAME_DAY, 1);

            long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TYPE,
                FeedEntry.COLUMN_NAME_LAST_SESSION,
                FeedEntry.COLUMN_NAME_TEST_RESULT,
                FeedEntry.COLUMN_NAME_DAY
        };

        // Filter results WHERE "title" = 'My Title'
        // String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        // String[] selectionArgs = {"My Title"};

        // How you want the results sorted in the resulting Cursor
        // String sortOrder =
        // FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        return cursor;
    }
}
