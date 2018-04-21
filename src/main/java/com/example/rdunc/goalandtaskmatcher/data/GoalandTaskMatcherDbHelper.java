package com.example.rdunc.goalandtaskmatcher.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GoalandTaskMatcherDbHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = GoalandTaskMatcherDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "goals.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public GoalandTaskMatcherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the goals table
        String SQL_CREATE_GOALS_TABLE =  "CREATE TABLE " + GoalandTaskMatcherContract.GoalEntry.TABLE_NAME + " ("
                + GoalandTaskMatcherContract.GoalEntry.MY_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GoalandTaskMatcherContract.GoalEntry.COLUMN_GOAL_DESCRIPTION + " TEXT );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_GOALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
